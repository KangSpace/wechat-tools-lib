package com._20dot.weixin.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebUtil {
    private static Logger logger = Logger.getLogger(WebUtil.class.getName());

    // 转发请求
    public static void forwardRequest(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(path).forward(request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE,"转发请求出错！", e);
            throw new RuntimeException(e);
        }
    }

    // 重定向请求
    public static void redirectRequest(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(path);//request.getContextPath() +
        } catch (Exception e) {
            logger.log(Level.SEVERE,"重定向请求出错！", e);
            throw new RuntimeException(e);
        }
    }

    // 发送错误代码
    public static void sendError(int code, HttpServletResponse response) {
        try {
            response.sendError(code);
        } catch (Exception e) {
            logger.log(Level.SEVERE,"发送错误代码出错！", e);
            throw new RuntimeException(e);
        }
    }

    // 判断是否为 AJAX 请求
    public static boolean isAJAX(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") != null;
    }

    // 获取请求路径
    public static String getRequestPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = StringUtils.defaultIfEmpty(request.getPathInfo(), "");
        return servletPath + pathInfo;
    }

    // 从 Cookie 中获取数据
    public static String getCookie(HttpServletRequest request, String name) {
        String value = "";
        try {
            Cookie[] cookieArray = request.getCookies();
            if (cookieArray != null) {
                for (Cookie cookie : cookieArray) {
                    if (StringUtils.isNotEmpty(name) && name.equals(cookie.getName())) {
                        value = CodecUtil.urlDecode(cookie.getValue());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,"获取 Cookie 出错！", e);
            throw new RuntimeException(e);
        }
        return value;
    }

    // 设置 Redirect URL 到 Session 中
    public static void setRedirectURL(HttpServletRequest request, String sessionKey) {
        if (!isAJAX(request)) {
            String requestPath = getRequestPath(request);
            request.getSession().setAttribute(sessionKey, requestPath);
        }
    }

    // 是否为 IE 浏览器
    public boolean isIE(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        return agent != null && agent.contains("MSIE");
    }

    /**
     * 是否为微信浏览器请求
     * @param request
     * @return
     */
    public static void validIsWeiXinBroswer(HttpServletRequest request){
        if(!isWeiXinBroswer(request))
            throw new RuntimeException("请在微信客户端打开链接");
    }
    /**
     * 是否为微信浏览器请求
     * @param request
     * @return
     */
    public static boolean isWeiXinBroswer(HttpServletRequest request){
        String agent = request.getHeader("User-Agent");
        return agent != null && agent.contains("MicroMessenger");
    }

    /**
     * 输出alert信息
     * @param msg
     * @param response
     * @throws IOException
     */
    public static void writeAlertMsg(String msg, HttpServletResponse response){
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print("<script type=\"text/javascript\">alert(\""+msg+"\");document.write(\""+msg+"\");document.close();</script>");
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    /**
     * 输出字符串
     * @param msg
     * @param response
     * @throws IOException
     */
    public static void write(String msg, HttpServletResponse response){
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print("<script type=\"text/javascript\">alert(\""+msg+"\");document.write(\""+msg+"\");document.close();</script>");
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
    }
    /**
     * 输出JSON字符串
     * @param jsonMsg
     * @param response
     * @throws IOException
     */
    public static void write(JSONObject jsonMsg, HttpServletResponse response){
        write(jsonMsg.toString(),response);
    }
    /**
     * 输出JSON字符串
     * @param o
     * @param response
     * @throws IOException
     */
    public static void write(Object o, HttpServletResponse response){
        write(JSONObject.fromObject(o),response);
    }


    /**
     * 获取服务器路径
     * @param
     * @Author kango2gler@gmail.com
     * @Date 2017/1/5 16:43
     * @return
     */
    public static String getBasePath(HttpServletRequest request){
        return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
    }

    /**
     * 获取项目路径
     * @param
     * @Author kango2gler@gmail.com
     * @Date 2017/1/5 16:43
     * @return
     */
    public static String getProjectPath(HttpServletRequest request){
        return getBasePath(request)+request.getContextPath()+"/";
    }
    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param domain
     * @param path
     * @param maxAge
     * @param isHttpOnly
     * @Author kango2gler@gmail.com
     * @Date 2017/1/7 19:54
     * @return
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String domain, String path, int maxAge, boolean isHttpOnly){
        StringBuffer sb = new StringBuffer();
        sb.append(name+"="+value).append(";");
        sb.append("Path="+(path!=null?path:"/")).append(";");
        sb.append("Domain="+(domain!=null?domain:"/")).append(";");
        sb.append("Max-Age="+maxAge).append(";");
        if(isHttpOnly)
            sb.append("HttpOnly");
        //response.setHeader("Set-Cookie", "cookiename=value;Path=/;Domain=domainvalue;Max-Age=seconds;HttpOnly");
        response.addHeader("Set-Cookie",sb.toString());
    }

}
