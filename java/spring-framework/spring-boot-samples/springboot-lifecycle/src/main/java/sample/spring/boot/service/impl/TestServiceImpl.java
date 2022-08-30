package sample.spring.boot.service.impl;

import org.springframework.stereotype.Service;
import sample.spring.boot.service.ITestService;

@Service
public class TestServiceImpl implements ITestService {

    @Override
    public String test() {
        return this.toString();
    }
}
