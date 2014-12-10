package com.github.quick4j.upm.roles.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.util.JsonUtils;
import com.github.quick4j.core.web.http.AjaxResponse;
import com.github.quick4j.upm.roles.entity.PathInRole;
import com.github.quick4j.upm.roles.entity.Role;
import com.github.quick4j.upm.roles.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaojh
 */
@Controller
@RequestMapping("/roles")
public class RolesController {
    private static final Logger logger = LoggerFactory.getLogger(RolesController.class);
    private static final String LOCATION = "roles/";

    @Resource
    private RoleService rolesService;

    @RequestMapping(method = RequestMethod.GET)
    public String listing(){
        return LOCATION + "index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String showAddPage(){
        return LOCATION + "new";
    }

    @RequestMapping(
        value = "/new",
        method = RequestMethod.POST,
        produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public AjaxResponse doCreate(Role role){
        rolesService.save(role);
        return new AjaxResponse(true);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String showEditPage(@PathVariable("id") String id){
        return LOCATION + "edit";
    }

    @RequestMapping(
        value = "/{id}/edit",
        method = RequestMethod.POST,
        produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public AjaxResponse doUpdate(Role role){
        rolesService.save(role);
        return new AjaxResponse(true);
    }

    @RequestMapping(
        value = "/{id}/delete",
        method = RequestMethod.GET,
        produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public AjaxResponse doDelete(@PathVariable("id") String id){
        logger.info("delete roles");
        Criteria<Role> criteria = rolesService.createCriteria(Role.class);
        criteria.delete(id);
        return new AjaxResponse(true);
    }

    @RequestMapping(
        value = "/{id}/paths",
        method = RequestMethod.GET
    )
    public String doShowResource(){
        return LOCATION + "paths";
    }

    @RequestMapping(
        value = "/{id}/paths",
        method = RequestMethod.POST,
        produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public AjaxResponse doSaveRoleOfResources(@PathVariable("id") String roleId,
                                         @RequestParam("inserted") String inserted,
                                         @RequestParam("deleted") String deleted){

        List<PathInRole> insertList =
                JsonUtils.formJson(inserted, new TypeReference<List<PathInRole>>(){});
        List<PathInRole> deleteList =
                JsonUtils.formJson(deleted, new TypeReference<List<PathInRole>>(){});

        rolesService.assignResource(roleId, insertList, deleteList);
        return new AjaxResponse(true);
    }
}
