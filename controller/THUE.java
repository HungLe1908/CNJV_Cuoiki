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


public class THUE {
        Connection con ;
        Statement stmt ;
    public THUE() {
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
            String sql = "select * from THUE";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Số Phiếu</th><th>Mã Khách</th><th>Ngày Thuê</th></tr>");
            while (rs.next()) {
                String soPhieu = rs.getString("SoPhieu");
                String maKhach = rs.getString("MaKhach");
                String ngayThue = rs.getString("NgayThue");
                out.println("<tr><td>" + soPhieu + "</td><td>" + maKhach + "</td><td>" + ngayThue + "</td> + <td><a href='Thongtin?SoPhieu=" + soPhieu + "'>Xóa</a></td>"
                        +"<td><form method='post'>" +
                        "<input type='hidden' name='SoPhieu' value='" + soPhieu + "'>" +
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
        String soPhieu = request.getParameter("SoPhieu");
        String maKhach = request.getParameter("MaKhach");
        String ngayThue = request.getParameter("NgayThue");
        
        try {
           openConnection();
           String sql = "INSERT INTO THUE (SoPhieu, MaKhach, NgayThue) VALUES (?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql))
            {
                statement.setString(1, soPhieu);
                statement.setString(2, maKhach);
                statement.setString(3, ngayThue);

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
            String soPhieuXoa = request.getParameter("SoPhieuXoa");

            // Kiểm tra xem mã khách có tồn tại không
            if (soPhieuXoa != null && !soPhieuXoa.isEmpty()) {
                // Tạo câu lệnh SQL để xóa khách hàng
                String sql = "DELETE FROM THUE WHERE SoPhieu = ?";
                
                // Tạo đối tượng PreparedStatement để thực hiện câu lệnh SQL
                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, soPhieuXoa);
                    
                    // Thực hiện xóa dữ liệu
                    int rowsDeleted = statement.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        out.println("Xóa số phiếu thành công!");
                    } else {
                        out.println("Không tìm thấy số phiếu có mã " + soPhieuXoa + " để xóa.");
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
        String searchSoPhieu = req.getParameter("searchSoPhieu");
        try {
            openConnection();
            String sql = "SELECT * FROM THUE WHERE SoPhieu=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchSoPhieu);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Số Phiếu</th><th>Mã Khách</th><th>Ngày Thuê</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("SoPhieu") + "</td><td>" + resultSet.getString("MaKhach") + "</td><td>" + resultSet.getString("NgayThue") + "</td>"+"<td><a href='Xoa?SoPhieuXoa=" + resultSet.getString("SoPhieu") + "'>Xóa</a></td>"+"<td><a href='Sua?SoPhieu=" + resultSet.getString("SoPhieu") + "'>Sua</a></td> </tr>");
                    out.println("</table>");
                } else {
                 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        
        
        }
        public void suaTHUE(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        res.setContentType("text/html");
        String soPhieu = req.getParameter("SoPhieu");
        String maKhach = req.getParameter("MaKhach");
        String ngayThue = req.getParameter("NgayThue");
        try {
            openConnection();
            String sql = "UPDATE THUE SET MaKhach=?, NgayThue=? WHERE SoPhieu=?";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, soPhieu);
                statement.setString(2, maKhach);
                statement.setString(3, ngayThue);

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
}