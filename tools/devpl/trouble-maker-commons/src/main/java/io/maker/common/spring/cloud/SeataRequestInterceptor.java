package io.maker.common.spring.cloud;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@ConditionalOnProperty(prefix = "write.mp.jdbc", name = "transactionPolicy", havingValue = "gtsx")
//@ConditionalOnClass(RootContext.class)
public class SeataRequestInterceptor implements RequestInterceptor {

	@Override
    public void apply(RequestTemplate requestTemplate) {
//        String xid = RootContext.getXID();
		String xid = "";
        if (xid != null && !xid.isEmpty()) {
            requestTemplate.header("SEATA-XID", new String[]{xid});
        }
    }
}