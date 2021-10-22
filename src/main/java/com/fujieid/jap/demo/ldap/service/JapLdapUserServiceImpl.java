package com.fujieid.jap.demo.ldap.service;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.ldap.model.LdapPerson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 适用于 jap-social 模块，实现 getByPlatformAndUid 和 createAndGetSocialUser 方法，如果需要sso登录，则必须实现 getById 方法
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 14:09
 * @since 1.0.0
 */
@Service
public class JapLdapUserServiceImpl implements JapUserService {

    /**
     * 模拟 DB 操作
     */
    private static final List<JapUser> userDatas = new ArrayList<>();

    /**
     * Save the ldap user information to the database and return JapUser
     * <p>
     * It is suitable for the {@code jap-ldap} module
     *
     * @param userInfo User information obtained through ldap login, type {@code com.fujieid.jap.ldap.model.LdapPerson}
     * @return When saving successfully, return {@code JapUser}, otherwise return {@code null}
     * @since 1.0.6
     */
    @Override
    public JapUser createAndGetLdapUser(Object userInfo) {
        // 转换 object 为 LdapPerson
        LdapPerson ldapPerson = (LdapPerson) userInfo;
        // 将 ldapPerson 持久化到数据库中或者其他媒介中
        JapUser japUser = new JapUser();
        japUser.setUserId(ldapPerson.getUid());
        japUser.setUsername(ldapPerson.getUid());
        // 此处仅作演示，保存的是加密后的密码
        japUser.setPassword(ldapPerson.getPassword());
        japUser.setAdditional(ldapPerson);
        userDatas.add(japUser);
        return japUser;
    }
}
