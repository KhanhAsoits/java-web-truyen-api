package com.shv.app.services.base;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ICrudService<E, IdType> {
    E save(E entity);

    E update(E entity);

    void removeById(IdType idType);

    E remove(E entity);

    List<E> findAll();

    Page<E> findByPage(int page, int limit);

    List<E> findByKeyWord(String keyword);

    Page<E> search(String query, int page, int limit);
}
