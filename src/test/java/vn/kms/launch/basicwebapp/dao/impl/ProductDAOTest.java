//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.dao.impl;

import org.hamcrest.junit.MatcherAssert;
import org.junit.Test;

import vn.kms.launch.basicwebapp.dao.ProductDAO;
import vn.kms.launch.basicwebapp.dao.impl.ProductDAOImpl;
import vn.kms.launch.basicwebapp.model.Product;
import vn.kms.launch.basicwebapp.model.ProductCategory;
import vn.kms.launch.basicwebapp.test.EmbeddedDB;

import static org.hamcrest.Matchers.*;

public class ProductDAOTest extends EmbeddedDB {
    private ProductDAO productDAO = new ProductDAOImpl();

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setCategory(ProductCategory.CATEGORY_1);
        productDAO.save(product);

        MatcherAssert.assertThat("There should 1 product in the system", productDAO.findAll().size(), is(equalTo(1)));
    }
}
