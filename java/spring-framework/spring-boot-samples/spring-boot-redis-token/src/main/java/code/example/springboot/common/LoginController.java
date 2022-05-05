package code.example.springboot.common;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import code.example.springboot.annotation.AuthToken;

@Controller
public class LoginController {

	@Autowired
	private Md5TokenGenerator tokenGenerator;// token生成器

	@Autowired
	private RedisUtils redisUtils;// redis工具类

	private ResultVo resultVo;

	@GetMapping("") // 去登陆页面
	public String index() {
		return "login";
	}

	/**
	 * 检查登录状态
	 * 
	 * @param request
	 * @return resultVo:已登录：code:0,msg:成功 未登录：返回登录页面
	 */
	@GetMapping("/checkLogin")
	@AuthToken
	@ResponseBody
	public String checkLogin(HttpServletRequest request) {
		return JSON.toJSONString(resultVo);
	}

	/**
	 * 登录校验表单以及验证码
	 * 
	 * @param @RequestBody 请求体Map<String,String>
	 * @param request      获取客户端ip
	 * @return resultVo code=0管理员验证成功，code=1学生验证成功， code=2教师验证成功，code=401账号密码错误，
	 *         code=402验证码错误, data——>token令牌
	 */
	@PostMapping("/login")
	@ResponseBody
	public String login(@RequestBody Map map, HttpServletRequest request) {
		return JSON.toJSONString(resultVo);
	}
}
