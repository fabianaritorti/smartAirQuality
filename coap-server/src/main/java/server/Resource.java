package server;

public class Resource {

	

	private String ip;
	private String path;
	private String name;
	private String info;
	private boolean isObservable;
	private boolean state = false;
	private Integer value = 15;
	
	public Resource(String ip, String path, String name, String info, boolean isObservable) {
		super();
		this.ip = ip;
		this.path = path;
		this.name = name;
		this.info = info;
		this.isObservable = isObservable;
		
	}

	
	
	
	
	public String getIp() {
		return ip;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public String getInfo() {
		return info;
	}

	public boolean isState() {
		return state;
	}

	public int getValue() {
		return value;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getCoapURI() {
		return "coap://[" + this.ip + "]:5683/" + this.path;
	}
	
	public boolean equals(Resource o){
		return (this.path.equals(o.path) && this.ip.equals(o.ip));
	}
	//private String name;
	
	
	
	

//	public String getName() {
//		return name;
//	}

//	public void setName(String name) {
//		this.name = name;
//	}

//	public Resource(String ip, String path,String name) {
//		// TODO Auto-generated constructor stub
//		this.ip = ip;
//		this.path = path;
//		this.name = name;
//		
//	}

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getPath() {
//		return path;
//	}
//
//	public String getIp() {
//		return ip;
//	}
//	
//	
//
//	public void setPath(String path) {
//		this.path = path;
//	}
//
//	public void setIp(String ip) {
//		this.ip = ip;
//
//	}
//

//	
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
	
	public String showStatus() {
		if (this.getName().contains("res_air")) {
			if (this.isState()) {
				System.out.println("STATUS:ON");
			} else if (!this.isState()) {
				System.out.println("STATUS:OFF");
			}
		}
		if (this.getName().contains("res_quality")) {
			System.out.println("QUALITY VALUE: " + this.getValue());
		}
		return this.getName();
		
	}
	
	

	
	
	

	


}
