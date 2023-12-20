import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.HANGHOA;
import controller.menu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/ThongtinHH")
public class ThongtinHH extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public ThongtinHH() {
        super();
    }
    HANGHOA in = new HANGHOA();
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
     
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<body>");
        menu menu = new menu();
        menu.htmenu(res, out);
        out.println("<h2>Tìm Kiếm Hàng Hóa</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaKhach'>Mã Hàng Hóa:</label>");
        out.println("  <input type='text' id='searchMaHangHoa' name='searchMaHangHoa' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");
        out.println("<h2>Form Nhập Dữ Liệu Hàng Hóa</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='MaHangHoa'>Mã Hàng Hóa:</label>");
        out.println("  <input type='text' id='MaHangHoa' name='MaHangHoa' required><br>");
        out.println("  <label for='tenKhach'>Tên Hàng Hóa:</label>");
        out.println("  <input type='text' id='TenHangHoa' name='TenHangHoa' required><br>");
        out.println("  <label for='NuocSanXuat'>Nước Sản Xuất:</label>");
        out.println("  <input type='text' id='NuocSanXuat' name='NuocSanXuat' required><br>");
        out.println("  <label for='TinhTrangHH'>Tình Trạng Hàng Hóa:</label>");
        out.println("  <input type='text' id='TinhTrangHH' name='TinhTrangHH' required><br>");
        out.println("  <label for='GiaVon'>Giá Vốn:</label>");
        out.println("  <input type='text' id='GiaVon' name='GiaVon' required><br>");
        out.println("  <label for='GiaThue1N'>Giá Thuê 1 Ngày:</label>");
        out.println("  <input type='text' id='GiaThue1N' name='GiaThue1N' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        out.println("</body>");
        out.println("<style>");
        out.println("body");
        out.println("table {border-collapse: collapse; width: 80%;}");
        out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
        out.println("th {background-color: #f2f2f2;}");
        out.println("</style>");
        out.println("</head>");
        out.println("</table>");
        out.println("</body></html>");
        String searchMaHangHoa = req.getParameter("searchMaHangHoa");
        if (searchMaHangHoa == null) {
            in.displayData(out);
        }
        else{
            in.TK(req, res, out);
        }
        in.xoa(req, res, out);
        in.nhap(req, res);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
PrintWriter out = response.getWriter();

out.println("<html>");
out.println("<head>");
out.println("<title>Form Nhập Dữ Liệu</title>");
out.println("</head>");
out.println("<body>");

String maHangHoa = request.getParameter("MaHangHoa"); // Assuming you pass the MaHangHoa as a parameter
out.println("<h2>Form Nhập Dữ Liệu Chi Tiết Hàng Hóa</h2>");
out.println("<form method='post'>");

out.println("  <input type='hidden' name='MaHangHoa' value='" + maHangHoa + "'/>");
out.println("  <label for='TenHangHoa'>Tên Hàng Hóa:</label>");
out.println("  <input type='text' id='TenHangHoa' name='TenHangHoa' required><br>");

out.println("  <label for='NuocSanXuat'>Nước Sản Xuất:</label>");
out.println("  <input type='text' id='NuocSanXuat' name='NuocSanXuat' required><br>");

out.println("  <label for='tinhTrangHH'>Tình Trạng Hàng Hóa:</label>");
out.println("  <input type='text' id='TinhTrangHH' name='TinhTrangHH' required><br>");

out.println("  <label for='GiaVon'>Giá Vốn:</label>");
out.println("  <input type='text' id='GiaVon' name='GiaVon' required><br>");

out.println("  <label for='GiaThueHH'>Giá Thuê Hàng Hóa:</label>");
out.println("  <input type='text' id='GiaThue1N name='GiaThue1N' required><br>");

// Add more fields as needed

out.println("  <input type='submit' value='Submit'>");
out.println("</form>");

out.println("</body>");
out.println("</html>");

String tenHangHoa = request.getParameter("TenHangHoa");
String nuocSanXuat = request.getParameter("NuocSanXuat");
String tinhTrangHH = request.getParameter("TinhTrangHH");
String giaVon = request.getParameter("GiaVon");
String giaThue1N = request.getParameter("GiaThue1N");
// Retrieve other parameters as needed

if (maHangHoa != null && tenHangHoa != null) {
    // Call the method to handle the update for HangHoa
    // Assuming you have a method named suaHangHoa in your 'hangHoaDAO' object
    in.suaHangHoa(request, response, out);
}
    }
    
}