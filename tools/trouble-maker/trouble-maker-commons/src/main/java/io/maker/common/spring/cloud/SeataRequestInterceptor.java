package io.maker.common.spring.cloud;

import feign.RequestInterceptor;
import feign.RequestTemplate;
// import io.seata.core.context.RootContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

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