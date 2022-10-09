package com.shv.app.services.base;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ICrudService<E, IdType> {
    E save(E entity) throws Exception;

    E update(E entity) throws Exception;

    void removeById(IdType idType) throws Exception;

    E remove(E entity) throws Exception;

    List<E> findAll() throws Exception;

    Page<E> findByPage(int page, int limit) throws Exception;

    List<E> findByKeyWord(String keyword) throws Exception;

    Page<E> search(String query, int page, int limit) throws Exception;
}
