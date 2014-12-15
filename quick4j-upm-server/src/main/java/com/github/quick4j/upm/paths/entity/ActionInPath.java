package com.github.quick4j.upm.paths.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.AbstractEntity;
import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.plugin.logging.annontation.Auditable;
import com.github.quick4j.upm.actions.entity.Action;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.List;

/**
 * @author zhaojh
 */
@javax.persistence.Entity
@Table(name = "upm_actionInPath")
@Auditable
public class ActionInPath extends AbstractEntity{
    @Id
    @Column(length = 32)
    private String id;
    @Column(name = "path_id", length = 32, nullable = false)
    private String pathId;
    @Column(name = "action_id", length = 32, nullable = false)
    private String actionId;
    @Column(name = "action_code", length = 100)
    private String actionCode;
    @Column(name = "application_id", length = 32)
    private String applicationId;

    private String pathName;

    public ActionInPath() {}

    public ActionInPath(Action action, Path path) {
        this.actionId = action != null ? action.getId() : null;
        this.actionCode = action != null ? action.getCode() : null;
        this.pathId = path != null ? path.getId() : null;
        this.pathName = path != null ? path.getName() : null;
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
    public List<? extends Entity> getSlave() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getChineseName() {
        return "资源与按钮关联关系";
    }

    @Override
    @JsonIgnore
    public String getName() {
        return String.format("%s被绑定到%s", actionCode, pathName == null ? pathId : pathName);
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

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
