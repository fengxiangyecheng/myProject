package com.testdemo.service.user;

import com.testdemo.common.ServerResponse;
import com.testdemo.config.SecurityConfig.MigrationPasswordEncoder;
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
     * 登录验证：BCrypt 优先，明文密码兼容 + 自动升级
     */
    public PageData getLoginValidation(PageData pd) {
        String username = pd.getString("USERNAME");
        String rawPassword = pd.getString("PASSWORD");
        if (Tools.isEmpty(username) || Tools.isEmpty(rawPassword)) {
            return null;
        }
        // 查询用户（仅按用户名，不在 SQL 中比对密码）
        PageData query = new PageData();
        query.put("USERNAME", username);
        PageData user = sysuserOldMapper.queryByUsername(query);
        if (user == null) {
            return null;
        }
        String storedPwd = user.getString("PASSWORD");
        // BCrypt 校验（兼容明文）
        if (!passwordEncoder.matches(rawPassword, storedPwd)) {
            return null;
        }
        // 明文密码自动升级为 BCrypt
        if (passwordEncoder instanceof MigrationPasswordEncoder
                && ((MigrationPasswordEncoder) passwordEncoder).needsMigration(storedPwd)) {
            PageData upd = new PageData();
            upd.put("USERNAME", username);
            upd.put("PASSWORD", passwordEncoder.encode(rawPassword));
            sysuserOldMapper.updatePassword(upd);
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
