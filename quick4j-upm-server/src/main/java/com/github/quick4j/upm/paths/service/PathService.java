package com.github.quick4j.upm.paths.service;

import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.upm.actions.entity.Action;
import com.github.quick4j.upm.paths.entity.ActionsInPath;
import com.github.quick4j.upm.paths.entity.Path;

import java.util.List;
import java.util.Map;

/**
 * @author zhaojh
 */
public interface PathService extends CrudService<Path, Map>{
    List<ActionsInPath> findAssignedButtons(String id);
    void assignButton(String id, List<Action> inserted, List<Action> deleted);
}
