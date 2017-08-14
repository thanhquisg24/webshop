package com.shopping.admin.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.core.model.common.EntityList;

@Controller
public class DashBoardController {
	
    @RequestMapping({"admin","/admin/dashboad"})
    public String index(Model model) {
     
        return "admin/Dashboard/index";
    }
    @RequestMapping({"/admin/dashboad2"})
    public String hello(Model model) {
     
        return "admin/Dashboard/indexFullBackup";
    }
    @RequestMapping({"/admin/blank"})
    public String blank(Model model) {
     
        return "admin/Dashboard/blank";
    }
    @RequestMapping({"/admin/testjson"})
    @ResponseBody
    public EntityList testJson() {
    	EntityList l=new EntityList();
    	l.setTotalCount(200);
       return l;
    }
}
