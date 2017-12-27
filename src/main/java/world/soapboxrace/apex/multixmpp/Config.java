package world.soapboxrace.apex.multixmpp;

public class Config
{
    public static String getXmppIp()
    {
        return getProperty("multixmpp.xmpp.ip");
    }
    
    public static int getXmppPort()
    {
        return Integer.parseInt(getProperty("multixmpp.xmpp.port"));
    }
    
    public static String getXmppToken()
    {
        return getProperty("multixmpp.xmpp.token");
    }
    
    public static String getAuthToken()
    {
        return getProperty("multixmpp.authtoken");
    }
    
    private static String getProperty(String name)
    {
        String prop = System.getProperty(name);

        if (prop == null)
        {
            throw new IllegalStateException("Cannot find property '" + name + '\'');
        }

        return prop;
    }
}
