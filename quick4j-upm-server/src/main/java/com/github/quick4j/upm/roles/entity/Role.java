package com.github.quick4j.upm.roles.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;
import com.github.quick4j.plugin.logging.annontation.Auditable;

import java.util.List;

/**
 * @author zhaojh
 */
@Auditable
@MapperNamespace("com.github.quick4j.upm.roles.entity.RoleMapper")
public class Role extends Entity {
    private String name;
    private String desc;
    private String applicationId;

    @Override
    @JsonIgnore
    public String getMetaData() {
        return "角色-" + this.name;
    }

    @Override
    @JsonIgnore
    public List<? extends Entity> getSlave() {
        return null;
    }

    public String getName() {
        return name;
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
