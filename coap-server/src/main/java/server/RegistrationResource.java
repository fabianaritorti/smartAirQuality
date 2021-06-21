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

	//</.well-known/core>;ct=40,</res_air>;title="Air actuator: ?POST/
	//		PUT state=ON|OFF";obs;rt="Air Control",</res_quality>;title="Quality sensor";methods="GET";rt="int";obs

	
	
	public void handleGET(CoapExchange exchange) {
		int nodeId = 0;
		exchange.accept();
		String name = null;
		
		//ottengo la richiesta della payload come una stringa
		//String responseText = exchange.getRequestText();
		
		//converto la stringa in un array di char
		//char[] charResponse = responseText.toCharArray();
		
		//nodeId = Character.getNumericValue(charResponse[0]);
		
		
		
		
		
		//inetAddress è il source address della richiesta
		InetAddress inetAddress = exchange.getSourceAddress();
		System.out.println("INETADDRESS"+ inetAddress);
		System.out.println("INETHOSTADDRESS" + inetAddress.getHostAddress());
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
		String[] resources = responseText.split(";");
		//String[] resources = responseText.split(",");
		//mi costruisco un resourcesPath con il numero totale di risorse (ho 3 nodi e ne devo avere 3)
		String []resourcesPath =  new String[TOTAL_RESOURCES]; 
		int index = 0;
		for (int i = 0; i<resources.length; i++) {
			System.out.println("RESOURCES" + resources[i]);
			//per ogni risorsa splittata prima (vedi 21,24) mi faccio uno split con la , per ottenere </res_quality>
			//String []resources2 = resources[i].split(",");
			String []resources2 = resources[i].split(",");
			//String []resources2 = resources[i].split(";");
			System.out.println("RESOURCES2" + resources2[i]);
			
			if (resources2.length > 1) {
				//se sono + si 1 e quindi il mio numero di risorse vado a rimpiazzare i simboli sotto con le "" in modo tale da 
				//ottenere i nomi delle singole risorse
				//resourcesPath[index++] = resources2[1].replaceAll("[\\<>]", "");	
				
				resourcesPath[index++] = resources2[1].replaceAll("[//<>]", "");
				System.out.println("RESOURCES_PATH"+ resourcesPath);
				}
			
		}
		
		for (int i = 0; i < resourcesPath.length; i++) {
			
			name = Rooms[nodeId];
			if (resourcesPath[i] == null) {
				System.out.println("There is no resource");
			} else {
				if (resourcesPath[i] != null) {
					//il metodo contains restituisce TRUE se res_quality è contenuta nel payload
					if (resourcesPath[i].contains("res_quality")) {
						//inetAddress.getHostAddress restituisce l'IP
						
						Quality q = new Quality(inetAddress.getHostAddress(),resourcesPath[i]);
						if (MainApplication.getQualityMap().containsValue(q)) {
							System.out.println("This quality value is present yet");
						} else if(!MainApplication.getQualityMap().containsValue(q)) {
							MainApplication.getQualityMap().put(name, q);
							addObservingClient(q);
						}
					} else if (resourcesPath[i].contains("res_air")) {
						Air a = new Air(inetAddress.getHostAddress(),resourcesPath[i]);
						if (MainApplication.getAirMap().containsValue(a)) {
							System.out.println("This air state is present yet");
						} else if (!MainApplication.getAirMap().containsValue(a)) {
							MainApplication.getAirMap().put(name, a);
							
						}
					}
					
				}
			}
			nodeId++;
			
			
		}
		roomCounter++;
		
		System.out.println("Room "+ name + " added");
		System.out.println("-------------------------------");
		System.out.println("The rooms registered are:" + roomCounter);
		System.out.println("-------------------------------");
		if (roomCounter == Rooms.length) {
			System.out.println("-------------------------------");
			System.out.println("All the resources are added!");
			System.out.println("-------------------------------");
			System.out.println("Registration is complete!");
			System.out.println("-------------------------------");
			MainApplication.setWaitRegistration(false);
		}
	}
	
	public void addObservingClient(Quality q) {
		MyClient client = new MyClient(q);
		MainApplication.getClientList().add(client);
		//essendo un array faccio -1 perchè il get mi restituisce l'indice e quindi il client ad esempio il 1o è in posizione 0
		MainApplication.getClientList().get(MainApplication.getClientList().size()-1).startObserving();
	}

}
