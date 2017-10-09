//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.kms.launch.basicwebapp.web.controller.AbstractController;
import vn.kms.launch.basicwebapp.web.controller.ProductController;

@WebServlet({ "/app/*" })
public class MainController extends HttpServlet {
    private static final long serialVersionUID = -4528452534436354306L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String contextPath = req.getServletContext().getContextPath();
        requestURI = requestURI.substring(requestURI.indexOf(contextPath) + contextPath.length());
        String method = req.getMethod();

        //TODO read the url mapping from a config file
        //Hint: config pattern url --> controller class --> http method --> action class method name
        AbstractController controller = null;
        String methodName = null;
        if (requestURI.contains("/product/list") && method.equals("GET")) {
            controller = new ProductController();
            methodName = "getListAction";
        } else if (requestURI.contains("/product/get") && method.equals("GET")) {
            controller = new ProductController();
            methodName = "getProductByIdAction";
        } else if (requestURI.contains("/product/updateOrCreate") && method.equals("POST")) {
            controller = new ProductController();
            methodName = "createOrUpdateProductAction";
        }
        if (controller != null && methodName != null) {
            controller.executeMethod(methodName, req, resp);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/error.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
