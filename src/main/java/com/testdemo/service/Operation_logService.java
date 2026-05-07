package com.testdemo.service;

import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.Sys_operation_logMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class Operation_logService extends BaseService {

    @Autowired
    private Sys_operation_logMapper logMapper;

    public void saveLog(String userId, String username, String action, String detail, String ip) {
        try {
            PageData pd = new PageData();
            pd.put("user_id", userId);
            pd.put("username", username);
            pd.put("action", action);
            pd.put("detail", detail != null && detail.length() > 450 ? detail.substring(0, 450) : detail);
            pd.put("ip", ip);
            logMapper.insertLog(pd);
        } catch (Exception ignored) {
            // 日志失败不影响主流程
        }
    }

    public List<PageData> queryPageLogList(Page page) {
        try {
            return logMapper.queryPageLogList(page);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
