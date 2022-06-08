package io.maker.codegen.web.service.external;

import java.util.Map;

import org.apache.poi.ss.formula.functions.Columns;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import io.maker.base.rest.ListResult;

@FeignClient(name = "IFeignClient", url = "${refer.url.codegen}")
public interface IFeignClient {

	@PostMapping("/mysql/meta/informationschema/queryColumns.do")
	ListResult<Columns> queryColumns(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestBody(required = false) Map<String, Object> dateInfo);

	@PostMapping("/mysql/meta/informationschema/queryColumns.do")
	ListResult<Columns> queryColumns(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestBody(required = false) Map<String, Object> dateInfo);

}
