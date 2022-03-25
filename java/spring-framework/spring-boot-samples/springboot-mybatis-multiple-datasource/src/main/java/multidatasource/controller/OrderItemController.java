package multidatasource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import multidatasource.service.OrderItemService;
import multidatasource.service.ProductService;

/**
 * 多数据源事务测试
 */
@RestController
public class OrderItemController {
	@Autowired
	private ProductService ts1;
	@Autowired
	private OrderItemService ts2;

	//localhost:8888/savetest.do
	@RequestMapping("/savetest.do")
	public String savetest() {
		return "success";
	}

	@RequestMapping("/saveteacher.do")
	public String saveteacher() {
		return "success";
	}

	// ########################开始事务测试##########################
	/**
	 * 结果是一个插入进去了，属于非正常现象
	 * 
	 * @return
	 */
	@RequestMapping("/test.do")
	public String test() {
		return "success";
	}

	/**
	 * 结果是两个都没法插入---属于正常现象
	 * 
	 * @return
	 */
	@RequestMapping("/test2.do")
	public String test2() {
		return "success";
	}

	/**
	 * 结果是一个插入进去了，属于非正常现象
	 * 
	 * @return
	 */
	@RequestMapping("/test3.do")
	public String test3() {
		return "success";
	}
}
