package world.soapboxrace.apex.multixmpp;

import world.soapboxrace.apex.multixmpp.data.Message;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Date;

public class MessageDecoder implements Decoder.Text<Message>
{

    @Override
    public Message decode(final String textMessage) throws DecodeException
    {
        Message message = new Message();
        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();
        message.setAction(jsonObject.getString("action"));
        message.setContent(jsonObject.getString("content"));
        message.setToken(jsonObject.getString("token"));
        return message;
    }

    @Override
    public boolean willDecode(String s)
    {
        return true;
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