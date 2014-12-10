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
@Table(name = "upm_pathInRole")
@Auditable
public class PathInRole extends AbstractEntity{
    @Id
    @Column(length = 32)
    private String id;
    @Column(name = "role_id", length = 32)
    private String roleId;
    @Column(name = "path_id", length = 32)
    private String pathId;
    @Column(name = "action_id", length = 32)
    private String actionId;
    @Column(name = "action_code", length = 32)
    private String actionCode;

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

    @Override
    public String getChineseName() {
        return "角色资源权限";
    }

    @Override
    public String getName() {
        return null;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }
}
