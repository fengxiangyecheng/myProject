package com.testdemo.service;

import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.App_schedule_typeMapper;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class App_schedule_typeService extends BaseService {

    @Autowired
    private App_schedule_typeMapper _mapper;

    @Transactional
    public ServerResponse<String> addApp_schedule_typeNo(PageData pd) {
        if (Tools.isObjEmpty(pd)) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        PageData q = new PageData();
        q.put("type_code", pd.getString("type_code"));
        if (_mapper.queryApp_schedule_typeKey(q).size() > 0) {
            return ServerResponse.createByErrorMessage("类型编码已存在");
        }
        return _mapper.addApp_schedule_type(pd) > 0
                ? ServerResponse.createBySuccessMessage("添加成功")
                : ServerResponse.createByErrorMessage("添加失败");
    }

    public ServerResponse<String> deleteApp_schedule_type(PageData pd) {
        if (Tools.isObjEmpty(pd.get("id"))) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return _mapper.deleteApp_schedule_typeId(pd) > 0
                ? ServerResponse.createBySuccessMessage("删除成功")
                : ServerResponse.createByErrorMessage("删除失败");
    }

    @Transactional
    public ServerResponse<String> updateApp_schedule_type(PageData pd) {
        if (Tools.isEmpty(pd.getString("id"))) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return _mapper.updateApp_schedule_type(pd) > 0
                ? ServerResponse.createBySuccessMessage("修改成功")
                : ServerResponse.createByErrorMessage("修改失败");
    }

    public ServerResponse<List<PageData>> queryApp_schedule_typeKey(PageData pd) {
        if (Tools.isObjEmpty(pd)) {
            return ServerResponse.badArgument();
        }
        return ServerResponse.createBySuccess(_mapper.queryApp_schedule_typeKey(pd));
    }

    public List<PageData> queryPageApp_schedule_typeKeyList(Page page) {
        return _mapper.queryPageApp_schedule_typeKeyList(page);
    }
}
