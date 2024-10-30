package org.lancoo.crm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 配置WebSocket传输参数,如消息大小限制、发送超时时间和发送缓冲区大小。
     *
     * @param registration
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(8192) // 设置消息大小限制
                .setSendTimeLimit(10 * 1000) // 设置发送超时时间
                .setSendBufferSizeLimit(512 * 1024); // 设置发送缓冲区大小
    }

    /**
     * 添加日志拦截器,用于记录WebSocket通道的收发情况。
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new AuthInChannelInterceptor());
    }

    /**
     * 配置客户端输出通道的线程池参数。
     *
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor()
                .corePoolSize(8)
                .maxPoolSize(32)
                .keepAliveSeconds(60);
    }

    /**
     * 注册WebSocket端点"/ws",并启用SockJS支持。
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * 配置消息代理,启用"/topic"和"/queue"两种消息广播模式,并设置应用程序目的地前缀为"/app"。
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }
}