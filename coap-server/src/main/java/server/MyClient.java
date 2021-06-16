package server;

import java.net.URI;

import org.eclipse.californium.core.CoapClient;

public class MyClient extends CoapClient {

	public MyClient() {
		// TODO Auto-generated constructor stub
	}

	public MyClient(String uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}

	public MyClient(URI uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}

	public MyClient(String scheme, String host, int port, String... path) {
		super(scheme, host, port, path);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
