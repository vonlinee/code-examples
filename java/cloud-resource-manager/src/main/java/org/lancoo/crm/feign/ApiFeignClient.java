package org.lancoo.crm.feign;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huaweicloud.sdk.ces.v1.model.BatchListMetricDataRequest;
import com.huaweicloud.sdk.ces.v1.model.BatchListMetricDataResponse;
import org.lancoo.crm.domain.ServerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@Service
public class ApiFeignClient {

    @Resource
    ObjectMapper objectMapper;

    @Resource
    RestTemplate restTemplate;

    @Value("${server.port}")
    private Integer port;

    public BatchListMetricDataResponse batchQueryMetricData(Long projectId, BatchListMetricDataRequest request) {
        try {
            String apiUrl = "http://localhost:" + port + String.format("/api/V1.0/%s/batch-query-metric-data", projectId);

            ResponseEntity<BatchListMetricDataResponse> responseEntity = restTemplate.postForEntity(URI.create(apiUrl), request, BatchListMetricDataResponse.class);

            return responseEntity.getBody();
        } catch (Exception exception) {
            exception.printStackTrace();
            BatchListMetricDataResponse error = new BatchListMetricDataResponse();
            error.setHttpStatusCode(50);
            return error;
        }
    }

    public ServerResponse getAllServers() {
        return getAllServers(1, 10);
    }

    public ServerResponse getAllServers(Integer pageIndex, Integer pageSize) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        try {
            String apiUrl = "http://localhost:" + port + "/api/servers/getAllServers";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);
            builder.queryParam("page", pageIndex);
            builder.queryParam("size", pageSize);
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为 GET
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json"); // 可选，设置请求头
            // 获取响应
            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            if (responseCode == HttpURLConnection.HTTP_OK) { // 检查响应码
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
            }
            return JSONUtil.toBean(response.toString(), ServerResponse.class);
        } catch (Exception exception) {
            ServerResponse serverResponse = new ServerResponse();
            serverResponse.setErrMsg(exception.toString());
            return serverResponse;
        }
    }
}