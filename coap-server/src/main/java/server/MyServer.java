package server;

import org.eclipse.californium.core.CoapServer;

public class MyServer extends CoapServer {
	
	public void startServer() {
		System.out.println("Server started...");
		this.add(new RegistrationResource("registration"));
		this.start();
	}


}

