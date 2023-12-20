import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.THUE;
import controller.menu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/ttTHUE")
public class ttTHUE extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public ttTHUE() {
        super();
    }
    THUE in = new THUE();
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
        out.println("<h2>Tìm Kiếm Phiếu Thuê</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchSoPhieu'>Số Phiếu:</label>");
        out.println("  <input type='text' id='searchSoPhieu' name='searchSoPhieu' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");
        out.println("<h2>Form Nhập Dữ Liệu Phiếu Thuê</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='SoPhieu'>Số Phiếu:</label>");
        out.println("  <input type='text' id='SoPhieu' name='SoPhieu' required><br>");
        out.println("  <label for='MaKhach'>Mã Khách:</label>");
        out.println("  <input type='text' id='MaKhach' name='MaKhach' required><br>");
        out.println("  <label for='NgayThue'>Ngày Thuê:</label>");
        out.println("  <input type='text' id='NgayThue' name='NgayThue' required><br>");
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
        String searchSoPhieu = req.getParameter("searchSoPhieu");
        if (searchSoPhieu == null) {
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

String soPhieu = request.getParameter("SoPhieu"); // Assuming you pass the MaPhieuThue as a parameter
out.println("<h2>Form Nhập Dữ Liệu Phiếu Thuê</h2>");
out.println("<form method='post'>");

out.println("  <input type='hidden' name='SoPhieu' value='" + soPhieu + "'/>");
out.println("  <label for='maKhach'>Mã Khách:</label>");
out.println("  <input type='text' id='MaKhach' name='MaKhach' required><br>");

out.println("  <label for='ngayThue'>Ngày Thuê:</label>");
out.println("  <input type='text' id='NgayThue' name='NgayThue' required><br>");

out.println("  <label for='ngayTra'>Ngày Trả:</label>");
out.println("  <input type='text' id='NgayTra' name='NgayTra' required><br>");

// Add more fields as needed

out.println("  <input type='submit' value='Submit'>");
out.println("</form>");

out.println("</body>");
out.println("</html>");

String maKhach = request.getParameter("MaKhach");
String ngayThue = request.getParameter("NgayThue");
String SoPhieu = request.getParameter("SoPhieu");
// Retrieve other parameters as needed

if (soPhieu != null && maKhach != null) {
    // Call the method to handle the update for ChiTietThue
    // Assuming you have a method named suaChiTietThue in your 'chiTietThueDAO' object
    in.suaTHUE(request, response, out);
}
    }
    
}