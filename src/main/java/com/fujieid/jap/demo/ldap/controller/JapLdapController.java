/*
 * Copyright (c) 2020-2040, 北京符节科技有限公司 (support@fujieid.com & https://www.fujieid.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fujieid.jap.demo.ldap.controller;

import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.context.JapAuthentication;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.core.strategy.JapStrategy;
import com.fujieid.jap.demo.ldap.config.JapConfigContext;
import com.fujieid.jap.demo.ldap.util.ViewUtil;
import com.fujieid.jap.http.adapter.jakarta.JakartaRequestAdapter;
import com.fujieid.jap.http.adapter.jakarta.JakartaResponseAdapter;
import com.fujieid.jap.ldap.LdapConfig;
import com.fujieid.jap.ldap.LdapStrategy;
import com.fujieid.jap.sso.config.JapSsoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping("/ldap")
public class JapLdapController {
    @Autowired
    private JapUserService japUserService;

    @GetMapping("/login")
    public String toLogin(HttpServletRequest request, HttpServletResponse response) {
        JapConfigContext.strategy = "ldap";
        if (JapAuthentication.checkUser(new JakartaRequestAdapter(request), new JakartaResponseAdapter(response)).isSuccess()) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView renderAuth(HttpServletRequest request, HttpServletResponse response) {
        JapStrategy ldapStrategy = new LdapStrategy(japUserService, new JapConfig());
        JapResponse japResponse = ldapStrategy.authenticate(new LdapConfig()
                        .setUrl("ldap://localhost:389")
                        .setBindDn("cn=admin,dc=test,dc=com")
                        .setCredentials("123456")
                        .setBaseDn("dc=test,dc=com")
                        .setFilters("(&(objectClass=inetOrgPerson)(uid=%s))")
                        .setTrustStore("")
                        .setTrustStorePassword(""),
                new JakartaRequestAdapter(request),
                new JakartaResponseAdapter(response));
        return ViewUtil.getView(japResponse);
    }
}
