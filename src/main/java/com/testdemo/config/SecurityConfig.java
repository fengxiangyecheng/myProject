package com.testdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().permitAll()
            .and()
                .csrf().disable()
                .headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MigrationPasswordEncoder();
    }

    /**
     * 密码迁移编码器：BCrypt 优先，兼容明文密码自动升级
     */
    public static class MigrationPasswordEncoder implements PasswordEncoder {

        private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        @Override
        public String encode(CharSequence rawPassword) {
            return bcrypt.encode(rawPassword);
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            if (rawPassword == null || encodedPassword == null) {
                return false;
            }
            // 已是 BCrypt 格式 → BCrypt 校验
            if (encodedPassword.startsWith("$2a$")) {
                return bcrypt.matches(rawPassword, encodedPassword);
            }
            // 明文密码 → 直接比较（兼容旧数据）
            return rawPassword.toString().equals(encodedPassword);
        }

        /** 判断密码是否需要迁移为 BCrypt */
        public boolean needsMigration(String encodedPassword) {
            return encodedPassword == null || !encodedPassword.startsWith("$2a$");
        }
    }
}
