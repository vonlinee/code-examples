<#list feignclientList as feignclient>

    // ${feignclient.desc}
	@PostMapping("${feignclient.url}")
	EntityResult<BuTestcarPrepare> ${feignclient.methodName}(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
			@RequestBody ParamPage<BuTestcarPrepare> param);
</#list>