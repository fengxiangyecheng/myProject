package com.testdemo.controller;

import com.testdemo.base.BaseController;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.service.Operation_logService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

@Api(value = "sys_operation_log", description = "操作日志", tags = "操作日志")
@Controller
@RequestMapping("/sys_operation_log")
public class Sys_operation_logController extends BaseController {

    @Autowired
    private Operation_logService operationLogService;

    private static boolean isSysAdmin(PageData u) {
        return u != null && "1".equals(String.valueOf(u.get("role_manage_id")));
    }

    @RequestMapping(value = "/queryPageLogList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页查询操作日志（系统管理员）")
    public JSONObject queryPageLogList() {
        Page page = getPage();
        PageData pd = putUserPd(getPageData());
        page.setPd(pd);
        if (!isSysAdmin(getUserPd())) {
            page.setTotalResult(0);
            return viewReturnPageData(page, Collections.emptyList());
        }
        List<PageData> list = operationLogService.queryPageLogList(page);
        return viewReturnPageData(page, list);
    }
}
