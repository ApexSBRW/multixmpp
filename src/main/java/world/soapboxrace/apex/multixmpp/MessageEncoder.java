package world.soapboxrace.apex.multixmpp;

import world.soapboxrace.apex.multixmpp.data.Message;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(final Message message) throws EncodeException
    {
        return Json.createObjectBuilder()
                       .add("message", message.getContent())
                       .build().toString();
    }

    @Override
    public void init(EndpointConfig config)
    {
        
    }

    @Override
    public void destroy()
    {

    }
}