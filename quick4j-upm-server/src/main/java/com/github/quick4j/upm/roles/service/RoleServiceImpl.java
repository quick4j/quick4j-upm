package com.github.quick4j.upm.roles.service;

import com.github.quick4j.core.service.support.SimpleCrudService;
import com.github.quick4j.upm.roles.entity.PathsInRoles;
import com.github.quick4j.upm.roles.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhaojh
 */
@Component("rolesService")
public class RoleServiceImpl extends SimpleCrudService<Role, Map> implements RoleService{
    @Override
    public void assignResource(String id,
                               List<PathsInRoles> insertList,
                               List<PathsInRoles> deleteList) {

        if(!deleteList.isEmpty()){
            for (PathsInRoles pathsInRoles : deleteList){
                pathsInRoles.setRoleId(id);
                getCrudRepository().delete(PathsInRoles.class, "deleteByRoleIdAndPathIdAndActionId", pathsInRoles);
            }
        }

        if (!insertList.isEmpty()){
            for(PathsInRoles pathsInRoles : insertList){
                pathsInRoles.setRoleId(id);
            }

            getCrudRepository().insert(insertList);
        }
    }
}
