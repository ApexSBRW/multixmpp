package world.soapboxrace.apex.multixmpp;

import world.soapboxrace.apex.multixmpp.data.Message;
import world.soapboxrace.apex.multixmpp.data.SendMessagePayload;
import world.soapboxrace.apex.multixmpp.openfire.OpenFireSoapBoxCli;

import javax.json.Json;
import javax.websocket.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@javax.websocket.server.ServerEndpoint(value = "/xmpp", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ServerEndpoint
{
    private static final int STATE_UNAUTHENTICATED = 0;
    private static final int STATE_AUTHENTICATED = 1;
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    public static void broadcast(String msg)
    {
        for (Session peer : peers)
        {
            try
            {
                peer.getBasicRemote().sendText(msg);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @OnOpen
    public void onOpen(Session session)
    {
        System.out.println(format("%s connected", session.getId()));
        peers.add(session);

        try
        {
            session.getBasicRemote().sendText("Hello racer!");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Message message, Session session) throws IOException, EncodeException
    {
//            System.out.println(message.getContent());
        String action = message.getAction();
        String content = message.getContent();
        String token = message.getToken();
        
        if (!token.equals(Config.getAuthToken()))
        {
            return;
        }

        switch (action)
        {
            case "SEND_MESSAGE":
                SendMessagePayload smp = Utils.gson.fromJson(content, SendMessagePayload.class);
                OpenFireSoapBoxCli.getInstance().sendRaw(smp.getData());
                break;
            case "KEEP_ALIVE":
                session.getBasicRemote().sendText("KeepAlive return");
            default:
                break;
        }
//        String user = (String) session.getUserProperties().get("user");
//        if (user == null) {
//            session.getUserProperties().put("user", message.getSender());
//        }
//        if ("quit".equalsIgnoreCase(message.getContent())) {
//            session.close();
//        }
//
//        System.out.println(format("[%s:%s] %s", session.getId(), message.getReceived(), message.getContent()));
//
//        //broadcast the message
//        for (Session peer : peers) {
//            if (!session.getId().equals(peer.getId())) { // do not resend the message to its sender
//                peer.getBasicRemote().sendObject(message);
//            }
//        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException
    {
        System.out.println(format("%s disconnected", session.getId()));
        peers.remove(session);
        //notify peers about leaving the chat room
//        for (Session peer : peers) {
//            Message chatMessage = new Message();
//            chatMessage.setSender("Server");
//            chatMessage.setContent(format("%s left the chat room.", (String) session.getUserProperties().get("user")));
//            chatMessage.setReceived(new Date());
//            peer.getBasicRemote().sendObject(chatMessage);
//        }
    }

}