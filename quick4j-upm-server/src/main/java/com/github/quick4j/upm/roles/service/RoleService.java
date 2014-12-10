package com.github.quick4j.upm.roles.service;

import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.upm.roles.entity.PathInRole;
import com.github.quick4j.upm.roles.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author zhaojh
 */
public interface RoleService extends CrudService<Role>{
    void assignResource(String id, List<PathInRole> insertList, List<PathInRole> deleteList);
}
