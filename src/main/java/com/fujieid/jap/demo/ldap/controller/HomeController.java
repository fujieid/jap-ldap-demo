package com.fujieid.jap.demo.ldap.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.context.JapAuthentication;
import com.fujieid.jap.demo.ldap.config.JapConfigContext;
import com.fujieid.jap.http.adapter.jakarta.JakartaRequestAdapter;
import com.fujieid.jap.http.adapter.jakarta.JakartaResponseAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 首页控制器
 */
@Controller
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        toIndex(model, request, response);
        return "index";
    }

    private void toIndex(Model model, HttpServletRequest request, HttpServletResponse response) {
        JapUser japUser = JapAuthentication.getUser(new JakartaRequestAdapter(request), new JakartaResponseAdapter(response));
        if (null != japUser) {
            model.addAttribute("userJson", claimsToJson(japUser));
        }
        model.addAttribute("strategy", JapConfigContext.strategy);
        model.addAttribute("sso", JapConfigContext.sso);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        JapAuthentication.logout(new JakartaRequestAdapter(request), new JakartaResponseAdapter(response));
        return "redirect:/";
    }

    @RequestMapping("/enableSso")
    public String enableSso(Model model, HttpServletRequest request, HttpServletResponse response) {
        JapConfigContext.sso = !JapConfigContext.sso;
        toIndex(model, request, response);
        return "redirect:/";
    }

    private String claimsToJson(JapUser japUser) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // null替换为""
            mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
                @Override
                public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
                    arg1.writeString("");
                }
            });
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(japUser);
        } catch (JsonProcessingException jpe) {
            log.error("Error parsing claims to JSON", jpe);
        }
        return "Error parsing claims to JSON.";
    }

}
