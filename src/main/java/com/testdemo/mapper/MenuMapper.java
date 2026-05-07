package com.testdemo.mapper;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MenuMapper {
/** 添加数据 */
int addMenu(PageData pd);
/** 根据id删除数据 */
int deleteMenuId(PageData pd);
/** 根据id更新数据 */
int updateMenu(PageData pd);
/** 获取用户列表数据 不分页*/
List<PageData> queryMenuKey(PageData pd);
/** 获取用户列表数据 分页*/
List<PageData> queryPageMenuKeyList(Page page);}
