package server;


public class MainApplication {

	

	public static void main(String[] args) {
		
		showMenu();
		//System.out.println("WELCOME ");
		

	}

	public static void showMenu() {
		System.out.println("************************************************");
		System.out.println("Welcome to the Air Depuration System!");
		System.out.println("Please, insert a command");
		System.out.println("0.Show the resources and their status");
		System.out.println("1.Start depuration");
		System.out.println("2.Stop depuration");
	// 	//eventualmente aggiungere altro
		System.out.println("3.Exit");
		System.out.println("************************************************");
	}

	

}
