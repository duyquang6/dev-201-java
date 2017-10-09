//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.kms.launch.basicwebapp.dao.ProductDAO;
import vn.kms.launch.basicwebapp.exception.GeneralException;
import vn.kms.launch.basicwebapp.model.Product;
import vn.kms.launch.basicwebapp.model.ProductCategory;
import vn.kms.launch.basicwebapp.web.utils.ConnectionManager;

/**
 * @author thanhtran
 *
 */
public class ProductDAOImpl extends GenericDAOImpl<Product, Long>implements ProductDAO {
    private static final Logger LOG = LoggerFactory.getLogger(ProductDAOImpl.class.getCanonicalName());

    private static final String INSERT_QUERY = "INSERT INTO PRODUCTS(PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_DESC, PRODUCT_PRICE) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE PRODUCTS SET PRODUCT_NAME = ?, PRODUCT_CATEGORY = ?, PRODUCT_DESC = ?, PRODUCT_PRICE = ? WHERE PRODUCT_ID = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM PRODUCTS WHERE PRODUCT_ID = ?";

    @Override
    public void save(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
            if (product.getId() != null) {
                statement = connection.prepareStatement(UPDATE_QUERY);
                statement.setString(1, product.getName());
                statement.setString(2, product.getCategory().name());
                statement.setString(3, product.getDescription());
                statement.setBigDecimal(4, product.getPrice());
                statement.setLong(5, product.getId());
            } else {
                statement = connection.prepareStatement(INSERT_QUERY);
                statement.setString(1, product.getName());
                statement.setString(2, product.getCategory().name());
                statement.setString(3, product.getDescription());
                statement.setBigDecimal(4, product.getPrice());
            }
            statement.executeUpdate();
            LOG.debug("Save product successfully!");
        } catch (Exception e) {
            throw new GeneralException("Cannot save Product", e);
        } finally {
            ConnectionManager.close(statement);
            ConnectionManager.close(connection);
        }
    }

    @Override
    public Product getById(Long id) {
        Product result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = generateEntity(resultSet);
                LOG.debug("Found Product with id = " + id);
            }
        } catch (Exception e) {
            throw new GeneralException("Cannot get Product by id", e);
        } finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(statement);
            ConnectionManager.close(connection);
        }
        return result;
    }

    @Override
    public List<Product> search() {
        // TODO need implementation and change the method parameters
        return new ArrayList<Product>();
    }

    // TODO Remove all hard-coded column name using annotation and Java
    // reflection and move this method to GenericDAOImpl
    @Override
    protected Product generateEntity(ResultSet resultSet) {
        try {
            Product product = new Product();
            product.setId(resultSet.getLong("PRODUCT_ID"));
            product.setName(resultSet.getString("PRODUCT_NAME"));
            product.setCategory(ProductCategory.valueOf(resultSet.getString("PRODUCT_CATEGORY")));
            product.setDescription(resultSet.getString("PRODUCT_DESC"));
            product.setPrice(resultSet.getBigDecimal("PRODUCT_PRICE"));
            return product;
        } catch (SQLException e) {
            throw new GeneralException("Cannot generate entity product from result set", e);
        }
    }

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }
}
