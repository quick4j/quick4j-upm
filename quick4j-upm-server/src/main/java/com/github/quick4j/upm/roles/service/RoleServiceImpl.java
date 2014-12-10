package com.github.quick4j.upm.roles.service;

import com.github.quick4j.core.service.support.SimpleCrudService;
import com.github.quick4j.upm.roles.entity.PathInRole;
import com.github.quick4j.upm.roles.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhaojh
 */
@Component("rolesService")
public class RoleServiceImpl extends SimpleCrudService<Role> implements RoleService{
    @Override
    public void assignResource(String id,
                               List<PathInRole> insertList,
                               List<PathInRole> deleteList) {

        if(!deleteList.isEmpty()){
            for (PathInRole pathInRole : deleteList){
                pathInRole.setRoleId(id);
                getCrudRepository().delete(PathInRole.class, "deleteByRoleIdAndPathIdAndActionId", pathInRole);
            }
        }

        if (!insertList.isEmpty()){
            for(PathInRole pathInRole : insertList){
                pathInRole.setRoleId(id);
            }

            getCrudRepository().insert(insertList);
        }
    }
}
