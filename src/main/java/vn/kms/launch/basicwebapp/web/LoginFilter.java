//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * @author thanhtran
 *
 */
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
    private static final String SECRET = "LaunchProgram";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) req).getRequestURI();
        String contextPath = req.getServletContext().getContextPath();
        requestURI = requestURI.substring(requestURI.indexOf(contextPath) + contextPath.length());

        // TODO read the white-list from app-config
        if (requestURI.equals("/index.jsp") || requestURI.equals("/error.jsp")) {
            chain.doFilter(req, resp);
        } else {
            //TODO update authentication method
            boolean authenticated = !SECRET.equals(req.getParameter("secret"));
            if (authenticated) {
                chain.doFilter(req, resp);
            } else {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/error.jsp");
                dispatcher.forward(req, resp);
            }
        }
    }

    @Override
    public void destroy() {
    }

}
