package cn.dubbo.provider.service.impl;

import cn.dubbo.provider.service.IProviderService;

/**
 * @author Brave
 * @create 2021-08-13 9:40
 * @description
 **/
public class ProviderServiceImpl implements IProviderService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
