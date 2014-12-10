package com.github.quick4j.upm.roles.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.AbstractEntity;
import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.plugin.logging.annontation.Auditable;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.List;

/**
 * @author zhaojh
 */
@javax.persistence.Entity
@Table(name = "upm_roles")
@Auditable
public class Role extends AbstractEntity {
    @Id
    @Column(length = 32)
    private String id;
    @Column(name = "role_name", length = 200)
    private String name;
    @Column(name = "role_desc", length = 500)
    private String desc;
    @Column(name = "application_id", length = 32)
    private String applicationId;

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getChineseName() {
        return "角色";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    @JsonIgnore
    public List<? extends Entity> getSlave() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
