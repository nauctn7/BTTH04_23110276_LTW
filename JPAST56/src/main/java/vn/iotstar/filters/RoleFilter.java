package vn.iotstar.filters;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;

@WebFilter("/*")
public class RoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String ctx = req.getContextPath();
        String path = req.getRequestURI().substring(ctx.length());

        boolean publicPath = path.startsWith("/login")
        				|| path.startsWith("/register")
                          || path.startsWith("/assets")
                          || path.startsWith("/public")
                          || path.startsWith("/uploads")
                          || path.startsWith("/css")
                          || path.startsWith("/js")
                          || path.startsWith("/favicon");
        if (publicPath) { chain.doFilter(request, response); return; }

        User u = (User) req.getSession().getAttribute("currentUser");
        if (u == null) { resp.sendRedirect(ctx + "/login"); return; }

        if (path.startsWith("/admin/") || "/admin/home".equals(path)) {
            if (u.getRoleId() != 3) { resp.sendError(403); return; }
        } else if (path.startsWith("/manager/") || "/manager/home".equals(path)) {
            if (u.getRoleId() != 2 && u.getRoleId() != 3) { resp.sendError(403); return; }
        }
        chain.doFilter(request, response);
    }
}
