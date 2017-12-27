package world.soapboxrace.apex.multixmpp.openfire;

import world.soapboxrace.apex.multixmpp.MarshalXML;
import world.soapboxrace.apex.multixmpp.SubjectCalc;
import world.soapboxrace.apex.multixmpp.jaxb.XMPP_MessageType;

public class OpenFireSoapBoxCli {

	private OpenfireTalk xmppTalk;

	private static OpenFireSoapBoxCli instance;

	public static OpenFireSoapBoxCli getInstance() {
        if (instance == null) {
			instance = new OpenFireSoapBoxCli();
		}
		return instance;
	}

	private OpenFireSoapBoxCli() {
		OpenfireHandshake xmppHandShake = new OpenfireHandshake();
		xmppTalk = xmppHandShake.getXmppTalk();
	}

	public void sendRaw(String msg) {
		xmppTalk.write(msg);
	}

	public void send(String msg, String to) {
		XMPP_MessageType messageType = new XMPP_MessageType();
		messageType.setTo(to);
		messageType.setBody(msg);
		messageType.setSubject(SubjectCalc.calculateHash(messageType.getTo().toCharArray(), msg.toCharArray()));
		String packet = MarshalXML.marshal(messageType);
		xmppTalk.write(packet);
	}

	public void send(String msg, Long to) {
		XMPP_MessageType messageType = new XMPP_MessageType();
		messageType.setToPersonaId(to);
		messageType.setBody(msg);
		messageType.setSubject(SubjectCalc.calculateHash(messageType.getTo().toCharArray(), msg.toCharArray()));
		String packet = MarshalXML.marshal(messageType);
		xmppTalk.write(packet);
	}

	public void send(Object object, Long to) {
		String responseXmlStr = MarshalXML.marshal(object);
		this.send(responseXmlStr, to);
	}

	public void send(Object object, String to) {
		String responseXmlStr = MarshalXML.marshal(object);
		this.send(responseXmlStr, to);
	}
	
	public void disconnect() {
		instance = null;
	}

}