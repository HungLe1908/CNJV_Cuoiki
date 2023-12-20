package controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CHITIETTHUE {
    Connection con;
    PreparedStatement stmt;
    int SoPhieu;
    int MaHangHoa;
    String NgayTra;

    public CHITIETTHUE() {
        // You may initialize the database connection here if needed
    }
    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/QuanLyChoThueDoCuoi?useUnicode=true&characterEncoding=UTF-8", "root", "19082003h");
    }
    private CHITIETTHUE Parameter(HttpServletRequest request) {
        CHITIETTHUE CHITIETTHUE = new CHITIETTHUE();
        CHITIETTHUE.SoPhieu = Integer.parseInt(request.getParameter("SoPhieu"));
        CHITIETTHUE.MaHangHoa = Integer.parseInt(request.getParameter("MaHangHoa"));
        CHITIETTHUE.NgayTra = request.getParameter("NgayTra");
        // Add more fields as needed
        try {
            // Additional code for database operations if required
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return CHITIETTHUE;

    }
public void displayData(PrintWriter out) {
        try {
            // Open connection, create statement, and execute query
            openConnection();
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM CHITIETTHUE"; // Change table name to "chitietthue"
            ResultSet rs = stmt.executeQuery(sql);

            // Display data in a table
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Số Phiếu</th><th>Mã Hàng Hóa</th><th>Ngày Trả</th></tr>");

            // Iterate through the result set and display each row in the table
            while (rs.next()) {
                String soPhieu = rs.getString("SoPhieu");
                String maHangHoa = rs.getString("MaHangHoa");
                String ngayTra = rs.getString("NgayTra");

                out.println("<tr><td>" + soPhieu + "</td><td>" + maHangHoa + "</td><td>" + ngayTra + "</td>" +
                        "<td><form method='get'>" +
                        "<input type='hidden' name='soPhieuXoa' value='" + soPhieu + "'>" +
                        "<input type='submit' value='Xoa'>" +
                        "</form></td></td>"+
                        "<td><form method='post'>" +
                        "<input type='hidden' name='SoPhieu' value='" + soPhieu + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
            }

            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
    }
public void nhapCHITIETTHUE(HttpServletRequest request, HttpServletResponse response) {
        try {
            openConnection();
            CHITIETTHUE CHITIETTHUE = Parameter(request); // Assuming you have a Parameter method

            // Assuming your table name for ChiTietHoaDon is "chitiethoadon"
            String sql = "INSERT INTO CHITIETTHUE (SoPhieu, MaHangHoa, NgayTra) VALUES (?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, CHITIETTHUE.SoPhieu);
                statement.setInt(2, CHITIETTHUE.MaHangHoa);
                statement.setString(3, CHITIETTHUE.NgayTra);

                // Execute the insert statement
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
                    System.out.println("Dữ liệu chi tiết đã được chèn thành công!");
                } else {
                    PrintWriter out = response.getWriter();
                    System.out.println("Chèn dữ liệu chi tiết không thành công!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public void suaChiTietHoaDon(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        res.setContentType("text/html");
        try {
            openConnection();
            CHITIETTHUE chiTietHoaDon = Parameter(req); // Assuming you have a Parameter method in ChiTietHoaDonDAO
            String sql = "UPDATE chitiethoadon SET MaHangHoa=?, NgayTra=? WHERE SoPhieu=?";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, chiTietHoaDon.MaHangHoa);
                statement.setString(2, chiTietHoaDon.NgayTra);
                statement.setInt(3, chiTietHoaDon.SoPhieu);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out.println("Dữ liệu Chi Tiết Hóa Đơn đã được cập nhật thành công!");
                } else {
                    out.println("Không thể cập nhật dữ liệu Chi Tiết Hóa Đơn! Hãy chắc chắn rằng Số Phiếu tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
public void TKChiTietThue(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        String searchSoPhieu = req.getParameter("searchSoPhieu"); // Change the parameter name based on your form

        try {
            openConnection();
            String sql = "SELECT * FROM CHITIETTHUE WHERE SoPhieu=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchSoPhieu);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body, table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table>");
                    out.println("<tr><th>Số Phiếu</th><th>Mã Hàng Hóa</th><th>Ngày Trả</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("SoPhieu") + "</td>"
                            + "<td>" + resultSet.getString("MaHangHoa") + "</td>"
                            + "<td>" + resultSet.getString("NgayTra") + "</td>"
                            + "<td><form method='get'>" +
                            "<input type='hidden' name='maSoPhieuXoa' value='" + resultSet.getString("SoPhieu") + "'>" +
                            "<input type='submit' value='Xoa'>" +
                            "</form></td></td>"+
                            "<td><form method='post'>" +
                            "<input type='hidden' name='SoPhieu' value='" + resultSet.getString("SoPhieu") + "'>" +
                            "<input type='submit' value='Sửa'>" +
                            "</form></td></tr>");

                } else {
                    // Handle the case where no matching record is found
                    out.println("Không tìm thấy Chi Tiết Thuê với Số Phiếu Thuê: " + searchSoPhieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
public void xoatubang(String tableName, String columnName, int value) throws Exception 
       {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) 
            {
            statement.setInt(1, value);
            statement.executeUpdate();
            }
       }
public void xoaCHITIETTHUE(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            openConnection();

            // Retrieve the SoPhieu parameter from the request
            String soPhieuParam = request.getParameter("soPhieuXoa");
            if (soPhieuParam != null && !soPhieuParam.isEmpty()) {
                int soPhieu = Integer.parseInt(soPhieuParam);

                // Start a transaction
                con.setAutoCommit(false);

                try {
                    xoatubang("CHITIETTHUE", "SoPhieu", soPhieu);  // Replace "chitiethoadon" with the actual table name
                    // You may need to call xoatubang for other tables related to ChiTietHoaDon

                    con.commit();

                    out.println("Xóa chi tiết hóa đơn thành công!");
                } catch (Exception e) {
                    // Rollback the transaction if an error occurs
                    con.rollback();
                    out.println("Lỗi: " + e.getMessage());
                } finally {
                    // Reset auto-commit to true
                    con.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        } finally {
            out.close();
        }
}
}

    