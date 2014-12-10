package com.github.quick4j.upm.paths.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.core.util.JsonUtils;
import com.github.quick4j.core.web.http.AjaxResponse;
import com.github.quick4j.upm.actions.entity.Action;
import com.github.quick4j.upm.paths.entity.ActionInPath;
import com.github.quick4j.upm.paths.entity.Path;
import com.github.quick4j.upm.paths.service.PathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh
 */
@Controller
@RequestMapping("/paths")
public class PathsController {
    private static final Logger logger = LoggerFactory.getLogger(PathsController.class);
    private final String LOCATION = "paths/";

    @Resource
    private PathService pathService;

    @Resource
    private CrudService<Action> actionService;

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
    public AjaxResponse doCreate(Path path){
        pathService.save(path);
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
    public AjaxResponse doUpdate(@PathVariable("id") String id, Path path){
        pathService.save(path);
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
        Criteria<Path> criteria = pathService.createCriteria(Path.class);
        criteria.delete(id);
        return new AjaxResponse(AjaxResponse.Status.OK);
    }

    @RequestMapping(value = "/{id}/bound/actions", method = RequestMethod.GET)
    public String doShowActionsInPath(){
        return LOCATION + "actions";
    }

    @RequestMapping(
        value = "/{id}/bound/actions",
        method = RequestMethod.POST,
        produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public AjaxResponse doBoundOrUnBoundActionForPath(@PathVariable("id") String pathid,
                                                    @RequestParam("inserted") String inserted,
                                                    @RequestParam("deleted") String deleted){

        logger.info("===> inserted: {}", inserted);
        logger.info("===> deleted: {}", deleted);

        List<Action> insertedList = JsonUtils.formJson(inserted, new TypeReference<List<Action>>(){});
        List<Action> deletedList = JsonUtils.formJson(deleted, new TypeReference<List<Action>>(){});
        pathService.saveActionAndPathRelation(pathid, insertedList, deletedList);
        return new AjaxResponse(AjaxResponse.Status.OK);
    }

    @RequestMapping(
        value = "/{id}/actions",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @ResponseBody
    public AjaxResponse doFindActionsInPath(@PathVariable("id") String pathid){
        List<Action> actions = pathService.findActionsInPath(pathid);
        return new AjaxResponse(AjaxResponse.Status.OK, actions);
    }
}
