package com.lh.customer;

import javax.websocket.*;
import java.net.URI;

/**
 * Created by LH 2446059046@qq.com on 2017/5/23.
 */
@ClientEndpoint
public class WebSocketClinet {
    private Session session;

    public WebSocketClinet (URI uri) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            container.connectToServer(this, uri);
        } catch (Exception io) {
            throw new RuntimeException(io);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        this.session = userSession;
    }

    @OnClose
    public void onClose() {
        try {
            this.session.close();
            this.session = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
