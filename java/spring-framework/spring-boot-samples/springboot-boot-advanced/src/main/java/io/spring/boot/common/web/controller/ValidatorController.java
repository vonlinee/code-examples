//package org.springboot.sample.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.validation.Valid;
//
//import org.springboot.sample.entity.ValidatorTest;
//import org.springboot.sample.service.ValidatorTestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * Java验证框架测试
// *
// * @author 单红宇(365384722)
// * @myblog http://blog.csdn.net/catoop/
// * @create 2016年4月14日
// */
//@Controller
//@RequestMapping("/validator")
//public class ValidatorController {
//
//
//    @ModelAttribute("testModel")
//    public ValidatorTest model() {
//        ValidatorTest vtest = new ValidatorTest();
//        vtest.setUsername("shanhy");
//        vtest.setPassword("123456");
//        return vtest;
//    }
//
//    /**
//     * 使用required属性限定参数是否必须
//     *
//     * @param name
//     * @param age
//     * @return
//     * @author SHANHY
//     * @create 2016年4月14日
//     */
//    @RequestMapping("/test1")
//    @ResponseBody
//    public Map<String, String> test1(@RequestParam(required = true) String name,
//                                     @RequestParam(required = false) String age) {
//        Map<String, String> map = new HashMap<>();
//        map.put("name", name);
//        map.put("age", age);
//        return map;
//    }
//
//    @RequestMapping("/test")
//    public String test() {
//        return "validator1";
//    }
//
//
//    /**
//     * 响应到JSP页面
//     *
//     * @param test
//     * @param result 这里的BindResult result必须紧跟着前面的@ModelAttribute， 否则会出错
//     * @return
//     * @author SHANHY
//     * @create 2016年4月14日
//     */
//    @RequestMapping("/test2")
//    public String test2(ValidatorTest test,
//                        BindingResult result, Model model) {
//        model.addAttribute("test", test);
//        return "validator2";
//    }
//
//    /**
//     * 响应到JSP页面
//     *
//     * @param test
//     * @param result 这里的BindingResult必须紧挨着@Valid参数的，即必须紧挨着需要校验的参数，
//     *               这就意味着我们有多少个@Valid参数就需要有多少个对应的Errors参数，它们是一一对应的。
//     * @return
//     * @author SHANHY
//     * @create 2016年4月14日
//     */
//    @RequestMapping("/test3")
//    public String test3(@Valid @ModelAttribute("testModel") ValidatorTest test,
//                        BindingResult result, Model model) {
//        model.addAttribute("test", test);
//        if (result.hasErrors())
//            return "validator1";
//        return "validator2";
//    }
//
//    @RequestMapping("/test6")
//    @ResponseBody
//    public Model test6(@Valid @ModelAttribute("testModel") ValidatorTest test,
//                       BindingResult result, Model model) {
//        model.addAttribute("test", test);
//        // 在实际开发中，我们需要判断是否存在错误，来决定是继续执行后续代码，还是跳转到别的页面
//        if (result.hasErrors()) {
//            model.addAttribute("error", "验证不通过!");
//        }
//        return model;
//    }
//
//    /**
//     * 基础数据类型验证
//     *
//     * @param name
//     * @param model
//     * @return
//     * @author SHANHY
//     * @create 2016年4月29日
//     */
//    @RequestMapping("/test4")
//    @ResponseBody
//    public Model test4(String name, Model model) {
//        model.addAttribute("name", name);
//        return model;
//    }
//
//    /**
//     * 测试方法级别的验证（如果验证失败，则会抛出异常 ConstraintViolationException）
//     *
//     * @param name
//     * @param model
//     * @return
//     * @author SHANHY
//     * @create 2016年4月17日
//     */
//    @RequestMapping("/test5")
//    @ResponseBody
//    public Model test5(String name, String password, Model model) {
//        try {
////            String content = validatorTestService.getContent(name, password);
//            String content = "";
//            model.addAttribute("name", content);
//        } catch (ConstraintViolationException e) {
//            addErrorMessage(model, e);
//        }
//        return model;
//    }
//
//    /**
//     * 添加错误消息，建议将该方法提取为一个公共的方法使用。
//     *
//     * @param model
//     * @param e
//     * @author SHANHY
//     * @create 2016年5月4日
//     */
//    protected void addErrorMessage(Model model, ConstraintViolationException e) {
//        Map<String, String> errorMsg = new HashMap<>();
//        model.addAttribute("errorMsg", errorMsg);
//
//        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
//            // 获得验证失败的类 constraintViolation.getLeafBean()
//            // 获得验证失败的值 constraintViolation.getInvalidValue()
//            // 获取参数值 constraintViolation.getExecutableParameters()
//            // 获得返回值 constraintViolation.getExecutableReturnValue()
//            errorMsg.put(constraintViolation.getLeafBean().getClass().getName() + "-" + constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
//        }
//    }
//}
