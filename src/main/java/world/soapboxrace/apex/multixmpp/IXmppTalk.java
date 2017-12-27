package world.soapboxrace.apex.multixmpp;

import java.net.Socket;

public interface IXmppTalk
{
    Socket getSocket();

    void setSocket(Socket socket);
    
    String read();
    
    void write(String msg);
    
    void handleMessage(String msg);
}
