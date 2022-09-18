package com.shv.app.repositories.book;

import com.shv.app.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    @Query(value = "select * from book join author on id = author_id join category on category.id = book.category_id where book.title like %?1% or category.title like %?1% or author.author_name like %?1%" ,nativeQuery = true)
    Page<Book> findByKeyWord(String query, Pageable pageable);

    Optional<Book> findBookByTitle(String title);

    Book findByTitle(String name);

//    Optional<Book> findById(Long aLong);
}
