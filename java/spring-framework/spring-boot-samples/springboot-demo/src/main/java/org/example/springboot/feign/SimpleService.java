package org.example.springboot.feign;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import feign.Feign;
import feign.Headers;
import feign.Param;
import feign.Request;
import feign.RequestLine;
import feign.gson.GsonDecoder;

public final class SimpleService {

	public static class Contributor {
		public final String login;
		public final int contributions;

		public Contributor(String login, int contributions) {
			this.login = login;
			this.contributions = contributions;
		}
	}

	interface GitHub {
		@RequestLine(value = "GET /repos/{owner:[a-zA-Z]*}/{repo}/contributors", // 支持正则校验
				decodeSlash = false) // 支持转义字符
		@Headers("Accept: application/json") // 测试添加header
		List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);
	}

	public static void main(String... args) throws IOException {
		// 获取用来访问HTTP API的代理类
		GitHub github = Feign.builder().decoder(new GsonDecoder()) // 返回内容为json格式，所以需要用到json解码器
				.options(new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true)) // 配置超时参数等
				.target(GitHub.class, "https://api.github.com");

		// 像调用方法一样访问HTTP API
		List<Contributor> contributors = github.contributors("OpenFeign", "feign");
		contributors.stream().map(contributor -> contributor.login + " (" + contributor.contributions + ")")
				.forEach(System.out::println);
	}
}