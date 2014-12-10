package com.github.quick4j.upm.actions.entity;

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
@Table(name = "upm_actions")
@Auditable
public class Action extends AbstractEntity {
    @Id
    @Column(length = 32)
    private String id;
    @Column(name = "action_code", length = 100, nullable = false)
    private String code;
    @Column(name = "action_name", length = 200)
    private String name;
    @Column(name = "action_icon", length = 100)
    private String icon;
    @Column(name = "action_index")
    private int index;
    
    @Override
    @JsonIgnore
    public String getChineseName() {
        return "操作按钮";
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

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
