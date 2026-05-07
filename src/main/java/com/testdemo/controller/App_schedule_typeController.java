package com.testdemo.controller;

import com.testdemo.base.BaseController;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.service.App_schedule_typeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(value = "app_schedule_type", description = "日程类型管理", tags = "日程类型管理")
@Controller
@RequestMapping("/app_schedule_type")
public class App_schedule_typeController extends BaseController {

    @Autowired
    private App_schedule_typeService _service;

    @RequestMapping(value = "/addApp_schedule_typeNo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增日程类型")
    public ServerResponse<String> addApp_schedule_typeNo() {
        return _service.addApp_schedule_typeNo(this.putUserPd(getPageData()));
    }

    @RequestMapping(value = "/deleteApp_schedule_type", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除日程类型")
    public ServerResponse<String> deleteApp_schedule_type() {
        return _service.deleteApp_schedule_type(getPageData());
    }

    @RequestMapping(value = "/updateApp_schedule_type", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新日程类型")
    public ServerResponse<String> updateApp_schedule_type() {
        return _service.updateApp_schedule_type(getPageData());
    }

    @RequestMapping(value = "/queryApp_schedule_typeKey", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询日程类型")
    public ServerResponse<List<PageData>> queryApp_schedule_typeKey() {
        return _service.queryApp_schedule_typeKey(getPageData());
    }

    @RequestMapping(value = "/queryPageApp_schedule_typeKeyList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页查询日程类型")
    public JSONObject queryPageApp_schedule_typeKeyList() {
        PageData pd1 = getPageData();
        PageData pd = this.putUserPd(pd1);
        Page page = getPage();
        page.setPd(pd);
        List<PageData> list = _service.queryPageApp_schedule_typeKeyList(page);
        return viewReturnPageData(page, list);
    }
}
