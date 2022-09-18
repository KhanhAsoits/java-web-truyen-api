package com.shv.app.entities;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
@SQLDelete(sql = "UPDATE category SET state = 0 where id = ?")
@FilterDef(name = "deletedImageFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedImageFilter", condition = "state = :isDeleted")
public class Image {
    @Id
    private String id;

    private String relation_id;

    @Column(unique = true)
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(String relation_id) {
        this.relation_id = relation_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
