//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.kms.launch.basicwebapp.annotation.Table;
import vn.kms.launch.basicwebapp.dao.GenericDAO;
import vn.kms.launch.basicwebapp.exception.GeneralException;
import vn.kms.launch.basicwebapp.web.utils.ConnectionManager;

/**
 * @author thanhtran
 *
 */
// TODO Need implementation using Java Annotation and Reflection to generate
// query
public abstract class GenericDAOImpl<E, I> implements GenericDAO<E, I> {
    protected ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public void save(E entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public E getById(I id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<E> findAll() {
        Class<E> eClz = getEntityClass();
        Table table = eClz.getAnnotation(Table.class);
        String tableName = table.name();
        if ("".equals(tableName)) {
            tableName = eClz.getSimpleName();
        }

        List<E> result = new ArrayList<E>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(String.format("SELECT * FROM %s", tableName));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(generateEntity(resultSet));
            }
        } catch (Exception e) {
            throw new GeneralException("Cannot get all entities from table: " + tableName, e);
        } finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(statement);
            ConnectionManager.close(connection);
        }
        return result;
    }

    protected abstract Class<E> getEntityClass();

    protected E generateEntity(ResultSet rs) {
        try {
            E entity = getEntityClass().newInstance();
            //TODO populate entity fields via java annotation and reflection
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
