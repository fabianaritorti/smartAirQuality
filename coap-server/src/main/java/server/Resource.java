package server;

public class Resource {

	private String ip;
	private String path;
	private String name;
	//private String name;
	


//	public String getName() {
//		return name;
//	}

//	public void setName(String name) {
//		this.name = name;
//	}

	public Resource(String ip, String path,String name) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.path = path;
		this.name = name;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
	
	

	
	
	

	


}
