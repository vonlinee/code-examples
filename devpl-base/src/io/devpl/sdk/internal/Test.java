package io.devpl.sdk.internal;

import java.util.ArrayList;

import javax.management.modelmbean.ModelMBean;

import com.google.gson.Gson;

import io.devpl.sdk.internal.rest.ResponseStatus;
import io.devpl.sdk.internal.rest.Result;
import io.devpl.sdk.internal.rest.ResultTemplate;

public class Test {
	
	public static void main(String[] args) {
		
		Result result = ResultTemplate.list(ModelMBean.class)
				.code(200)
				.message("1111")
				.status(ResponseStatus.HTTP_200)
				.build();
		
		Gson gson = new Gson();
		
		String json = gson.toJson(result);
		
		System.out.println(json);
		
	}
}
