package org.example.dao;

import org.example.model.Item;

import java.util.List;

public interface Idao<T> {

    boolean save(T entity);
    List<T> findAll();
    T findById(Long id);
    boolean deleteById(Long id);
    boolean update(T entity);
    }
