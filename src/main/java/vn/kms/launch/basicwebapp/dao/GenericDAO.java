//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.dao;

import java.util.List;

/**
 * @author thanhtran
 *
 */
public interface GenericDAO<E, I> {
    void save(E entity);

    E getById(I id);

    List<E> findAll();
}
