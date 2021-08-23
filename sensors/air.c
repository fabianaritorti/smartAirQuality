
#include <stdio.h>
#include <stdlib.h>
#include "coap-log.h"
#include "sys/log.h"
#include "sys/node-id.h"
#include "contiki.h"
#include "coap-engine.h"
#include "coap-blocking-api.h"
#include "sys/etimer.h"
#include "os/dev/leds.h"

#define LOG_MODULE "NODE"
#define LOG_LEVEL LOG_LEVEL_DBG

#define P_THRESHOLD 50
#define Q_THRESHOLD 50

//resource definition
extern coap_resource_t res_presence; 
extern coap_resource_t res_quality; 
extern coap_resource_t res_air; 

extern bool air_state;
extern int presence;
extern int quality;
bool air_state_old;
bool registered = false;



#define SERVER_EP "coap://[fd00::1]:5683"

PROCESS(air_node, "Air quality node");
AUTOSTART_PROCESSES(&air_node);

static struct etimer timer;

void client_chunk_handler(coap_message_t *response) {
  
	const uint8_t *chunk;

	if(response == NULL) {
		puts("Request timed out");
	return;
	}

	if(!registered)
	registered = true;

	int len = coap_get_payload(response, &chunk);
	printf("|%.*s", len, (char *)chunk);
}

PROCESS_THREAD(air_node, ev, data){

	static coap_endpoint_t server_ep;
	static coap_message_t request[1];

	PROCESS_BEGIN();
	

	leds_set(LEDS_NUM_TO_MASK(LEDS_RED)); // at the beginning, all lights are off

	
	coap_activate_resource(&res_air, "res_air");
    coap_activate_resource(&res_quality, "res_quality");
	//coap_activate_resource(&res_presence, "res_presence");

	coap_endpoint_parse(SERVER_EP, strlen(SERVER_EP), &server_ep);

	coap_init_message(request, COAP_TYPE_CON, COAP_GET ,0);
	coap_set_header_uri_path(request, "registration");
	

	char msg[4];
	
	//sprintf(msg,"%d",node_id);
	coap_set_payload(request, (uint16_t * )msg, sizeof(msg)-1);

	//per registrare la risorsa, commentare da riga 81 ad 84
	while(!registered){
		LOG_DBG("Retrying registration..\n");
		COAP_BLOCKING_REQUEST(&server_ep, request, client_chunk_handler);
	}

	LOG_DBG("Registered\n");

    //genero un valore intero randomico ogni 20 secondi(per la presenza e la qualità dell'aria)
    etimer_set(&timer,30 * CLOCK_SECOND);
	printf("TIMER:%d \n",timer);

    while(true) {
        PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&timer));
		//printf("YOU ARE IN");
		
		
        //HO AGGIUNTO LA RIGA 99
		//LOG_DBG("quality value: %d\n", quality);

		air_state_old = air_state;
		int qualityToAdd = 1+ rand()%10;

		if (air_state_old == 0) {
			quality = quality - qualityToAdd;

		}
		//genero valori casuali di presenza e qualità (numero da 1 a 100)
        // presence = 1 + rand()%100;
		// LOG_DBG("presence value: %d\n", presence);
		// if (presence <= P_THRESHOLD) {
		// 	LOG_DBG("Presence not detected!\n");
		// }
		// else if (presence > P_THRESHOLD) {
		// 	LOG_DBG("Presence detected! \n");
		// }

        //quality = 1 + rand()%100;

		
        //LOG_DBG("presence: %d\n", presence);
		//HO COMMENTATO LA RIGA 123
        LOG_DBG("quality value: %d\n", quality);
        // if (presence <= P_THRESHOLD) {
		// 	air_state = 0;
        //     leds_set(LEDS_NUM_TO_MASK(LEDS_RED));
        // }
        // else if (presence > P_THRESHOLD && quality <= Q_THRESHOLD) {
		// 	air_state = 1;
        //     leds_set(LEDS_NUM_TO_MASK(LEDS_GREEN));
        //     //METTERE QUALCOSA PER FAR IN MODO CHE LA QUALITA' DELL'ARIA SIA BUONA
        // }
		if (quality <= Q_THRESHOLD) { 
			//LOG_DBG("Presence detected! \n");
			//LOG_DBG("quality value: %d\n", quality);
			LOG_DBG("Air quality is bad! \n");
			air_state = 1;
			leds_set(LEDS_NUM_TO_MASK(LEDS_GREEN));
			quality = quality+qualityToAdd;
			//COMMENTO PER ME HO COMMENTATO LA RIGA 141
			LOG_DBG("quality value: %d\n", quality);
			//LOG_DBG("Air quality is good! \n");
			
			
		}
		if (quality > Q_THRESHOLD) {
			//HO AGGIUNTO LA RIGA 147
			//LOG_DBG("quality value: %d\n", quality);
			LOG_DBG("Air quality is good! \n");
			leds_set(LEDS_NUM_TO_MASK(LEDS_RED));
			air_state = 0;
			
		}

		if (air_state != air_state_old) { // when state changes, trigger call
			//res_air.trigger();
			//res_presence.trigger();
			
			res_quality.trigger();
			//res_presence.trigger();
			
			
		}
        etimer_reset(&timer);
            

        
        
    }
	PROCESS_END();
}

