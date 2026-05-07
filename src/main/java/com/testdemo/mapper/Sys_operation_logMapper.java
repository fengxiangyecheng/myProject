package com.testdemo.mapper;

import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sys_operation_logMapper {

    int insertLog(PageData pd);

    List<PageData> queryPageLogList(Page page);
}
