package server;

public class Air extends Resource{
	
	private boolean state;

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Air(String ip, String path) {
		super(ip, path);
		//quando inizializzo la risorsa l'attuatore dell'aria è spento
		state = false;
		// TODO Auto-generated constructor stub
	}
	
	

	

}
