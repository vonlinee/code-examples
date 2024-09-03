package org.cloud.crm.controller;

import org.cloud.crm.service.IAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/spring")
public class SpringController {

    @Resource
    IAccountService accountService;

    @GetMapping("/tx")
    public void addExpression() {
        accountService.transferMoney("A", "B", 100);
    }
}
