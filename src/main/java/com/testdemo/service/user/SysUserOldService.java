package com.testdemo.service.user;

import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.user.SysUserOldMapper;
import com.testdemo.util.Tools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SysUserOldService {

    private final SysUserOldMapper sysuserOldMapper;
    private final PasswordEncoder passwordEncoder;

    public SysUserOldService(SysUserOldMapper sysuserOldMapper, PasswordEncoder passwordEncoder) {
        this.sysuserOldMapper = sysuserOldMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 登录验证：先走原始 SQL 比对（兼容明文），失败则尝试 BCrypt
     */
    public PageData getLoginValidation(PageData pd) {
        // 1. 先用原始方式（SQL 中比对用户名+明文密码），兼容全部旧数据
        PageData user = sysuserOldMapper.getLoginValidation(pd);
        if (user != null) {
            return user;
        }
        // 2. 明文比对失败，尝试 BCrypt（密码已加密的情况）
        String username = pd.getString("USERNAME");
        String rawPassword = pd.getString("PASSWORD");
        if (Tools.isEmpty(username) || Tools.isEmpty(rawPassword)) {
            return null;
        }
        PageData query = new PageData();
        query.put("USERNAME", username);
        user = sysuserOldMapper.getSystemUserByColumn(query);
        if (user == null) {
            return null;
        }
        String storedPwd = user.getString("PASSWORD");
        if (!passwordEncoder.matches(rawPassword, storedPwd)) {
            return null;
        }
        return user;
    }


    public ServerResponse registerUser(PageData pd) {

        if(Tools.isEmpty(pd.getString("USERNAME")) || Tools.isEmpty(pd.getString("PASSWORD"))){
           return ServerResponse.badArgument();
        }

        final PageData pageData = new PageData();
        pageData.put("USERNAME", pd.getString("USERNAME"));
        pageData.put("luck", true);
        final PageData systemUserByColumn = sysuserOldMapper.getSystemUserByColumn(pageData);

        if(!Tools.isObjEmpty(systemUserByColumn)){
            return ServerResponse.createByErrorMessage("该用户已存在");
        }

        // 密码 BCrypt 加密后入库
        pd.put("PASSWORD", passwordEncoder.encode(pd.getString("PASSWORD")));
        final int i = sysuserOldMapper.addSystemUser(pd);

        if(i > 0)return ServerResponse.createBySuccessMessage("注册成功");

        return ServerResponse.createByErrorMessage("注册失败");
    }


}
