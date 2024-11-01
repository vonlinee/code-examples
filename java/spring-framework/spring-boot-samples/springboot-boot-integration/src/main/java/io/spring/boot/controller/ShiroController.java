package io.spring.boot.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.spring.boot.common.entity.User;
import io.spring.boot.dao.IUserDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Shiro测试Controller
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年1月13日
 */
@Controller
public class ShiroController {

    private static final Logger logger = LoggerFactory.getLogger(ShiroController.class);

    @Autowired
    private IUserDao userDao;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, HttpSession session, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        session.setAttribute("test", "测试");

        String username = user.getUsername();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        logger.info("从HttpSession中读取Attr : test = {}", currentUser.getSession().getAttribute("test"));
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查  
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应  
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法  
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景  
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        //验证是否登录成功  
        if (currentUser.isAuthenticated()) {
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            return "redirect:/user";
        } else {
            token.clear();
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes) {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        return "redirect:/login";
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        logger.info("------没有权限-------");
        return "403";
    }

    @RequestMapping("/user")
    public String getUserList(Map<String, Object> model, HttpSession session, HttpServletRequest request) {
        SecurityUtils.getSubject();
        SecurityUtils.getSubject();
        SecurityUtils.getSubject();
        request.getSession();
        request.getSession();
        request.getSession();
        request.getSession();

        model.put("userList", userDao.getList());
        return "user";
    }

    @RequestMapping("/user/edit/{userid}")
    public String getUserList(@PathVariable int userid) {
        logger.info("------进入用户信息修改-------");
        return "user_edit";
    }
}
