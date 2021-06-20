package server;

import java.net.URI;
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
	private Quality quality;
	String keyQuality;
	
	public MyClient(Quality q) {
		super(q.getCoapURI());
		this.quality = q;
	}
	
	public void startObserving() {
		obs = this.observe(new CoapHandler() {
			
			
			public void onLoad(CoapResponse response) {
				// TODO Auto-generated method stub
				try {
					String value;
					JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(response.getResponseText());
					Integer THRESHOLD = 100 ;
					if (jsonObject.containsKey("quality")) {
						value = jsonObject.get("quality").toString();
						int valueQuality = Integer.parseInt((value).trim());
						keyQuality = getQualityKeys(MainApplication.getQualityMap(), quality);
						
						Air air = MainApplication.getAirMap().get(keyQuality);						
						
						
						if (valueQuality < THRESHOLD) {
							air.setState(true);
						} else if (valueQuality > THRESHOLD){
							air.setState(false);
						}
						
						//TODO settare l'hashMap in base al valore della qualità
					} else {
						System.out.println("This quality value is not founded here!");
						return;
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
	
	//mi ritorno tutte le chiavi associati al valore della qualità
	public static <String,Quality> String getQualityKeys(Map <String,Quality> map, Quality q) {
		for (String k: map.keySet()) {
			if (q.equals(map.get(k))) {
				return k;
			}
		}
		return null;
		
	}
	

}
