package com.testdemo.mapper;

import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface App_schedule_typeMapper {

    int addApp_schedule_type(PageData pd);

    int deleteApp_schedule_typeId(PageData pd);

    int updateApp_schedule_type(PageData pd);

    List<PageData> queryApp_schedule_typeKey(PageData pd);

    List<PageData> queryPageApp_schedule_typeKeyList(Page page);
}
