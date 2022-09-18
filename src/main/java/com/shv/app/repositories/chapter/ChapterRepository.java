package com.shv.app.repositories.chapter;

import com.shv.app.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter,String> {

    @Query(value = "select * from chapter order by chapter.number_of_chapter limit 0,1",nativeQuery = true)
    Chapter findMaxChapter();
}
