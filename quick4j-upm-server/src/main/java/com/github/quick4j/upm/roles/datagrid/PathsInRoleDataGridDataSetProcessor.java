package com.github.quick4j.upm.roles.datagrid;

import com.github.quick4j.core.beans.DynamicBean;
import com.github.quick4j.core.mybatis.paging.model.DataPaging;
import com.github.quick4j.core.mybatis.paging.model.PageRequest;
import com.github.quick4j.plugin.datagrid.DataSetProcessException;
import com.github.quick4j.plugin.datagrid.support.AbstractDataSetProcessor;
import com.github.quick4j.upm.paths.entity.ActionInPath;
import com.github.quick4j.upm.paths.entity.Path;
import com.github.quick4j.upm.roles.entity.PathInRole;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zhaojh
 */
@Component
@DependsOn("pathsInRoleDataGrid")
public class PathsInRoleDataGridDataSetProcessor extends AbstractDataSetProcessor{

    @Override
    public String getName() {
        return "pathsInRole";
    }

    @Override
    public DataPaging process(DataPaging dataPaging, PageRequest<Map<String, Object>> pageRequest) throws DataSetProcessException {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        Map<String, Map<String, Object>> tmp = new HashMap<String, Map<String, Object>>();
        List<Path> rows = dataPaging.getRows();
        for (Path path : rows){
            Map<String, Object> pathMap = new DynamicBean(path).toMap();
            tmp.put(path.getId(), pathMap);
            data.add(pathMap);
        }

        Map<String, List<Map<String, String>>> withActionOfPathOfMap = findActionsForEveryPath(tmp.keySet().toArray(new String[]{}));

        Set<String> editablePathIdSet = withActionOfPathOfMap.keySet();
        for (String pathid : editablePathIdSet){
            List<Map<String, String>> actions = withActionOfPathOfMap.get(pathid);
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
     * @return key:pathid, value: 绑定在这个pathid所代表的path上的action列表
     */
    private Map<String, List<Map<String, String>>> findActionsForEveryPath(String[] pathIds){

        List<ActionInPath> list = getRepository().findAll(ActionInPath.class, "selectAllByPathIds", pathIds);

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
