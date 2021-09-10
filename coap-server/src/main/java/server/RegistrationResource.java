package server;

import java.net.InetAddress;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class RegistrationResource extends CoapResource {
	

	
	public RegistrationResource(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

//	</.well-known/core>;ct=40,</res_air>;title="Air actuator: ?POST/PUT state=ON|OFF";rt="Air Control";obs,</res_quality>;title="Quality sensor";rt="Quality sensor";obs
//	PUT state=ON|OFF";obs;rt="Air Control",</res_quality>;title="Quality sensor";methods="GET";rt="int";obs

	
//	PATH /res_air
//	NAMEres_air
//	INFO title="Air actuator: ?POST/PUT state=ON|OFF";rt="Air Control"
//	OBSobs

	
	
	public void handleGET(CoapExchange exchange) {
		
		exchange.accept();
		
		
	
		
		
		
		
		
		//inetAddress is the source address of the request
		InetAddress inetAddress = exchange.getSourceAddress();

		
		CoapClient client = new CoapClient("coap://[" + inetAddress.getHostAddress() + "]:5683/.well-known/core");
		
		CoapResponse response = client.get();
		
		
		String code = response.getCode().toString();
		if (!code.startsWith("2")) {
			System.out.println("Error: " + code);
			return;
		}
		//responseText payload obtained by getResponseText(row 19,20)
		String responseText = response.getResponseText();
		System.out.println("PAYLOAD:" + responseText);
		
//		
		String[] resources = responseText.split(",");


//		
		for (int i = 1; i<resources.length; i++) {
			//1 because the first one is </.well-known/core>;ct=40

			String[] parameters = resources[i].split(";");
//			

			if(parameters.length > 0 && parameters[0].split("<").length > 1) {
				String path = parameters[0].split("<")[1].replace(">", "");
				String name = path.replace("/", "");
				String info = parameters[1]+";"+parameters[2];
				String observableP = parameters[3];
				System.out.println("PATH "+ path);
				System.out.println("NAME" + name);
				System.out.println("INFO "+ info);
				System.out.println("OBS" + observableP);
				boolean obs = false;
				
				if(parameters.length>3) {
					if(parameters[3].contains("obs")) {
					obs = true;
					}
				}
				
				Resource newResource = new Resource(inetAddress.getHostAddress(), path,name,info,obs);
				String nameResource = name + inetAddress.getHostAddress();
			
				MainApplication.getRegisteredResources().put(nameResource,newResource);
				
				System.out.println("\n"+nameResource+" registered");
				
				//TODO OBSERVING CLIENT
				if (obs == true) {
					
					MainApplication.observedResources.put(nameResource,new MyClient(newResource));
					MainApplication.getObservedResources().get(nameResource).startObserving();
				}
				
				
					
			}
		


			}
	}
			


	

}
