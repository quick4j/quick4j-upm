package com.github.quick4j.upm.roles.datagrid;

import com.github.quick4j.core.beans.DynamicBean;
import com.github.quick4j.core.mybatis.paging.model.DataPaging;
import com.github.quick4j.core.mybatis.paging.model.PageRequest;
import com.github.quick4j.plugin.datagrid.DataGridPostProcessException;
import com.github.quick4j.plugin.datagrid.support.AbstractDataGridPostProcessor;
import com.github.quick4j.upm.paths.entity.ActionInPath;
import com.github.quick4j.upm.roles.entity.PathInRole;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zhaojh
 */
//@Component
//@DependsOn("pathsInRoleDataGrid")
public class PathsInRoleDataGridPostProcessor extends AbstractDataGridPostProcessor{

    @Override
    public String getName() {
        return "pathsInRole";
    }

    @Override
    public DataPaging process(DataPaging dataPaging, PageRequest<Map<String, Object>> pageRequest) throws DataGridPostProcessException {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        Map<String, Map<String, Object>> tmp = new HashMap<String, Map<String, Object>>();
        List rows = dataPaging.getRows();
        for (Object item : rows){
            Map<String, Object> path = new DynamicBean(item).toMap();
            tmp.put((String) path.get("id"), path);
            data.add(path);
        }

        Map<String, List<Map<String, String>>> actionsInPathMap = getActionsInPerPath(tmp.keySet().toArray(new String[]{}));
        Iterator<String> iterator = actionsInPathMap.keySet().iterator();
        while (iterator.hasNext()){
            String pathid = iterator.next();
            List<Map<String, String>> actions = actionsInPathMap.get(pathid);
            for (Map action : actions){
                tmp.get(pathid).put((String) action.get("code"), "cross");
            }
            tmp.get(pathid).put("actions", actions);
            tmp.get(pathid).put("editable", true);
        }

        String roleId = (String) pageRequest.getParameters().get("roleId");
        List<PathInRole> authorizedPathList = getAuthorizedPaths(roleId);
        for (PathInRole pathInRole : authorizedPathList){
            tmp.get(pathInRole.getPathId()).put(pathInRole.getActionCode(), "check");
        }

        tmp.clear();
        return new DataPaging(data, dataPaging.getTotal());

    }

    private List<PathInRole> getAuthorizedPaths(String roleId){
        PathInRole params = new PathInRole();
        params.setRoleId(roleId);
        return getRepository().findByParams(PathInRole.class, params);
    }

    /**
     * 根据pathid 分类actions
     * @param pathIds
     * @return key:pathid, value: path上对应的actions
     */
    private Map<String, List<Map<String, String>>> getActionsInPerPath(String[] pathIds){

        List<ActionInPath> list = getRepository().findByIds(ActionInPath.class, pathIds);

        Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
        for (ActionInPath actionInPath : list){
            String pathid = actionInPath.getPathId();
            if(map.containsKey(pathid)){
                Map<String, String> action = new HashMap<String, String>();
                action.put("id", actionInPath.getActionId());
                action.put("code", actionInPath.getActionCode());
                map.get(pathid).add(action);
            }else{
                List<Map<String, String>> actionList = new ArrayList<Map<String, String>>();
                Map<String, String> action = new HashMap<String, String>();
                action.put("id", actionInPath.getActionId());
                action.put("code", actionInPath.getActionCode());
                actionList.add(action);
                map.put(pathid, actionList);
            }
        }

        return map;
    }
}
