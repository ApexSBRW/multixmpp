package world.soapboxrace.apex.multixmpp;

import org.glassfish.tyrus.server.Server;
import world.soapboxrace.apex.multixmpp.data.SendMessagePayload;
import world.soapboxrace.apex.multixmpp.openfire.OpenFireSoapBoxCli;

import javax.websocket.DeploymentException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        OpenFireSoapBoxCli.getInstance();

        SendMessagePayload smp = new SendMessagePayload();
        smp.setData("test");
        smp.setTo("sbrw.100");
        
        System.out.println(JsonUtil.formatMessage("SEND_MESSAGE", Utils.toJson(smp)));
        Server server = new Server("localhost", 8025, "/ws", ServerEndpoint.class);

        try {
            server.start();
            new Scanner(System.in).nextLine();
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }
}
