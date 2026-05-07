package com.testdemo.mapper;

import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sys_system_configMapper {

    int addSys_system_config(PageData pd);

    int deleteSys_system_configId(PageData pd);

    int updateSys_system_config(PageData pd);

    List<PageData> querySys_system_configKey(PageData pd);

    List<PageData> queryPageSys_system_configKeyList(Page page);
}

