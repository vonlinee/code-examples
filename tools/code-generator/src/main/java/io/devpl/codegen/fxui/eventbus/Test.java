package io.devpl.codegen.fxui.eventbus;

import com.google.common.eventbus.EventBus;

/**
 * <p>
 * 一：发布订阅模式
 * 发布者-发布事件（事件即方法参数），不使用继承方式，非侵入性
 * 订阅者(方法) - 名称唯一，至少有一个参数
 * <p>
 * 二：事件过滤
 * 根据参数过滤：参数类型，参数个数
 * 根据方法名过滤
 * 根据方法所在对象过滤
 * 根据指定字符串唯一标识符过滤
 * <p>
 * 三：事件回调
 * 根据订阅者方法的返回值
 * 四：手动注册
 */
public class Test {

    public static void main(String[] args) {
        EventBus bus = new EventBus();

        bus.register(new Test());
    }

    @Subscribe
    public void method() {

    }
}
