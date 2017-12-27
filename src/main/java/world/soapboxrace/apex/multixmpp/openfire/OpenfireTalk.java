package world.soapboxrace.apex.multixmpp.openfire;

import world.soapboxrace.apex.multixmpp.BaseXmppTalk;
import world.soapboxrace.apex.multixmpp.MarshalXML;
import world.soapboxrace.apex.multixmpp.ServerEndpoint;
import world.soapboxrace.apex.multixmpp.UnmarshalXML;
import world.soapboxrace.apex.multixmpp.jaxb.XMPP_IQPingType;
import world.soapboxrace.apex.multixmpp.jaxb.XMPP_IQPongType;

import java.net.Socket;

public class OpenfireTalk extends BaseXmppTalk
{
    OpenfireTalk(Socket socket)
    {
        super(socket);
    }

    @Override
    public void handleMessage(String msg)
    {
        if (msg.contains("<ping xmlns=\"urn:xmpp:ping\"/>"))
        {
            XMPP_IQPingType openfirePing = (XMPP_IQPingType) UnmarshalXML.unMarshal(msg, XMPP_IQPingType.class);
            write(MarshalXML.marshal(new XMPP_IQPongType(openfirePing.getId())));
        } else
        {
            ServerEndpoint.broadcast(msg);
        }
    }
}
