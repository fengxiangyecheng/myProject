package com.testdemo.controller;

import com.testdemo.base.BaseController;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.service.Sys_system_configService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(value = "sys_system_config", description = "系统配置", tags = "系统配置")
@Controller
@RequestMapping("/sys_system_config")
public class Sys_system_configController extends BaseController {

    @Autowired
    private Sys_system_configService _service;

    @RequestMapping(value = "/addSys_system_configNo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增系统配置")
    public ServerResponse<String> addSys_system_configNo() {
        return _service.addSys_system_configNo(this.putUserPd(getPageData()));
    }

    @RequestMapping(value = "/deleteSys_system_config", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除系统配置")
    public ServerResponse<String> deleteSys_system_config() {
        return _service.deleteSys_system_config(getPageData());
    }

    @RequestMapping(value = "/updateSys_system_config", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新系统配置")
    public ServerResponse<String> updateSys_system_config() {
        return _service.updateSys_system_config(getPageData());
    }

    @RequestMapping(value = "/querySys_system_configKey", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询系统配置")
    public ServerResponse<List<PageData>> querySys_system_configKey() {
        return _service.querySys_system_configKey(getPageData());
    }

    @RequestMapping(value = "/queryPageSys_system_configKeyList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页查询系统配置")
    public JSONObject queryPageSys_system_configKeyList() {
        PageData pd1 = getPageData();
        PageData pd = this.putUserPd(pd1);
        Page page = getPage();
        page.setPd(pd);
        List<PageData> list = _service.queryPageSys_system_configKeyList(page);
        return viewReturnPageData(page, list);
    }
}

