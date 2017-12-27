package world.soapboxrace.apex.multixmpp;

import javax.json.Json;

public class JsonUtil {

    public static String formatMessage(String action, String content) {
        return Json.createObjectBuilder()
                .add("action", action)
                .add("content", content)
                .build().toString();
    }

}