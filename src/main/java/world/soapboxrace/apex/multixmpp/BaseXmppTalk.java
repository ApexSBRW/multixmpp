package world.soapboxrace.apex.multixmpp;

import java.io.*;
import java.net.Socket;

public abstract class BaseXmppTalk implements IXmppTalk
{
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public BaseXmppTalk(Socket socket)
    {
        setSocket(socket);
    }

    public Socket getSocket()
    {
        return socket;
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
        setReaderWriter();
    }

    @Override
    public String read()
    {
        String msg = null;
        char[] buffer = new char[8192];
        int charsRead;
        try
        {
            if ((charsRead = reader.read(buffer)) != -1)
            {
                msg = new String(buffer).substring(0, charsRead);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("S->C [" + msg + "]");
        if (msg != null && !msg.isEmpty())
        {
            this.handleMessage(msg);
        }
        return msg;
    }

    public void startReader()
    {
        XmppTalkReader xmppTalkReader = new XmppTalkReader(this);
        xmppTalkReader.start();
    }

    public void write(String msg)
    {
        try
        {
            char[] cbuf = new char[msg.length()];
            msg.getChars(0, msg.length(), cbuf, 0);
            System.out.println("C->S [" + msg + "]");
            writer.write(cbuf);
            writer.flush();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setReaderWriter()
    {
        try
        {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static class XmppTalkReader extends Thread
    {
        private BaseXmppTalk xmppTalk;

        XmppTalkReader(BaseXmppTalk xmppTalk)
        {
            this.xmppTalk = xmppTalk;
        }

        @Override
        public void run()
        {
            while (true)
            {
                String read = xmppTalk.read();
                if (read == null)
                {
                    try
                    {
                        xmppTalk.socket.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    this.interrupt();
                    break;
                }
            }
        }
    }
}
