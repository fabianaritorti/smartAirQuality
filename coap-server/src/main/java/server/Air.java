package server;

public class Air extends Resource{
	
	private boolean state;

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Air(String ip, String path,String name,String info, boolean isObservable) {
		super(ip, path, name, info, isObservable);
		//quando inizializzo la risorsa l'attuatore dell'aria è spento
		state = false;
		// TODO Auto-generated constructor stub
	}
	
	

	

}
