package multidatasource.controller;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import multidatasource.entity.OrderItem;
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
	private OrderItemService orderItemService;

	// localhost:8888/savetest.do
	@RequestMapping("/saveone.do")
	public String saveOne() {
		OrderItem oi = new OrderItem();
		oi.setOrderNum(new Random().nextInt());
		oi.setOrderItem(new Random().nextInt());
		oi.setProdId(UUID.randomUUID().toString());
		oi.setItemPrice(BigDecimal.valueOf(10));
		oi.setQuantity(new Random().nextInt());
		orderItemService.saveOne(oi);
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
