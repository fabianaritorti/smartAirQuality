package server;

public class Resource {

	private String ip;
	private String path;
	private String name;
	private boolean observable;


	public Resource(String ip, String path, String name, boolean observable) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.path = path;
		this.name = name;
		this.observable = observable;
	}

	public String getPath() {
		return path;
	}

	public String getIp() {
		return ip;
	}
	
	public String getName() {
		return name;
	}

	public boolean getObservable() {
		return observable;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setIp(String ip) {
		this.ip = ip;

	}

	public String getCoapURI() {
		return "coap://[" + this.ip + "]:5683/" + this.path;
	}

	


}
