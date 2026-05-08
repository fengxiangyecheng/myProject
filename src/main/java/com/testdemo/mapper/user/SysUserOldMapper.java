package com.testdemo.mapper.user;

import com.testdemo.entity.system.PageData;

public interface SysUserOldMapper {

    /**
     * 验证用户账号及密码是否存在
     */
    PageData getLoginValidation(PageData pd);

    int addSystemUser(PageData pd);

    PageData getSystemUserByColumn(PageData pd);

    /** 仅按用户名查询（不含密码匹配，用于 BCrypt 校验） */
    PageData queryByUsername(PageData pd);

    /** 更新密码字段 */
    int updatePassword(PageData pd);

}
