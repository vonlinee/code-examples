package io.devpl.sdk.beans.impl;

import io.devpl.sdk.beans.DynamicBean;
import io.devpl.sdk.beans.impl.flexi.FlexiBean;

import java.util.HashMap;

/**
 * 基于Map结构实现DynamicBean
 * 属性是动态的，可以在Map中随意添加和删除
 * 实现可以选择继承Map或者采用其它组合方式
 * <p>
 * This class extends {@link HashMap}, allowing it to be used wherever a map is.
 * See {@link FlexiBean} for a map-like bean implementation that is more controlled.
 */
public interface MapBean extends DynamicBean {

}
