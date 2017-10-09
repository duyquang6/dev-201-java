//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.dao;

import java.util.List;

import vn.kms.launch.basicwebapp.model.Product;

/**
 * @author thanhtran
 *
 */
public interface ProductDAO extends GenericDAO<Product, Long> {
    List<Product> search();
}
