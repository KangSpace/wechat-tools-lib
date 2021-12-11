package org.kangspace.wechat.util;
import org.apache.commons.lang.StringUtils;
import org.kangspace.wechat.util.encryption.CodecUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebUtil {
    private static Logger logger = Logger.getLogger(WebUtil.class.getName());

    private static boolean checkParamName(String paramName) {
        return !paramName.equals("_"); // 忽略 jQuery 缓存参数
    }

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

    // 创建验证码
    public static String createCaptcha(HttpServletResponse response) {
        StringBuilder captcha = new StringBuilder();
        try {
            // 参数初始化
            int width = 60;                      // 验证码图片的宽度
            int height = 25;                     // 验证码图片的高度
            int codeCount = 4;                   // 验证码字符个数
            int codeX = width / (codeCount + 1); // 字符横向间距
            int codeY = height - 4;              // 字符纵向间距
            int fontHeight = height - 2;         // 字体高度
            int randomSeed = 10;                 // 随机数种子
            char[] codeSequence = {              // 验证码中可出现的字符
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            };
            // 创建图像
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            // 将图像填充为白色
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            // 设置字体
            g.setFont(new Font("Courier New", Font.BOLD, fontHeight));
            // 绘制边框
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, width - 1, height - 1);
            // 产生随机干扰线（160条）
            g.setColor(Color.WHITE);
            // 创建随机数生成器
            Random random = new Random();
            for (int i = 0; i < 160; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                g.drawLine(x, y, x + xl, y + yl);
            }
            // 生成随机验证码
            int red, green, blue;
            for (int i = 0; i < codeCount; i++) {
                // 获取随机验证码
                String validateCode = String.valueOf(codeSequence[random.nextInt(randomSeed)]);
                // 随机构造颜色值
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);
                // 将带有颜色的验证码绘制到图像中
                g.setColor(new Color(red, green, blue));
                g.drawString(validateCode, (i + 1) * codeX - 6, codeY);
                // 将产生的随机数拼接起来
                captcha.append(validateCode);
            }
            // 禁止图像缓存
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            // 设置响应类型为 JPEG 图片
            response.setContentType("image/jpeg");
            // 将缓冲图像写到 Servlet 输出流中
            ServletOutputStream sos = response.getOutputStream();
            ImageIO.write(bi, "jpeg", sos);
            sos.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"创建验证码出错！", e);
            throw new RuntimeException(e);
        }
        return captcha.toString();
    }

    // 是否为 IE 浏览器
    public boolean isIE(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        return agent != null && agent.contains("MSIE");
    }

    /**
     * 是否为微信浏览器请求
     * @param request request
     */
    public static void validIsWeiXinBroswer(HttpServletRequest request){
        if (!isWeiXinBroswer(request)) {
            throw new RuntimeException("请在微信客户端打开链接");
        }
    }
    /**
     * 是否为微信浏览器请求
     * @param request request
     * @return boolean
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
            response.setCharacterEncoding("UTF-8");
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
     */
    public static void write(String msg, HttpServletResponse response){
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(msg);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    /**
     * 获取服务器路径
     * @param request request
     * @author kango2gler@gmail.com
     * @since 2017/1/5 16:43
     * @return String
     */
    public static String getBasePath(HttpServletRequest request){
        return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
    }

    /**
     * 获取项目路径
     * @param
     * @author kango2gler@gmail.com
     * @since 2017/1/5 16:43
     * @return String
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
     * @author kango2gler@gmail.com
     * @since 2017/1/7 19:54
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String domain, String path, int maxAge, boolean isHttpOnly){
        StringBuffer sb = new StringBuffer();
        sb.append(name+"="+value).append(";");
        sb.append("Path="+(path!=null?path:"/")).append(";");
        sb.append("Domain="+(domain!=null?domain:"/")).append(";");
        sb.append("Max-Age="+maxAge).append(";");
        if (isHttpOnly) {
            sb.append("HttpOnly");
        }
        //response.setHeader("Set-Cookie", "cookiename=value;Path=/;Domain=domainvalue;Max-Age=seconds;HttpOnly");
        response.addHeader("Set-Cookie",sb.toString());
    }

}
