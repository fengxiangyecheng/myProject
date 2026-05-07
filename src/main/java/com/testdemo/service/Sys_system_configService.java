package com.testdemo.service;

import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.Sys_system_configMapper;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Sys_system_configService extends BaseService {

    @Autowired
    private Sys_system_configMapper _mapper;

    @Transactional
    public ServerResponse<String> addSys_system_configNo(PageData pd) {
        if (Tools.isObjEmpty(pd) || Tools.isEmpty(pd.getString("config_key"))) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        PageData q = new PageData();
        q.put("config_key", pd.getString("config_key").trim());
        if (_mapper.querySys_system_configKey(q).size() > 0) {
            return ServerResponse.createByErrorMessage("配置键已存在");
        }
        pd.put("config_key", q.getString("config_key"));
        return _mapper.addSys_system_config(pd) > 0
                ? ServerResponse.createBySuccessMessage("添加成功")
                : ServerResponse.createByErrorMessage("添加失败");
    }

    public ServerResponse<String> deleteSys_system_config(PageData pd) {
        if (Tools.isObjEmpty(pd.get("id"))) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return _mapper.deleteSys_system_configId(pd) > 0
                ? ServerResponse.createBySuccessMessage("删除成功")
                : ServerResponse.createByErrorMessage("删除失败");
    }

    @Transactional
    public ServerResponse<String> updateSys_system_config(PageData pd) {
        if (Tools.isEmpty(pd.getString("id"))) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        if (!Tools.isEmpty(pd.getString("config_key"))) {
            PageData q = new PageData();
            q.put("config_key", pd.getString("config_key").trim());
            List<PageData> dup = _mapper.querySys_system_configKey(q);
            for (PageData one : dup) {
                if (!String.valueOf(one.get("id")).equals(pd.getString("id"))) {
                    return ServerResponse.createByErrorMessage("配置键已存在");
                }
            }
            pd.put("config_key", q.getString("config_key"));
        }
        return _mapper.updateSys_system_config(pd) > 0
                ? ServerResponse.createBySuccessMessage("修改成功")
                : ServerResponse.createByErrorMessage("修改失败");
    }

    public ServerResponse<List<PageData>> querySys_system_configKey(PageData pd) {
        if (Tools.isObjEmpty(pd)) {
            return ServerResponse.badArgument();
        }
        return ServerResponse.createBySuccess(_mapper.querySys_system_configKey(pd));
    }

    public List<PageData> queryPageSys_system_configKeyList(Page page) {
        return _mapper.queryPageSys_system_configKeyList(page);
    }
}

