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


public class Index {
        Connection con ;
        Statement stmt ;
    public Index() {
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
            String sql = "select * from KHACH";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Mã Khách</th><th>Tên Khách</th><th>Địa Chỉ</th><th>SDT</th></tr>");
            while (rs.next()) {
                String maKhach = rs.getString("Makhach");
                String tenKhach = rs.getString("TenKhach");
                String DiaChi = rs.getString("DiaChi");
                String sdt = rs.getString("DienThoai");
                out.println("<tr><td>" + maKhach + "</td><td>" + tenKhach + "</td><td>" + DiaChi + "</td><td>"+ sdt + "</td>"+"<td><a href='Thongtin?MaKhachXoa=" + maKhach + "'>Xóa</a></td>"+"<td><a href='Sua?MaKhach=" + maKhach + "'>Sua</a></td> </tr>");
            }
            out.println("</table>");
            con.close();
            } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
            }
        }
        public void nhap(HttpServletRequest request, HttpServletResponse response) throws IOException
        {
        String maKhach = request.getParameter("MaKhach");
        String tenKhach = request.getParameter("TenKhach");
        String DiaChi = request.getParameter("DiaChi");
        String soDienThoai = request.getParameter("DienThoai");
        
        try {
           openConnection();
           String sql = "INSERT INTO KHACH (Makhach, TenKhach, DiaChi, DienThoai) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql))
            {
                statement.setString(1, maKhach);
                statement.setString(2, tenKhach);
                statement.setString(3, DiaChi);
                statement.setString(4, soDienThoai);

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
            String maKhachXoa = request.getParameter("MaKhachXoa");

            // Kiểm tra xem mã khách có tồn tại không
            if (maKhachXoa != null && !maKhachXoa.isEmpty()) {
                // Tạo câu lệnh SQL để xóa khách hàng
                String sql = "DELETE FROM KHACH WHERE MaKhach = ?";
                
                // Tạo đối tượng PreparedStatement để thực hiện câu lệnh SQL
                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, maKhachXoa);
                    
                    // Thực hiện xóa dữ liệu
                    int rowsDeleted = statement.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        out.println("Xóa khách hàng thành công!");
                    } else {
                        out.println("Không tìm thấy khách hàng có mã " + maKhachXoa + " để xóa.");
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
        String searchMaKhach = req.getParameter("searchMaKhach");
        try {
            openConnection();
            String sql = "SELECT * FROM KHACH WHERE MaKhach=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchMaKhach);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Mã Khách</th><th>Tên Khách</th><th>DiaChi</th><th>SDT</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("Makhach") + "</td><td>" + resultSet.getString("TenKhach") + "</td><td>" + resultSet.getString("DiaChi") + "</td><td>"+ resultSet.getString("DienThoai") + "</td>"+"<td><a href='Xoa?maKhachXoa=" + resultSet.getString("Makhach") + "'>Xóa</a></td>"+"<td><a href='Sua?MaKhach=" + resultSet.getString("MaKhach") + "'>Sua</a></td> </tr>");
                    out.println("</table>");
                } else {
                 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        
        
        }
}