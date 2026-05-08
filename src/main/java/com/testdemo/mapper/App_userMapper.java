package com.testdemo.mapper;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface App_userMapper {
/** 个人用户登录校验（用户名+密码） */
PageData getLoginValidation(PageData pd);
	PageData queryByUsername(PageData pd);
	int updatePassword(PageData pd);
/** 添加数据 */
int addApp_user(PageData pd);
/** 根据id删除数据 */
int deleteApp_userId(PageData pd);
/** 根据id更新数据 */
int updateApp_user(PageData pd);
/** 仅更新账号状态（禁用/启用），避免动态 if 把 0 当成空跳过 */
int updateApp_userAccountStatus(PageData pd);
/** 获取用户列表数据 不分页*/
List<PageData> queryApp_userKey(PageData pd);
/** 获取用户列表数据 分页*/
List<PageData> queryPageApp_userKeyList(Page page);
/** 用户名重复校验（不查 account_status，避免未打补丁时新增失败） */
int countApp_userByUsername(PageData pd);
/** 编辑时：是否存在其他 id 占用该用户名 */
int countApp_userUsernameOtherThanId(PageData pd);
}
