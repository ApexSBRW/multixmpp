package world.soapboxrace.apex.multixmpp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.StringReader;

public class Utils
{
    public static Gson gson = new GsonBuilder()
            .serializeNulls().create();

    public static <T> T fromJson(String json) {
        return gson.fromJson(json, new TypeToken<T>(){}.getType());
    }

    public static <T> String toJson(T obj) {
        return gson.toJson(obj, new TypeToken<T>(){}.getType());
    }
}
