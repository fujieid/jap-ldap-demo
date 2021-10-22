package com.fujieid.jap.demo.ldap.util;

import cn.hutool.core.util.URLUtil;
import com.fujieid.jap.core.result.JapResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021-03-03 18:35
 * @since 1.0.0
 */
public class ViewUtil {

    public static ModelAndView getView(JapResponse japResponse) {
        if (!japResponse.isSuccess()) {
            return new ModelAndView(new RedirectView("/?error=" + URLUtil.encode(japResponse.getMessage())));
        }
        if (japResponse.isRedirectUrl()) {
            return new ModelAndView(new RedirectView((String) japResponse.getData()));
        } else {
            return new ModelAndView(new RedirectView("/"));
        }
    }
}
