package com.reactpractice.lee.webSocket;


import com.reactpractice.lee.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@ServerEndpoint(value="/chatt")
public class WebSocket {

    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage //메시지가 수신되었을 때
    public void onMessage(String msg, Session session) throws Exception {
        System.out.println("recive message : " + msg);
        System.out.println(clients);
        for(Session s : clients){
            System.out.println("send data : " + msg);
            s.getBasicRemote().sendText(msg);
        }
    }

    @OnOpen
    public void onOpen(Session s){
        System.out.println("open session : " + s.getId());
        if(!clients.contains(s)){
            clients.add(s);
            System.out.println("session open : " + s);
        }else{
            System.out.println("이미 연결된 session임!!!!");
        }
    }

    @OnClose
    public void onClose(Session s){
        System.out.println("session close : " + s);
        clients.remove(s);
    }
}
