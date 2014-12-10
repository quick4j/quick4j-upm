package com.github.quick4j.upm.actions.web.controller;

import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.core.web.http.AjaxResponse;
import com.github.quick4j.upm.actions.entity.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zhaojh
 */
@Controller
@RequestMapping("/actions")
public class ActionsController {
    private static final Logger logger = LoggerFactory.getLogger(ActionsController.class);
    private final String LOCATION = "actions/";

    @Resource
    private CrudService<Action> simpleCrudService;

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
    public AjaxResponse doCreate(Action action){
        simpleCrudService.save(action);
        return new AjaxResponse(AjaxResponse.Status.OK);
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
    public AjaxResponse doUpdate(@PathVariable("id") String id, Action action){
        simpleCrudService.save(action);
        return new AjaxResponse(AjaxResponse.Status.OK);
    }

    @RequestMapping(
        value = "/{id}/delete",
        method = RequestMethod.GET,
        produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public AjaxResponse doDelete(@PathVariable("id") String id){
        logger.info("delete actions");
        Criteria<Action> criteria = simpleCrudService.createCriteria(Action.class);
        criteria.delete(id);
        return new AjaxResponse(AjaxResponse.Status.OK);
    }
}
