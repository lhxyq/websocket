package com.lh.provider;

import com.sun.org.glassfish.gmbal.ParameterNames;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by LH 2446059046@qq.com on 2017/5/23.
 */
@ServerEndpoint(value = "/chart/{nickName}")
public class WebSocketServer {
    //连接的对象的集合
    private static final Set<WebSocketServer> conns = new CopyOnWriteArraySet<WebSocketServer>();
    private String nickName;
    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "nickName") String nickName) {
        this.session = session;
        this.nickName = nickName;

        conns.add(this);
        String message = String.format("System> %s %s", this.nickName, " has joined.");
        WebSocketServer.broadCast(message);
    }

    @OnClose
    public void onClose() {
        conns.remove(this);
        String message = String.format("System> %s, %s", this.nickName, " has disconnection.");
        WebSocketServer.broadCast(message);
    }

    @OnMessage
    public void onMessage(String message, @PathParam(value = "nickName") String nickName) {
        WebSocketServer.broadCast(nickName + ">" + message);
    }

    @OnError
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }

    private static void broadCast(String message) {
        for (WebSocketServer conn : conns) {
            try {
                synchronized (conn) {
                    conn.session.getBasicRemote().sendText(message);
                }
            } catch (Exception e) {
                conns.remove(conn);
                try {
                    conn.session.close();
                } catch (IOException io) {
                    WebSocketServer.broadCast(String.format("System> %s %s", conn.nickName,
                            " has bean disconnection."));
                }
            }
        }
    }
}
