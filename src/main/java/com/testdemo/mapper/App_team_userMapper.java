package com.testdemo.mapper;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface App_team_userMapper {
/** 添加数据 */
int addApp_team_user(PageData pd);
/** 根据id删除数据 */
int deleteApp_team_userId(PageData pd);
/** 根据id更新数据 */
int updateApp_team_user(PageData pd);
/** 获取用户列表数据 不分页*/
List<PageData> queryApp_team_userKey(PageData pd);
/** 获取用户列表数据 分页*/
List<PageData> queryPageApp_team_userKeyList(Page page);}
