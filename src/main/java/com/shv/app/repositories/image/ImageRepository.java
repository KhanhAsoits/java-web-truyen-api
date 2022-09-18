package com.shv.app.repositories.image;

import com.shv.app.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,String> {

    @Query(value = "select * from image where relation_id = ?1",nativeQuery = true)
    Optional<List<Image>> findByRelationId(String id);

}
