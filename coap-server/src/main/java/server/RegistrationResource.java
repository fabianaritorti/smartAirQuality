package server;

import java.net.InetAddress;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class RegistrationResource extends CoapResource {
	
	private final static int TOTAL_RESOURCES = 3;
	private int roomCounter = 0;
	public static final String []Rooms = {"Hall", "Bathroom", "Study" };
	
	public RegistrationResource(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	//</.well-known/core>;ct=40,</res_air>;title="Air actuator: ?POST/v:1 t:CON c:GET i:cf69 {} [ ]
	//		PUT state=ON|OFF";obs;rt="Air Control",</res_quality>;title="Quav:1 t:CON c:GET i:cf6a {} [ ]
	//		lity sensor";methods="GET";rt="int";obs

	
	
	public void handleGET(CoapExchange exchange) {
		int nodeId = 0;
		exchange.accept();
		
		//nodeId = fromPayloadToInt(exchange.getRequestText());	
		//String key = nodeIdToRoomName(nodeId);

		InetAddress inetAddress = exchange.getSourceAddress();
		CoapClient client = new CoapClient("coap://[" + inetAddress.getHostAddress() + "]:5683/.well-known/core");
		CoapResponse response = client.get();
		
		String code = response.getCode().toString();
		if (!code.startsWith("2")) {
			System.out.println("Error: " + code);
			return;
		}

		String responseText = response.getResponseText();
		String[] resources = responseText.split(",");
	}

}
