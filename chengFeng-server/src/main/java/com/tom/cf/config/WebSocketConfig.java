package com.tom.cf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @method:Spring 框架提供了基于 WebSocket 的STOMP 支持， STOMP 是一个简单的可互操作的协议，
 * 通常被用于通过中间服务器在客户端之间进行异步消息传递。
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        //设置消息代理的前缀，如果消息的前缀是/topic
        //就会将消息转发给消息代理（ broker ），再由消息代理将消息广播给当前连接的客户端。
        config.enableSimpleBroker ("/topic");
        //表示配置一个或多个前缀，通过这些前缀过滤出需要被注解方法处理的消息
        //例如，前缀为app的 destination 可以通过@MessageMapping注解的方法处理 其他给brocker
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public  void registerStompEndpoints(StompEndpointRegistry registry) {
        //客户端将通过这里配置的URL 来建立 WebSocket 连接
        registry.addEndpoint("/chat").withSockJS();
    }

}
