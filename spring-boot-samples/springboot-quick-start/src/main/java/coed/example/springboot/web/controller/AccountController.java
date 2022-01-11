package coed.example.springboot.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import coed.example.springboot.web.Param;

@RestController
@RequestMapping("/account")
public class AccountController {

	@GetMapping(value = "/map", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> map(Param<String> param) {
		HashMap<String, Object> map = new HashMap<>();
		Gson gson = new Gson();
		String json = gson.toJson(param);
		map.put("json", json);
		return map;
	}
}
