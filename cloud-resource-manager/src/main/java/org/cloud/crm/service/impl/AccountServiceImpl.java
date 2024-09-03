package org.cloud.crm.service.impl;

import org.cloud.crm.service.IAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements IAccountService {
    @Override
    public boolean transferMoney(String a, String b, int money) {

        a();

        return false;
    }

    public void a() {
        b();
    }

    @Transactional
    public void b() {

        throw new RuntimeException("");
    }
}
