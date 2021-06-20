package server;

public class Air extends Resource{
	
	private boolean state;

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Air(String ip, String path, String name, boolean observable) {
		super(ip, path, name, observable);
		//quando inizializzo la risorsa l'attuatore dell'aria è spento
		state = false;
		// TODO Auto-generated constructor stub
	}
	
	

	

}
