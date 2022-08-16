package code.example.springboot.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class HttpTraceLog {
    private StringBuffer URL; //请求路径
    private String method;  //请求方法
    private String startTime; //请求开始时间
    private String parameterMap;  //请求参数
    private String requestBody; //请求体
    private String responseBody; //响应
}