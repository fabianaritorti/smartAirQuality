# smartAirQuality

This an Iot-Project about a depuration air system based on Cooja simulation and java application with Californium. 

After downloading the repository in contiki-ng directory, please do the following commands to run the application:

1.Start  contiki-ng

2.Go in the rpl-border-router directory :

	make TARGET=cooja connect-router-cooja

3.Go in the coap-server directory and start the java application:

	java -jar target/app-0.0.1-SNAPSHOT.jar	


