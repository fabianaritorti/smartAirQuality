package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.IIOException;

public class MainApplication {
	
	public static boolean observeMode = false;
	//public static boolean waitRegistration = true;
	
	public static HashMap<String,Quality> qualityMap = new HashMap<String,Quality>();
	public static HashMap<String,Air> airMap = new HashMap<String,Air>();
	
	public static ArrayList<MyClient> clientList = new ArrayList<MyClient>();
	
	

	

	public static void main(String[] args) {
		
		//runServer();
		showMenu();
		//System.out.println("WELCOME ");
		while(true) {
			int cmd = getCommand();
			switch(cmd) {
				case 1:
					//showResourcesStatus();
					showMenu();
					break;
				case 2:
					//startDepuration();
					showMenu();
					break;
				case 3:
					//stopDepuration();
					showMenu();
					break;
				case 0:
					System.out.println("EXIT FROM THE APPLICATION");
					System.exit(0);
					break;
					
					
			}
		}
		

	}

	public static void showMenu() {
		System.out.println("************************************************");
		System.out.println("Welcome to the Air Depuration System!");
		System.out.println("Please, insert a command");
		System.out.println("1.Show the resources and their status");
		System.out.println("2.Start depuration");
		System.out.println("3.Stop depuration");
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

	

}
