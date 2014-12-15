package com.github.quick4j.upm.users.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.AbstractEntity;
import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.plugin.logging.annontation.Auditable;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.List;

/**
 * @author zhaojh.
 */
@javax.persistence.Entity
@Table(name = "upm_users")
@Auditable
public class User extends AbstractEntity {
    @Id
    @Column(length = 32)
    private String id;
    @Column(name = "user_name", length = 200)
    private String name;
    @Column(name = "user_realname", length = 200)
    private String realName;
    @Column(name = "dept_id", length = 32)
    private String deptId;
    @Column(name = "dept_name", length = 500)
    private String deptName;

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
        return "用户";
    }

    @Override
    public String getName() {
        return name;
    }

    @JsonIgnore
    @Override
    public List<? extends Entity> getSlave() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
