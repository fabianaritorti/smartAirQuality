package server;

public class Resource {

	private String ip;
	private String path;
	


	public Resource(String ip, String path) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.path = path;
		
	}

	public String getPath() {
		return path;
	}

	public String getIp() {
		return ip;
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
		builder.append(ip);
		builder.append(", path=");
		builder.append(path);
		builder.append("]");
		return builder.toString();
	}
	
	

	
	
	

	


}
