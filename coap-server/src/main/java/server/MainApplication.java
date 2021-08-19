package server;

import java.awt.PageAttributes.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.IIOException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

public class MainApplication {
	
	public static boolean observeMode = false;
	
	public static boolean waitRegistration = true;
	
//	public static HashMap<String,Quality> qualityMap = new HashMap<String,Quality>();
////	
//	public static HashMap<String,Air> airMap = new HashMap<String,Air>();
	
	static public Map<String,Resource> registeredResources = new HashMap<String,Resource>();
	static public Map<String,MyClient> observedResources = new HashMap<String,MyClient>();
	
	public static boolean isObserveMode() {
		return observeMode;
	}

	public static Map<String, Resource> getRegisteredResources() {
		return registeredResources;
	}

	public static Map<String, MyClient> getObservedResources() {
		return observedResources;
	}

	public static void setObserveMode(boolean observeMode) {
		MainApplication.observeMode = observeMode;
	}

	public static void setRegisteredResources(Map<String, Resource> registeredResources) {
		MainApplication.registeredResources = registeredResources;
	}

	public static void setObservedResources(Map<String, MyClient> observedResources) {
		MainApplication.observedResources = observedResources;
	}

	
//	public static ArrayList<MyClient> clientList = new ArrayList<MyClient>();
	
//	public static ArrayList<Quality> qualityList = new ArrayList<Quality>();
//	public static ArrayList<Air> airList = new ArrayList<Air>();
	
//	public static ArrayList<Quality> getQualityList() {
//		return qualityList;
//	}
//
//	public static ArrayList<Air> getAirList() {
//		return airList;
//	}
//
//	public static void setQualityList(ArrayList<Quality> qualityList) {
//		MainApplication.qualityList = qualityList;
//	}
//
//	public static void setAirList(ArrayList<Air> airList) {
//		MainApplication.airList = airList;
//	}
	
//	public static ArrayList<MyClient> getClientList() {
//		return clientList;
//	}
	//public static ArrayList<Presence> presenceList = new ArrayList<Presence>();
	
	
//	public static HashMap<String, Quality> getQualityMap() {
//		return qualityMap;
//	}
//
//	public static HashMap<String, Air> getAirMap() {
//		return airMap;
//	}
//
//	public static ArrayList<MyClient> getClientList() {
//		return clientList;
//	}
//
//	public static void setQualityMap(HashMap<String, Quality> qualityMap) {
//		MainApplication.qualityMap = qualityMap;
//	}
//
//	public static void setAirMap(HashMap<String, Air> airMap) {
//		MainApplication.airMap = airMap;
//	}
//
//	public static void setClientList(ArrayList<MyClient> clientList) {
//		MainApplication.clientList = clientList;
//	}
	
	public static boolean isWaitRegistration() {
		return waitRegistration;
	}

	public static void setWaitRegistration(boolean waitRegistration) {
		MainApplication.waitRegistration = waitRegistration;
	}
	

	

	public static void main(String[] args) throws IOException {
		
		runServer();
		showMenu();
		
		while(true) {
//			if (waitRegistration) {
//				System.out.println("LOADING REGISTRATION");
//			}
			//showMenu();
			
			int cmd = getCommand();
			
			if (cmd > 4) {
				System.out.println("Error on digiting command");
				System.out.println("Please, check and insert a new command again");
				showMenu();
			}
			switch(cmd) {
				case 1:
					showResourcesStatus();
					//showQualityResources();
					//showAirResources();
					
					showMenu();
					break;
				
				case 2:
//					nodeId = getNodeId();
//					if (nodeId != null) {
//						changeDepuratorStatus("OFF", airList.get(nodeId));
//					}
					//changeDepuratorStatus("ON","res_air");
					changeDepuratorStatus("ON");
					showMenu();
					break;
				case 3:
					//changeDepuratorStatus("OFF", "res_air");
					changeDepuratorStatus("OFF");
					showMenu();
					break;
				case 4:
					System.out.println("WELCOME TO THE OBSERVE RESOURCE MODE");
					System.out.println("PLEASE PRESS 0 IF YOU WANT TO EXIT FROM THIS MODE");
					observeMode = true;
					while(observeMode) {
						int command = getCommand();
						if (command == 0) {
							observeMode = false;
							break;
						}
					}
					showMenu();
					break;
				case 0:
					System.out.println("**************************");
					System.out.println("EXIT FROM THE APPLICATION");
					System.out.println("**************************");
					System.exit(0);
					break;
					
					
			}
		}
		

	}

	private static void showResourcesStatus() {
		// TODO Auto-generated method stub
		for (Map.Entry<String, Resource> entry: registeredResources.entrySet()) {
//			//System.out.println("NODE" + qualityMap.keySet());
			System.out.println("RESOURCES:"  + entry.getValue().toString());
			System.out.println("STATUS INFO:"  + entry.getValue().showResourcesInfo());
			//entry.getValue().showStatus();
		}
		
	}
	
//	public static void changeDepuratorStatus(String state,String name) throws IOException {
//		CoapClient client = new CoapClient(registeredResources.get(name).getCoapURI());
//		//una volta inizializzato il client faccio una richiesta post(payload ossia lo status, formato)
//		CoapResponse coapResponse = client.post("state=" + state, MediaTypeRegistry.TEXT_PLAIN);
//		String code = coapResponse.getCode().toString();
//		if (!code.startsWith("2")) {
//			System.out.println("Error: " + code);
//			return;
//		}
//		
////		if (state == "ON") {
////			System.out.println("Depuration air started");
////			
////			registeredResources.get(name).setState(true);
////		} else if (state == "OFF"){
////			System.out.println("Depuration air stopped");
////			registeredResources.get(name).setState(false);
////		}
//	}
	public static void changeDepuratorStatus(String state) throws IOException {
		
		System.out.println("INSERISCI IL NODO A CUI VUOI CAMBIARE STATO");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String valueStatus;
		valueStatus = input.readLine();
		String nameIp = null;
		for (String key:registeredResources.keySet()) {
			if (key.endsWith(valueStatus)) {
				nameIp = key;
			}
		}
		CoapClient client = new CoapClient(registeredResources.get(nameIp).getCoapURI());
		//una volta inizializzato il client faccio una richiesta post(payload ossia lo status, formato)
		CoapResponse coapResponse = client.post("state=" + state, MediaTypeRegistry.TEXT_PLAIN);
		String code = coapResponse.getCode().toString();
		if (!code.startsWith("2")) {
			System.out.println("Error: " + code);
			return;
		}
		if (state == "ON") {
			System.out.println("Depuration air started");
			registeredResources.get(nameIp).setState(true);
			} else if (state == "OFF"){
				System.out.println("Depuration air stopped");
				registeredResources.get(nameIp).setState(false);
				}
		}

//		if (state == "ON") {
//			System.out.println("Depuration air started");
//			
//			registeredResources.get(name).setState(true);
//		} else if (state == "OFF"){
//			System.out.println("Depuration air stopped");
//			registeredResources.get(name).setState(false);
//		}
	
	

	public static void showMenu() {
		System.out.println("************************************************");
		System.out.println("Welcome to the Air Depuration System!");
		System.out.println("Please, insert a command");
		System.out.println("1.Show the resources info");
		System.out.println("2.Start depuration");
		System.out.println("3.Stop depuration");
		System.out.println("4.Observe resource mode");
		
	// 	//eventualmente aggiungere altro
		
		System.out.println("0.Exit");
		System.out.println("************************************************");
	}
	
	private static int getCommand() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String line;
		int intCommand;
		try {
			line = input.readLine();
			intCommand = Integer.parseInt(line);
			return intCommand;
		} catch (IOException e) {
			return -1;
		} catch (NumberFormatException n) {
			return -1;
		}
	}
	
public static void runServer() {
		
		new Thread() {
			public void run() {
				MyServer server = new MyServer();
				server.startServer();
			}
		}.start();
		
	}

//public static void showQualityResources() {
//	if (qualityMap.size() == 0) {
//		System.out.println("THERE IS NO QUALITY RESOURCE");
//		return;
//	}
//	for (Map.Entry<String, Quality> entry: qualityMap.entrySet()) {
//		//System.out.println("NODE" + qualityMap.keySet());
//		System.out.println("QUALITY RESOURCES:"  + entry.getValue().toString());
//	}
//}

//public static void showAirResources() {
//	if (airMap.size() == 0) {
//		System.out.println("THERE IS NO AIR RESOURCE");
//		return;
//	}
//	for (Map.Entry<String, Air> entry: airMap.entrySet()) {
//		System.out.println("AIR RESOURCES:" + entry.getValue().toString());
//	}
//}
//public static void showResourcesStatus() {
//	for (String key : MainApplication.getQualityMap().keySet()) {
//		getStatusResource(key);
//	}
//}

//public static void showResources() {
//	System.out.println("THESE ARE THE RESOURCES:");
//	for (int i = 0; i < getQualityList().size(); i++) {
//		Air air = getAirList().get(i);
//		Quality quality = qualityList.get(i);
//		System.out.println("This is the node" + i+2 + "quality resource: " + quality.getIp() + " " + quality.getPath());
//		System.out.println("This is the node" + i+2 + "air resource: " + air.getIp() + " " + air.getPath());
//	}
//	
//}

//	
//	
//}

//public static void getStatusResource() {
//	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//	String nameQuality;
//	String nameAir;
//	System.out.println("INSERT THE NAME OF THE RESOURCES");
//	try {
//		System.out.println("QUALITY NAME");
//		nameQuality = bufferedReader.readLine();
//		System.out.println("AIR NAME");
//		nameAir= bufferedReader.readLine();
//		Air air = airMap.get(nameAir);
//		if (air == null) {
//			System.out.println("THERE IS NO RESOURCE WITH THIS NAME");
//			return;
//		}
//		Quality quality = qualityMap.get(nameQuality);
//		if (quality == null) {
//			System.out.println("THERE IS NO RESOURCE WITH THIS NAME");
//			return;
//		}
//		String qualityValue = null;
//		if (quality.getQuality() < 50 ) {
//			qualityValue = "BAD";
//		} else {
//			qualityValue = "GOOD";
//		}
//		String lightValue = null;
//		if (air.isState()) {
//			lightValue = "ON";
//		} else {
//			lightValue = "OFF";
//		}
//
//		System.out.println("THE NODE HAS : " + qualityValue +  "QUALITY VALUE " + "AND LIGHT VALUE: " + lightValue);
//		
//		
//		
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}




//public static Integer getNodeId() {
//	System.out.println("INSERT THE NODE ID");
//	int index = getCommand();
//	if (index == -1) {
//		return null;
//	}
//	if (index < qualityList.size()) {
//		return index;
//	} else {
//		System.out.println("THERE IS NO NODE WITH THIS ID");
//	}
//	return null;
		

//	
}


