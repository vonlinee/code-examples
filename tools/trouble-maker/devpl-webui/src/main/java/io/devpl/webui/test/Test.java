package io.devpl.webui.test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.devpl.webui.rest.ListResult;
import io.devpl.webui.rest.ResponseStatus;
import io.devpl.webui.rest.ResultTemplate;
import io.maker.base.utils.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
//        ListResult<Model> list = new ListResult<>();
//        list.setCode(200);
//        list.setDescription("描述信息");
//        list.setMessage("提示信息");
//        list.setData(Lists.asList(new Model(), new Model()));
//        ResultTemplate template = list.getTemplate();
//
//        System.out.println(JSON.toJSONString(template));
//
        Gson gson = new Gson();
//        System.out.println(gson.toJson(template));


        ListResult<Map<String, Object>> builder = ListResult.builder();
        builder.status(ResponseStatus.HTTP_200);
        builder.description("描述信息");
        builder.pageInfo(1, 3);

        List<Map<String, Object>> list = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<>();
            list.add(map);
            map.put("key" + i, "val" + i);
        }

        try {
            int i = 1 / 0;
        } catch (Exception exception) {
            builder.throwable(exception);
        }
        builder.data(list);

        ResultTemplate template = builder.build();

        System.out.println(gson.toJson(template));

        JsonObject returnData = new JsonParser().parse(replyString).getAsJsonObject();
    }
}
