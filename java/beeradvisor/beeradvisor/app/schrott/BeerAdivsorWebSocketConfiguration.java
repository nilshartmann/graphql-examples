package nh.graphql.beeradvisor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * BeerAdivsorWebsocketConfiguration
 */
@Configuration
@EnableWebSocket
public class BeerAdivsorWebSocketConfiguration implements WebSocketConfigurer {
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(webSocketHandler(), "/subscriptions").setAllowedOrigins("*"); // .withSockJS();
  }

  WebSocketHandler webSocketHandler() {
    return new PerConnectionWebSocketHandler(MyWebsocketHandler.class);
  }

  @Bean
  ServletServerContainerFactoryBean createWebSocketContainer() {
    ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
    container.setMaxTextMessageBufferSize(1024 * 1024);
    container.setMaxBinaryMessageBufferSize(1024 * 1024);
    container.setMaxSessionIdleTimeout(30L * 1000);
    return container;
  }

}