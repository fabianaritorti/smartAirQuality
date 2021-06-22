package server;

import java.awt.PageAttributes.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.IIOException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

public class MainApplication {
	
	public static boolean observeMode = false;
	
	public static boolean waitRegistration = true;
	
//	public static HashMap<String,Quality> qualityMap = new HashMap<String,Quality>();
//	
//	public static HashMap<String,Air> airMap = new HashMap<String,Air>();
	
	public static ArrayList<MyClient> clientList = new ArrayList<MyClient>();
	
	public static ArrayList<Quality> qualityList = new ArrayList<Quality>();
	public static ArrayList<Air> airList = new ArrayList<Air>();
	
	public static ArrayList<Quality> getQualityList() {
		return qualityList;
	}

	public static ArrayList<Air> getAirList() {
		return airList;
	}

	public static void setQualityList(ArrayList<Quality> qualityList) {
		MainApplication.qualityList = qualityList;
	}

	public static void setAirList(ArrayList<Air> airList) {
		MainApplication.airList = airList;
	}
	
	public static ArrayList<MyClient> getClientList() {
		return clientList;
	}
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

	public static void setClientList(ArrayList<MyClient> clientList) {
		MainApplication.clientList = clientList;
	}
	
	public static boolean isWaitRegistration() {
		return waitRegistration;
	}

	public static void setWaitRegistration(boolean waitRegistration) {
		MainApplication.waitRegistration = waitRegistration;
	}
	

	

	public static void main(String[] args) {
		
		runServer();
		showMenu();
		
		while(true) {
			
			int cmd = getCommand();
			Integer nodeId;
			if (cmd > 4) {
				System.out.println("Error on digiting command");
				System.out.println("Please, check and insert a new command again");
				showMenu();
			}
			switch(cmd) {
				case 1:
					showResources();
					showMenu();
					break;
				case 2:
					nodeId = getNodeId();
					if (nodeId != null) {
						changeDepuratorStatus("ON", airList.get(nodeId));
					}
					showMenu();
					break;
				case 3:
					nodeId = getNodeId();
					if (nodeId != null) {
						changeDepuratorStatus("OFF", airList.get(nodeId));
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

	public static void showMenu() {
		System.out.println("************************************************");
		System.out.println("Welcome to the Air Depuration System!");
		System.out.println("Please, insert a command");
		System.out.println("1.Show the resources");
		System.out.println("2.Start depuration");
		System.out.println("3.Stop depuration");
		System.out.println("4.Show node status");
	// 	//eventualmente aggiungere altro
		//System.out.pritnln("Show the last quality air value")
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
public static void showResources() {
	System.out.println("THESE ARE THE RESOURCES:");
	for (int i = 0; i < getQualityList().size(); i++) {
		Air air = getAirList().get(i);
		Quality quality = qualityList.get(i);
		System.out.println("This is the node" + i+2 + "quality resource: " + quality.getIp() + " " + quality.getPath());
		System.out.println("This is the node" + i+2 + "air resource: " + air.getIp() + " " + air.getPath());
	}
	
}
public static void changeDepuratorStatus(String state, Air air) {
	CoapClient client = new CoapClient(air.getCoapURI());
	//una volta inizializzato il client faccio una richiesta post(payload ossia lo status, formato)
	CoapResponse coapResponse = client.post("state=" + state, MediaTypeRegistry.TEXT_PLAIN);
	String code = coapResponse.getCode().toString();
	if (!code.startsWith("2")) {
		System.out.println("Error: " + code);
		return;
	}
	if (state.toString().equals("ON")) {
		air.setState(true);
	} else {
		air.setState(false);
	}
}

public static Integer getNodeId() {
	System.out.println("INSERT THE NODE ID");
	int index = getCommand();
	if (index == -1) {
		return null;
	}
	if (index < qualityList.size()) {
		return index;
	} else {
		System.out.println("THERE IS NO NODE WITH THIS ID");
	}
		

	return null;

}
