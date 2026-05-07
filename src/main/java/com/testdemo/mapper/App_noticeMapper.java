package com.testdemo.mapper;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface App_noticeMapper {
/** 添加数据 */
int addApp_notice(PageData pd);
/** 根据id删除数据 */
int deleteApp_noticeId(PageData pd);
/** 根据id更新数据 */
int updateApp_notice(PageData pd);
/** 获取用户列表数据 不分页*/
List<PageData> queryApp_noticeKey(PageData pd);
/** 获取用户列表数据 分页*/
List<PageData> queryPageApp_noticeKeyList(Page page);}
