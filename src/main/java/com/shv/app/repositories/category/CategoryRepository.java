package com.shv.app.repositories.category;

import com.shv.app.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query(value = "select * from category join book on book.category_id = id",nativeQuery = true)
    Page<Category> findAll(String key,Pageable pageable);

    @Query(value = "SELECT * from category join image on image.id = ?1 join book on book.category_id = ?1",nativeQuery = true)
    Optional<Category> findById(String  id);

    Category findByTitle(String category_name);
}
