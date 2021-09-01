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

			
			int cmd = getCommand();
			
			if (cmd > 4) {
				System.out.println("Error on digiting command");
				System.out.println("Please, check and insert a new command again");
				showMenu();
			}
			switch(cmd) {
				case 1:
					showResourcesStatus();
					
					
					showMenu();
					break;
				
				case 2:

					changeDepuratorStatus("ON");
					showMenu();
					break;
				case 3:
					
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

			System.out.println("RESOURCES:"  + entry.getValue().toString());
			System.out.println("STATUS INFO---->:"  + entry.getValue().showResourcesInfo());
			
		}
		
	}
	

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


	

	public static void showMenu() {
		System.out.println("************************************************");
		System.out.println("Welcome to the Air Depuration System!");
		System.out.println("Please, insert a command");
		System.out.println("1.Show the resources info");
		System.out.println("2.Start depuration");
		System.out.println("3.Stop depuration");
		System.out.println("4.Observe resource mode");
		
	
		
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








}


