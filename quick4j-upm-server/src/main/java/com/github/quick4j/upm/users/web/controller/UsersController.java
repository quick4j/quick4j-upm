package com.github.quick4j.upm.users.web.controller;

import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.core.web.http.AjaxResponse;
import com.github.quick4j.upm.users.entity.User;
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
 * @author zhaojh.
 */
@Controller
@RequestMapping("/users")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private static final String LOCATION = "users/";
    @Resource
    private CrudService<User> simpleCrudService;

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
    public AjaxResponse doCreate(User user){
        simpleCrudService.save(user);
        return new AjaxResponse(true);
    }

    @RequestMapping(
        value = "/{id}/delete",
        method = RequestMethod.GET,
        produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public AjaxResponse doDelete(@PathVariable("id") String id){
        Criteria<User> criteria = simpleCrudService.createCriteria(User.class);
        criteria.delete(id);
        return new AjaxResponse(true);
    }
}
