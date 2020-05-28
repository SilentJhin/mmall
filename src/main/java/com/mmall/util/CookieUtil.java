package com.mmall.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = ".happymmall.com";
    private final static String COOKIE_NAME = "mmall_login_token";

    // 把request对象里的所有cookie保存到cookie数组里,遍历数组拿到指定的cookie后返回其值
    public static String readLoginToken (HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    log.info("return cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    // 根据传入的tooken新建cookie对象，将其保存到response对象里
    public static void writeLoginToken (HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/"); // 代表设置在根目录
        ck.setHttpOnly(true); // 不能通过脚本访问cookie
        // 如果maxage不设置，cookie就不会写入硬盘而是写在内存里，只在当前页面有效
        ck.setMaxAge(60 * 60 * 24 * 265);// -1 永久 单位是秒
        log.info("write cookieName:{}, cookieValue: {}", ck.getName(), ck.getValue());
        response.addCookie(ck);
    }

    // 从request里拿到cookie数组，找到指定的cookie，设置删除后添加到response里
    public static void delLoginToken (HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);// 0为删除cookie
                    log.info("del cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }
}
