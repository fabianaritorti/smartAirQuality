package server;

public class Resource {

	

	private String ip;
	private String path;
	private String name;
	private String info;
	private boolean isObservable;
	private boolean state = false;
	private Long valueQ = (long) 30;
	private Long valueP = (long) 15;
	
	




	public Resource(String ip, String path, String name, String info, boolean isObservable) {
		super();
		this.ip = ip;
		this.path = path;
		this.name = name;
		this.info = info;
		this.isObservable = isObservable;
		
	}

	
	public Long getValueP() {
		return valueP;
	}





	public void setValueP(Long valueP) {
		this.valueP = valueP;
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

	public boolean getState() {
		return state;
	}

	public Long getValueQ() {
		return valueQ;
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

	public void setValueQ(Long valueQ) {
		this.valueQ = valueQ;
	}

	public String getCoapURI() {
		return "coap://[" + this.ip + "]:5683/" + this.path;
	}
	
	public boolean equals(Resource o){
		return (this.path.equals(o.path) && this.ip.equals(o.ip));
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
	//metodo per mostrare lo stato delle risorse e i valori ottenuti
//	public String showStatus() {
//		String status = "";
//		if (this.getName().contains("res_air")) {
//			if (this.getState()) {
//				status = "ON";
//			} else if (!this.getState()) {
//				status = "OFF";
//			}
//		}
//		if (this.getName().contains("res_quality")) {
//			System.out.println("QUALITY VALUE: " + this.getValue() + "STATUS:" + status);
//		}
//		return this.getName();
//		
//	}
	public void showStatus() {
		String status = "";
		if (this.getState()) {
			status = "ON";
		} else if (!this.getState()) {
			status = "OFF";
		}
		String nameNode = this.ip;
		System.out.println("NODE : " + nameNode.charAt(nameNode.length() - 1));
		//System.out.println("NODE");
		System.out.println("QUALITY VALUE: " + this.getValueQ() +  " " + "STATUS: " + status);
		
	}
	
	public String showResourcesInfo() {
	String valueResource = null;
	if (this.getName().contains("res_air")) {
		if (this.getState()) {
			valueResource = "DEPURATOR STATUS : ON";
		} else if (!this.getState()) {
			valueResource = "DEPURATOR STATUS : OFF";
		}
	}
	if (this.getName().contains("res_quality")) {
		valueResource = "QUALITY VALUE:" + " " + this.getValueQ().toString();
	}
	if (this.getName().contains("res_presence")) {
		if (this.getValueP() < 50) {
			valueResource = "PRESENCE NOT DETECTED";
		} else {
			valueResource = "PRESENCE DETECTED";
		}
		
	}
	return this.getName() + " " + valueResource;
	
}
	
	
	

	


}
