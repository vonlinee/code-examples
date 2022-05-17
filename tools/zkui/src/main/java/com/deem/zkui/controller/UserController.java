package com.deem.zkui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user/system", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public Map<String, Object> login() {
        return new HashMap<>();
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        logger.debug("UserController Action!");
//        try {
//            Properties globalProps = (Properties) getServletContext().getAttribute("globalProps");
//            Map<String, Object> templateParam = new HashMap<>();
//            templateParam.put("uptime", globalProps.getProperty("uptime"));
//            templateParam.put("loginMessage", globalProps.getProperty("loginMessage"));
//            ServletUtil.INSTANCE.renderHtml(request, response, templateParam, "login.ftl.html");
//        } catch (TemplateException ex) {
//            logger.error(Arrays.toString(ex.getStackTrace()));
//            ServletUtil.INSTANCE.renderError(request, response, ex.getMessage());
//        }
//    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        logger.debug("UserController Post Action!");
//        try {
//            Properties globalProps = (Properties) getServletContext().getAttribute("globalProps");
//            Map<String, Object> templateParam = new HashMap<>();
//            HttpSession session = request.getSession(true);
//            session.setMaxInactiveInterval(Integer.valueOf(globalProps.getProperty("sessionTimeout")));
//            //TODO: Implement custom authentication logic if required.
//            String username = request.getParameter("username");
//            String password = request.getParameter("password");
//            String role = null;
//            Boolean authenticated = false;
//            //if ldap is provided then it overrides roleset.
//            if (globalProps.getProperty("ldapAuth").equals("true")) {
//                authenticated = new LdapAuth().authenticateUser(globalProps.getProperty("ldapUrl"), username, password, globalProps.getProperty("ldapDomain"));
//                if (authenticated) {
//                    JSONArray jsonRoleSet = (JSONArray) ((JSONObject) new JSONParser().parse(globalProps.getProperty("ldapRoleSet"))).get("users");
//                    for (Iterator it = jsonRoleSet.iterator(); it.hasNext(); ) {
//                        JSONObject jsonUser = (JSONObject) it.next();
//                        if (jsonUser.get("username") != null && jsonUser.get("username").equals("*")) {
//                            role = (String) jsonUser.get("role");
//                        }
//                        if (jsonUser.get("username") != null && jsonUser.get("username").equals(username)) {
//                            role = (String) jsonUser.get("role");
//                        }
//                    }
//                    if (role == null) {
//                        role = ZooKeeperUtil.ROLE_USER;
//                    }
//
//                }
//            } else {
//                JSONArray jsonRoleSet = (JSONArray) ((JSONObject) new JSONParser().parse(globalProps.getProperty("userSet"))).get("users");
//                for (Iterator it = jsonRoleSet.iterator(); it.hasNext(); ) {
//                    JSONObject jsonUser = (JSONObject) it.next();
//                    if (jsonUser.get("username").equals(username) && jsonUser.get("password").equals(password)) {
//                        authenticated = true;
//                        role = (String) jsonUser.get("role");
//                    }
//                }
//            }
//            if (authenticated) {
//                logger.info("UserController successful: " + username);
//                session.setAttribute("authName", username);
//                session.setAttribute("authRole", role);
//                response.sendRedirect("/home");
//            } else {
//                session.setAttribute("flashMsg", "Invalid UserController");
//                ServletUtil.INSTANCE.renderHtml(request, response, templateParam, "login.ftl.html");
//            }
//        } catch (ParseException | TemplateException ex) {
//            logger.error(Arrays.toString(ex.getStackTrace()));
//            ServletUtil.INSTANCE.renderError(request, response, ex.getMessage());
//        }
//    }
}
