package server;

public class Quality extends Resource{
	
	private int quality;

	//inserire un valore per la qualità iniziale
	//private int quality= 100;

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public Quality(String ip, String path) {
		super(ip, path);
		//int quality = 20;
		// TODO Auto-generated constructor stub
	}
	
	

}
