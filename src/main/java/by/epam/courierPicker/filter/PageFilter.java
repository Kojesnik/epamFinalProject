package by.epam.courierPicker.filter;


import by.epam.courierPicker.constant.Path;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {Path.ADMIN_PAGE_PATH, Path.COURIER_PAGE_PATH})
public class PageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.sendRedirect(request.getContextPath() + Path.MAIN_PATH);
        filterChain.doFilter(request, response);
    }
}
