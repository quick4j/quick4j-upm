package com.github.quick4j.upm.paths.service;

import com.github.quick4j.core.service.support.SimpleCrudService;
import com.github.quick4j.upm.actions.entity.Action;
import com.github.quick4j.upm.paths.entity.ActionsInPath;
import com.github.quick4j.upm.paths.entity.Path;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojh
 */
@Component("pathService")
public class PathServiceImpl extends SimpleCrudService<Path, Map> implements PathService{

    @Override
    public List<ActionsInPath> findAssignedButtons(String id) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("pathId", id);
        return getCrudRepository().findAll(ActionsInPath.class, parameters);
    }

    @Override
    public void assignButton(String id, List<Action> inserted, List<Action> deleted) {
        if(!deleted.isEmpty()){
            for (Action action : deleted){
                getCrudRepository().delete(Action.class, action.getId());
            }
        }

        if(!inserted.isEmpty()){
            List<ActionsInPath> list = new ArrayList<ActionsInPath>();
            for (Action action : inserted){
                list.add(new ActionsInPath(id, action.getId(), action.getCode(), null));
            }
            getCrudRepository().insert(list);
        }
    }
}
