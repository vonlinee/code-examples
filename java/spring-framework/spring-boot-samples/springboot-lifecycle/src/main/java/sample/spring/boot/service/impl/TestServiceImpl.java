package sample.spring.boot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.spring.boot.entity.Model;
import sample.spring.boot.service.ITestService;

@Service
public class TestServiceImpl implements ITestService {

    public TestServiceImpl() {
        System.out.println(this + "实例化");
    }

    @Autowired
    Model model;

    @Override
    public String test() {
        return this.toString();
    }
}
