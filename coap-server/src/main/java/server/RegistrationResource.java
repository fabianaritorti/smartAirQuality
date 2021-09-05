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
		
		
	
		
		
		
		
		
		//inetAddress è il source address della richiesta
		InetAddress inetAddress = exchange.getSourceAddress();

		//con newCoapClient si costruisce un nuovo clientCoap che manda richieste ad un uri specifico
		CoapClient client = new CoapClient("coap://[" + inetAddress.getHostAddress() + "]:5683/.well-known/core");
		//in questo caso vengono restituite tutte le risorse get e quindi res_quality e res_air
		CoapResponse response = client.get();
		
		//viene restituito il response code tipo 4.04 e quindi se è diverso da 2 è un errore perchè la richiesta non nè stata inoltrata
		String code = response.getCode().toString();
		if (!code.startsWith("2")) {
			System.out.println("Error: " + code);
			return;
		}
		//responseText è il payload ottenuto tramite il metodo getResponseText(riga 21,24)
		String responseText = response.getResponseText();
		System.out.println("PAYLOAD:" + responseText);
		//si fa lo split in base a ; per ottenere le songole risorse
//		
		String[] resources = responseText.split(",");
//		//mi costruisco un resourcesPath con il numero totale di risorse (ho 3 nodi e ne devo avere 3)

//		//int index = 0;
		for (int i = 1; i<resources.length; i++) {
			//1 because the first one is </.well-known/core>;ct=40

			String[] parameters = resources[i].split(";");
//			//per ogni risorsa splittata prima (vedi 21,24) mi faccio uno split con la , per ottenere </res_quality>

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
