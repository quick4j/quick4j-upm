package com.github.quick4j.upm.paths.service;

import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.upm.actions.entity.Action;
import com.github.quick4j.upm.paths.entity.ActionInPath;
import com.github.quick4j.upm.paths.entity.Path;

import java.util.List;
import java.util.Map;

/**
 * @author zhaojh
 */
public interface PathService extends CrudService<Path>{
    List<Action> findActionsInPath(String id);
    void saveActionAndPathRelation(String id, List<Action> inserted, List<Action> deleted);
}
