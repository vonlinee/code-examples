package io.maker.codegen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateModelUtils {

	public <T> Map<String, Object> getListInfo(List<T> list) {
		Map<String, Object> listMetaInfoMap = new HashMap<>();
		listMetaInfoMap.put("list", listMetaInfoMap);
		listMetaInfoMap.put("size", list.size());
		listMetaInfoMap.put("isEmpty", list.isEmpty());
		return listMetaInfoMap;
	}
}
