//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.WebContext;
import vn.kms.launch.basicwebapp.dao.ProductDAO;
import vn.kms.launch.basicwebapp.dao.impl.ProductDAOImpl;
import vn.kms.launch.basicwebapp.model.Product;
import vn.kms.launch.basicwebapp.model.ProductCategory;
import vn.kms.launch.basicwebapp.web.utils.TemplateEngineUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

public class ProductController extends AbstractController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class.getCanonicalName());

    private ProductDAO productDAO;

    public ProductController() {
        productDAO = new ProductDAOImpl();
    }

    public void getListAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> pList = productDAO.findAll();
        WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        webContext.setVariable("list", pList);
        TemplateEngineUtil.getEngine(req.getServletContext()).process("product/list", webContext, resp.getWriter());
    }

    public void getProductByIdAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        WebContext webContext;
        Product product = null;
        try {
            Long id = Long.parseLong(req.getParameter("productId"));
            product = productDAO.getById(id);
        } catch (Exception ignore) {
            // Ignore all exception
        }
        webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        webContext.setVariable("product", product);
        TemplateEngineUtil.getEngine(req.getServletContext()).process("product/view", webContext, resp.getWriter());
    }

    public void createOrUpdateProductAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        try {
            Product product = new Product();
            if (req.getParameter("productId") != null) {
                product.setId(Long.parseLong(req.getParameter("productId")));
            }
            product.setName(req.getParameter("productName"));
            product.setCategory(ProductCategory.valueOf(req.getParameter("categoryName")));
            product.setDescription(req.getParameter("productDesc"));
            if (req.getParameter("productPrice") != null) {
                product.setPrice(new BigDecimal(req.getParameter("productPrice")));
            }
            productDAO.save(product);
            out.println("Success");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            out.println("Fail");
        }
    }
}
