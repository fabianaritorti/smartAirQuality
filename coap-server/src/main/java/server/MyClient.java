package server;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import io.netty.handler.codec.json.JsonObjectDecoder;

public class MyClient extends CoapClient {

	CoapObserveRelation obs;
	private Resource resource;
	
	
	public MyClient(Resource resource) {
		//viene restituito l'host address relativo a questa risorsa(nodo)
		super(resource.getCoapURI());
		this.resource = resource;
	}
	
	public void startObserving() {
		//si manda una richiesta di observe e si invoca un handler ogni volta che una notifica arriva (HANDLER = CAOP RECEIVING MESSAGES)
		obs = this.observe(new CoapHandler() {
			
			//questo metodo viene invocato quando arriva una risposta o notifica COAP
			public void onLoad(CoapResponse response) {
				// TODO Auto-generated method stub
				try {
					String value = "";
					
					JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(response.getResponseText());
					if (resource.getName().contains("quality")){
						Long v = (Long) jsonObject.get("value");
						resource.setValue(v);
						if (v < 50) {
							resource.setState(true);
						} else if (v > 50) {
							resource.setState(false);
						}
					}
					
					
					
									
						


					if (MainApplication.observeMode == true) {
						Date date = new Date();
						Timestamp timestamp = new Timestamp(date.getTime());
						
						System.out.println("NODE IP:"+ "\n" + resource.getIp());
						resource.showStatus();
						
						if (jsonObject.containsKey("value")) {
							System.out.println("TIMESTAMP:"+ timestamp);
						}
						
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			public void onError() {
				System.out.println("Error in observing request!.");
				// TODO Auto-generated method stub
				
			}
		});
	}

}




