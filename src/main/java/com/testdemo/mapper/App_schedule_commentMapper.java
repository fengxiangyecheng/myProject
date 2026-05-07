package com.testdemo.mapper;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface App_schedule_commentMapper {
/** 添加数据 */
int addApp_schedule_comment(PageData pd);
/** 根据id删除数据 */
int deleteApp_schedule_commentId(PageData pd);
/** 根据id更新数据 */
int updateApp_schedule_comment(PageData pd);
/** 获取用户列表数据 不分页*/
List<PageData> queryApp_schedule_commentKey(PageData pd);
/** 获取用户列表数据 分页*/
List<PageData> queryPageApp_schedule_commentKeyList(Page page);}
