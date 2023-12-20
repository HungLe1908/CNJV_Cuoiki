package controller;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class menu {
    public void htmenu(HttpServletResponse res,PrintWriter out)
    {
        out.println("<div style='display: flex; justify-content: space-around; background-color: #f1f1f1; padding: 10px;'>");
        out.println("<a href='ThongtinHH' style='text-decoration: none; color: #333;'>Hàng Hóa</a>");
        out.println("<a href='Thongtin' style='text-decoration: none; color: #333;'>Khách Hàng</a>");
        out.println("<a href='ttTHUE' style='text-decoration: none; color: #333;'>Phiếu Thuê</a>");
        out.println("<a href='ttChitiet' style='text-decoration: none; color: #333;'>Chi Tiết Thuê</a>");
        out.println("<a href='index.html' style='text-decoration: none; color: #333;'>Đăng xuất </a>");
        out.println("</div>");

    }
}