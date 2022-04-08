package org.setamv.shardingsphere.sample.dynamic.service;

import org.setamv.shardingsphere.sample.dynamic.mapper.PurchaseOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 租户Service
 *
 * @author setamv
 * @date 2021-04-15
 */
@Service
public class TenantService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

}
