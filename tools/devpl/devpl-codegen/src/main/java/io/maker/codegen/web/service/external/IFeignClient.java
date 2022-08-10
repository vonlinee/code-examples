package io.maker.codegen.web.service.external;

import java.util.Map;

import io.maker.base.rest.ListResult;
import io.maker.codegen.core.db.meta.schema.ColumnInfoSchema;
import io.maker.codegen.core.db.meta.schema.TableInfoSchema;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "IFeignClient", url = "${refer.url.codegen}")
public interface IFeignClient {

    @PostMapping("/mysql/meta/informationschema/queryColumns.do")
    ListResult<ColumnInfoSchema> queryColumns(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestBody(required = false) Map<String, Object> dateInfo);

    @PostMapping("/mysql/meta/informationschema/queryColumns.do")
    ListResult<TableInfoSchema> queryTables(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestBody(required = false) Map<String, Object> dateInfo);
}
