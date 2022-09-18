package com.shv.app.repositories.author;

import com.shv.app.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {

    @Query(value = "select * from author where author.author_name = ?1", nativeQuery = true)
    Author findByName(String author_name);
}
