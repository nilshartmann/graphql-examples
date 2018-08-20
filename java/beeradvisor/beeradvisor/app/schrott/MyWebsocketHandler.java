package nh.graphql.beeradvisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * MyWebsocketHandler
 */
public class MyWebsocketHandler extends TextWebSocketHandler {

  private final Logger logger = LoggerFactory.getLogger(BeeradvisorApplication.class);

  public MyWebsocketHandler() {
    logger.info("CONSTRUCTOR!!!!!");
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    logger.info("CONNECTION ESTABLISHED!!!!  ==> " + session.getAcceptedProtocol());

  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    logger.info("CONNECTION CLOSED!!!! ==> " + status);
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    logger.info("TRANSPORT ERROR  ==> " + exception, exception);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    logger.info("TEXTMESSAGE  ", message.getPayload());
    session.sendMessage(new TextMessage("HUHU ????"));
  }

}