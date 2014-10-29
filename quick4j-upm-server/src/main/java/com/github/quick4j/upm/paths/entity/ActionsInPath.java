package com.github.quick4j.upm.paths.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;

import java.util.List;

/**
 * @author zhaojh
 */
@MapperNamespace("com.github.quick4j.upm.paths.entity.ActionsInPathMapper")
public class ActionsInPath extends Entity{
    private String pathId;
    private String actionId;
    private String actionCode;
    private String applicationId;

    public ActionsInPath() {}

    public ActionsInPath(String pathId, String actionId, String actionCode, String applicationId) {
        this.pathId = pathId;
        this.actionId = actionId;
        this.actionCode = actionCode;
        this.applicationId = applicationId;
    }

    @Override
    @JsonIgnore
    public String getMetaData() {
        return "按钮与资源关联关系";
    }

    @Override
    @JsonIgnore
    public List<? extends Entity> getSlave() {
        return null;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }
}
