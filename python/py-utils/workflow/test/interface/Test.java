ConfigurableApplicationContext context = app.run(args);
VeMktActAmountDBiz biz = context.getBean(VeMktActAmountDBiz.class);
Map<String, Object> map = new HashMap<>();
map.put("actDateStart", "2021-11-23 08:40:00");
ListResult<Map<String, Object>> result = biz.veMktActAmountDQuery(1, 10, map);
List<Map<String,Object>> rows = result.getRows();
System.out.println(rows);