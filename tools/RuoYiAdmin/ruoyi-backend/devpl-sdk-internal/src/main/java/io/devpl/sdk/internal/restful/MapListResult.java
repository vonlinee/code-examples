package io.devpl.sdk.internal.restful;

import java.io.Serializable;
import java.util.Map;

/**
 * 出现此类的原因在于下面两种写法：
 * 1.MapListResult<K, V>
 * 2.ListResult<Map<K, V>>
 * 个人觉得第一种写法看着舒服
 * @param <K>
 * @param <V>
 */
public class MapListResult<K, V> extends ListResult<Map<K, V>> implements Serializable {

}
