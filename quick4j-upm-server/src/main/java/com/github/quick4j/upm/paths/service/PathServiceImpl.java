package com.github.quick4j.upm.paths.service;

import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.support.SimpleCrudService;
import com.github.quick4j.upm.actions.entity.Action;
import com.github.quick4j.upm.paths.entity.ActionInPath;
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
public class PathServiceImpl extends SimpleCrudService<Path> implements PathService{
    @Override
    public void saveActionAndPathRelation(String id, List<Action> insertedActions, List<Action> deletedActions) {
        if(!deletedActions.isEmpty()){
            Criteria<ActionInPath> actionInPathCriteria = createCriteria(ActionInPath.class);
            for (Action action : deletedActions){
                ActionInPath params = new ActionInPath();
                params.setPathId(id);
                params.setActionId(action.getId());
                actionInPathCriteria.delete(params);
            }
        }

        if(!insertedActions.isEmpty()){
            Criteria<Path> pathCriteria = createCriteria(Path.class);
            Path path = pathCriteria.findOne(id);
            List<ActionInPath> list = new ArrayList<ActionInPath>();
            for (Action action : insertedActions){
                list.add(new ActionInPath(action, path));
            }

            getCrudRepository().insert(list);
        }
    }

    @Override
    public List<Action> findActionsInPath(String id) {
        ActionInPath param = new ActionInPath();
        param.setPathId(id);

        Criteria<ActionInPath> criteria = createCriteria(ActionInPath.class);
        List<ActionInPath> actionInPaths = criteria.findAll(param);

        List<String> actionIds = new ArrayList<String>();
        for (ActionInPath actionInPath : actionInPaths){
            actionIds.add(actionInPath.getActionId());
        }

        Criteria<Action> actionCriteria = createCriteria(Action.class);
        return actionCriteria.findAll(actionIds.toArray(new String[]{}));
    }
}
