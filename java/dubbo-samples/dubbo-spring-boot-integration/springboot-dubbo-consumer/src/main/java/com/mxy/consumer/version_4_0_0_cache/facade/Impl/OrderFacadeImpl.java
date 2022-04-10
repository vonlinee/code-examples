package com.mxy.consumer.version_4_0_0_cache.facade.Impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.mxy.api.facade.GoodsFacade;
import com.mxy.api.facade.dto.GoodsDto;
import com.mxy.consumer.version_2_0_0_asyn.domain.Goods;
import com.mxy.consumer.version_2_0_0_asyn.domain.Order;
import com.mxy.consumer.version_2_0_0_asyn.facade.OrderFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderFacadeImpl implements OrderFacade {


    /**
     * 1. lru 基于最近最少使用原则删除多余缓存，保持最热的数据被缓存。
     * 2. threadlocal 当前线程缓存，比如一个页面渲染，用到很多 portal，每个 portal 都要去查用户信息，通过线程缓存，可以减少这种多余访问。
     * 3. jcache 与 JSR107 集成，可以桥接各种缓存实现。
     */
    @Reference(check = false, timeout = 4000, version = "4.0.0", cache = "lru")
    private GoodsFacade goodsFacade;

    @Override
    public List<Order> getOrderListByOrderId(Long id) {
        List<GoodsDto> goodsDtoList = Lists.newArrayList();
        for (int i = 0; i <2; i++) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            goodsDtoList = goodsFacade.getGoodsByOrderId(id);
            stopwatch.stop();
            //第二次访问花费0毫秒
            log.info("花费时间 = {} ", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
        if (CollectionUtils.isEmpty(goodsDtoList)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(new Order(id, 100D, convert.apply(goodsDtoList)));
    }

    private Function<List<GoodsDto>, List<Goods>> convert = goodsList ->
            goodsList.stream().map(c ->
                    new Goods(c.getGoodsName(), c.getGoodsColor(), c.getQutity())
            ).collect(Collectors.toList());

}
