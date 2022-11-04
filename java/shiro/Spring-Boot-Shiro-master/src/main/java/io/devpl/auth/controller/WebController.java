package io.devpl.auth.controller;

import io.devpl.auth.bean.Response;
import io.devpl.auth.dao.DataSource;
import io.devpl.auth.util.JWTUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import io.devpl.auth.dao.UserInfo;
import io.devpl.auth.dao.UserService;
import io.devpl.auth.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class WebController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Response login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        UserInfo user = userService.findByUserName(username);
        if (user == null) {
            return new Response(405, "登陆失败", DataSource.getData());
        }
        if (user.getPassword().equals(password)) {
            return new Response(200, "Login success", JWTUtils.sign(username, password));
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/article")
    public Response article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new Response(200, "You are already logged in", null);
        } else {
            return new Response(200, "You are guest", null);
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public Response requireAuth() {
        return new Response(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public Response requireRole() {
        return new Response(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public Response requirePermission() {
        return new Response(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response unauthorized() {
        return new Response(401, "Unauthorized", null);
    }
}
