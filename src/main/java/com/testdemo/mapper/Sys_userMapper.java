package com.testdemo.mapper;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface Sys_userMapper {
/** 添加数据 */
int addSys_user(PageData pd);
/** 根据id删除数据 */
int deleteSys_userId(PageData pd);
/** 根据id更新数据 */
int updateSys_user(PageData pd);
/** 获取用户列表数据 不分页*/
List<PageData> querySys_userKey(PageData pd);
/** 获取用户列表数据 分页*/
List<PageData> queryPageSys_userKeyList(Page page);}
