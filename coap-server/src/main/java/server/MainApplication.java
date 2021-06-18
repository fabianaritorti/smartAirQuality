package server;


public class MainApplication {

	private static MainApplication sharedInstance = new Application();

	public static MainApplication getSharedInstance() {
		return sharedInstance;
	}

	public static void main(String[] args) {
		MainApplication.getSharedInstance.start();
		

	}

	public void showMenu() {
		System.out.println("Welcome to the Air Depuration System!");
		System.out.println("Please, insert a command");
		System.out.println("0.Show the resources and their status");
		System.out.println("1.Start depuration");
		System.out.println("2.Stop depuration");
		//eventualmente aggiungere altro
		System.out.println("3.Exit");
		

	}

	public void start() {
		showMenu();
		

	}

}
