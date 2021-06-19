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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Resource [ip=");
		builder.append(this.ip);
		builder.append(", path=");
		builder.append(this.path);
		builder.append(", name=");
		builder.append(this.name);
		builder.append(", observable=");
		builder.append(this.observable);
		builder.append("]");
		return builder.toString();
	}
	
	

	


}
