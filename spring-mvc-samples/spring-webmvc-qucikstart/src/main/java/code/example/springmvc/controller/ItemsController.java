package code.example.springmvc.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import code.example.springmvc.pojo.Items;

//org.springframework.web.servlet.mvc.Controller
public class ItemsController implements Controller {

	//需要添加Server Runtime:这里我选的是Tomcat 8.5
	//或者在maven中添加 servlet-api，servlet-jsp，jstl等依赖，因为这些依赖的作用范围都是Runtime
	//否则会提示HttpServletRequest，HttpServletResponse找不到
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //调用service查找数据库，查询商品列表，这里使用静态数据模拟
        List<Items> itemsList = new ArrayList<Items>();
        //向list中填充静态数据
        Items items_1 = new Items();
        items_1.setName("联想笔记本");
        items_1.setPrice(6000f);
        items_1.setDetail("X Carbon");
        Items items_2 = new Items();
        items_2.setName("苹果手机");
        items_2.setPrice(5000f);
        items_2.setDetail("iphone7");

        itemsList.add(items_1);
        itemsList.add(items_2);
        //返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //相当于request的setAttribute方法,在jsp页面中通过itemsList取数据
        modelAndView.addObject("itemsList", itemsList);
        //指定视图
        modelAndView.setViewName("/items/itemsList");
        return modelAndView;
    }
}
