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
					//Integer THRESHOLD = 50 ;
					//containsKey ritorna vero se la mappa(jsonObject) contiene questo valore
					if (resource.getName().contains("air")) {
						//la get ritorna il valore associato alla chiave
						value = jsonObject.get("status").toString();
						if (value.equals("ON")) {
							if (!resource.isState()) {
								System.out.println("Depuration air started");
							}
							resource.setState(true);
						} else if (value.equals("OFF")){
							if (resource.isState()) {
								System.out.println("Depuration air stopped");
							}
							resource.setState(false);
							
						}
						
						//si converte il valore da stringa ad intero
						//int valueQuality = Integer.parseInt((value).trim());
//						keyQuality = getQualityKeys(MainApplication.getQualityMap(), quality);
//						
//						Air air = MainApplication.getAirMap().get(keyQuality);						
						
//						
//						if (valueQuality < THRESHOLD) {
//							//resituisce l'indice del primo valore della quality nella lista
//							//int index = MainApplication.getQualityList().indexOf(quality);
//							//mi prendo lo stesso indice perchè equivale allo stesso host
//							//Air air = MainApplication.getAirList().get(index);
//							if (!air.isState()) 
//								air.setState(true);
//						} else if (valueQuality > THRESHOLD){
//							//int index = MainApplication.getQualityList().indexOf(quality);
//							//Air air = MainApplication.getAirList().get(index);
//							if (air.isState()) 
//								air.setState(false);
//							}
						
						
						//TODO settare l'hashMap in base al valore della qualità
					} else if (resource.getName().contains("quality")){
						int v = (Integer) jsonObject.get("value");
						resource.setValue(v);
						//TODO HERE
						
					}
					if (MainApplication.observeMode == true) {
						Date date = new Date();
						Timestamp timestamp = new Timestamp(date.getTime());
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
				System.out.println("Error in observing request.");
				// TODO Auto-generated method stub
				
			}
		});
	}
//	questo metterlo quando la qualità dell'aria e l 'aria hanno la stessa chiave
//	public static <String, Quality> String getQualityKeys(Map <String, Quality> map, Quality q) {
//		for(String k: map.keySet()) {
//			if(q.equals(map.get(k))) {
//				return k;
//			}
//		}
//		
//		return null;
//	}
}




