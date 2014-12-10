package com.github.quick4j.upm.paths.entity;

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
@Table(name = "upm_paths")
@Auditable
public class Path extends AbstractEntity{
    @Id
    @Column(length = 32)
    private String id;
    @Column(name = "path_name", length = 500, nullable = false)
    private String name;
    @Column(name = "path", length = 2000)
    private String path;
    @Column(name = "path_icon", length = 100)
    private String icon;
    @Column(name = "pid", length = 32)
    private String pid;
    @Column(name = "path_index", length = 45)
    private int index;
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
    @JsonIgnore
    public List<? extends Entity> getSlave() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getChineseName() {
        return "资源";
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
