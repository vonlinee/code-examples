package org.lancoo.crm.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/api/websocket/{user_id}")
public class WebSocketServer {

    @OnOpen
    public void onOpen(Session session, String userId) {

    }

    @OnClose
    public void onClose(Session session) {

    }
}
