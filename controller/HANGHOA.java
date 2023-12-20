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


public class HANGHOA {
        Connection con ;
        Statement stmt ;
    public HANGHOA() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/QuanLyChoThueDoCuoi?useUnicode=true&characterEncoding=UTF-8", "root", "19082003h");
        }
        public void displayData(PrintWriter out) 
        {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from HANGHOA";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Mã Hàng Hóa</th><th>Tên Hàng Hóa</th><th>Nước Sản Xuất</th><th>Tình Trạng Hàng Hóa</th></th>Giá Vốn</th></th>Giá Thuê 1 Ngày</th></tr>");
            while (rs.next()) {
                String maHangHoa = rs.getString("MaHangHoa");
                String tenHangHoa = rs.getString("TenHangHoa");
                String NuocSanXuat = rs.getString("NuocSanXuat");
                String tinhtrangHH = rs.getString("TinhTrangHH");
                double giaVon = rs.getDouble("GiaVon");
                double giathue1Ngay = rs.getDouble("GiaThue1N");
                out.println("<tr><td>" + maHangHoa + "</td><td>" + tenHangHoa + "</td><td>" + NuocSanXuat + "</td><td>" + tinhtrangHH + "</td><td>" + giaVon + "</td><td>" + giathue1Ngay + "</td>"+"<td><a href='Thongtin?MaHangHoaXoa=" + maHangHoa + "'>Xóa</a></td>"+
                        "<td><form method='post'>" +
                        "<input type='hidden' name='MaHangHoa' value='" + maHangHoa + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></td>");
            }
            out.println("</table>");
            con.close();
            } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
            }
        }
        public void nhap(HttpServletRequest request, HttpServletResponse response) throws IOException
        {
        String maHangHoa = request.getParameter("MaHangHoa");
        String tenHangHoa = request.getParameter("TenHangHoa");
        String NuocSanXuat = request.getParameter("NuocSanXuat");
        String tinhtrangHH = request.getParameter("TinhTrangHH");
        double giaVon = Double.parseDouble(request.getParameter("GiaVon"));
        double giathue1Ngay = Double.parseDouble(request.getParameter("GiaThue1N"));
        
        try {
           openConnection();
           String sql = "INSERT INTO HANGHOA (MaHangHoa, TenHangHoa, NuocSanXuat, TinhTrangHH, GiaVon, GiaThue1N) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql))
            {
                statement.setString(1, maHangHoa);
                statement.setString(2, tenHangHoa);
                statement.setString(3, NuocSanXuat);
                statement.setString(4, tinhtrangHH);
                statement.setDouble(5, giaVon);
                statement.setDouble(6, giathue1Ngay);

                // Thực hiện chèn dữ liệu
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
         
                    System.out.println("Dữ liệu đã được chèn thành công!");
                } 
                else 
                {
                    PrintWriter out = response.getWriter();
                  
                }
            }
            } catch (Exception e) 
            {
                   e.printStackTrace();
                    
            }
        }   
        public void xoa(HttpServletRequest request, HttpServletResponse response ,PrintWriter out)
        {
         try {
            openConnection();
            String maHangHoaXoa = request.getParameter("MaHangHoaXoa");

            // Kiểm tra xem mã khách có tồn tại không
            if (maHangHoaXoa != null && !maHangHoaXoa.isEmpty()) {
                // Tạo câu lệnh SQL để xóa khách hàng
                String sql = "DELETE FROM HANGHOA WHERE MaHangHoa = ?";
                
                // Tạo đối tượng PreparedStatement để thực hiện câu lệnh SQL
                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, maHangHoaXoa);
                    
                    // Thực hiện xóa dữ liệu
                    int rowsDeleted = statement.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        out.println("Xóa hàng hóa thành công!");
                    } else {
                        out.println("Không tìm thấy hàng hóa có mã " + maHangHoaXoa + " để xóa.");
                    }
                }
            } else {
            }
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
        }
        public void TK(HttpServletRequest req, HttpServletResponse res ,PrintWriter out)
        {
        String searchMaHangHoa = req.getParameter("searchMaHangHoa");
        try {
            openConnection();
            String sql = "SELECT * FROM HANGHOA WHERE MaHangHoa=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchMaHangHoa);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Mã Hàng Hóa</th><th>Tên Hàng Hóa</th><th>Nước Sản Xuất</th><th>Tình Trạng Hàng Hóa</th><th>Giá Vốn</th><th>Giá Thuê 1 Ngày</th><tr>");
                    out.println("<tr><td>" + resultSet.getString("MaHangHoa") + "</td><td>" + resultSet.getString("TenHangHoa") + "</td><td>" + resultSet.getString("NuocSanXuat") + "</td><td>"+ resultSet.getString("TinhTrangHH") + "</td><td>"+ resultSet.getDouble("GiaVon")+ "</td><td>"+ resultSet.getDouble("GiaThue1N") +" </td>"+"<td><a href='Xoa?maHangHoaXoa=" + resultSet.getString("MaHangHoa") + "'>Xóa</a></td>"+"<td><a href='Sua?MaHangHoa=" + resultSet.getString("MaHangHoa") + "'>Sua</a></td> </tr>");
                    out.println("</table>");
                } else {
                 
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
        public void xoaChiTietHoaDon(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            openConnection();

            // Retrieve the SoPhieu parameter from the request
            String soPhieuParam = request.getParameter("soPhieuXoa");
            if (soPhieuParam != null && !soPhieuParam.isEmpty()) {
                int soPhieu = Integer.parseInt(soPhieuParam);

                // Start a transaction
                con.setAutoCommit(false);

                try {
                    xoatubang("chitiethoadon", "SoPhieu", soPhieu);  // Replace "chitiethoadon" with the actual table name
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
        public void suaHangHoa(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        res.setContentType("text/html");
        String maHangHoa = req.getParameter("MaHangHoa");
        String tenHangHoa = req.getParameter("TenHangHoa");
        String NuocSanXuat = req.getParameter("NuocSanXuat");
        String tinhtrangHH = req.getParameter("TinhTrangHH");
        double giaVon = Double.parseDouble(req.getParameter("GiaVon"));
        double giathue1Ngay = Double.parseDouble(req.getParameter("GiaThue1N"));
        try {
            openConnection();
            String sql = "UPDATE HANGHOA SET TenHangHoa=?, NuocSanXuat=?, TinhTrangHH=?, GiaVon=?, GiaThue1N=? WHERE MaHangHoa=?";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, maHangHoa);
                statement.setString(2, tenHangHoa);
                statement.setString(3, NuocSanXuat);
                statement.setString(4,tinhtrangHH);
                statement.setDouble(5,giaVon);
                statement.setDouble(6,giathue1Ngay);
                
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out.println("Dữ liệu Hàng Hóa đã được cập nhật thành công!");
                } else {
                    out.println("Không thể cập nhật dữ liệu Hàng Hóa! Hãy chắc chắn rằng Mã hàng hóa tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
       
    }
