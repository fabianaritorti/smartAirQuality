#include <stdlib.h>
#include <string.h>
#include "coap-engine.h"
#include "contiki.h"
#include "os/dev/leds.h"

#include "sys/log.h"
#define LOG_MODULE "Air Actuator"
#define LOG_LEVEL LOG_LEVEL_DBG


extern struct process air_node;
process_event_t POST_EVENT;
bool air_state = 0;
//bool lightOn = 0;
//static int counter = 0;
//bool is_auto = true;

static void res_get_handler(coap_message_t *request, coap_message_t *response, uint8_t *buffer, uint16_t preferred_size, int32_t *offset);
static void res_post_put_handler(coap_message_t *request, coap_message_t *response, uint8_t *buffer, uint16_t preferred_size, int32_t *offset);
static void res_event_handler(void);

EVENT_RESOURCE(res_air, "title=\" Air actuator: ?POST/PUT auto=AUTO|MAN&status=ON|OFF\";rt=\"Air actuator\";obs",
	           res_get_handler,
               res_post_put_handler,
               res_post_put_handler,
               NULL);
 //              res_event_handler);

// static void res_event_handler(void) {
// 	LOG_DBG("sending notification");
//   	coap_notify_observers(&res_air);
// }

static void res_get_handler(coap_message_t *request, coap_message_t *response, uint8_t *buffer, uint16_t preferred_size, int32_t *offset){


	if(request != NULL){
		LOG_DBG("Received GET\n");
		//LOG_DBG("Observing handler number %d\n", counter); 
	}


	unsigned int accept = -1;
	coap_get_header_accept(request, &accept);


	if (accept== -1)
		accept = APPLICATION_JSON;

	if(accept == TEXT_PLAIN) {
	    coap_set_header_content_format(response, TEXT_PLAIN);
	    snprintf((char *)buffer, COAP_MAX_CHUNK_SIZE, "air_state=%d", air_state);
	    coap_set_payload(response, (uint8_t *)buffer, strlen((char *)buffer));    
	} else if(accept == APPLICATION_XML) {
		coap_set_header_content_format(response, APPLICATION_XML);
 		snprintf((char *)buffer, COAP_MAX_CHUNK_SIZE, "<air_state=\"%d\"/>", air_state);
		coap_set_payload(response, buffer, strlen((char *)buffer));
    	} 
	else if(accept == APPLICATION_JSON) {
		coap_set_header_content_format(response, APPLICATION_JSON);
		snprintf((char *)buffer, COAP_MAX_CHUNK_SIZE, "{\"air_state\":%d}", air_state);
		coap_set_payload(response, buffer, strlen((char *)buffer));
	}
	else {
		coap_set_status_code(response, NOT_ACCEPTABLE_4_06);
		const char *msg = "Supporting content-type application/json";
		coap_set_payload(response, msg, strlen(msg));
  	}

}



static void res_post_put_handler(coap_message_t *request, coap_message_t *response, uint8_t *buffer, uint16_t preferred_size, int32_t *offset)
{
	if(request != NULL) {
		LOG_DBG("POST/PUT Request Sent\n");
	}
    size_t len = 0;
	const char *state = NULL;
	int success = 1;

	

    //rivedere bene la parte sia di air.c che delle risorse
	if((len = coap_get_post_variable(request, "state", &state))) {
		//atoi function convert a string argument to an integer 
		if(strncmp(state, "ON", len) == 0) {
			printf("CIAOOO");
			air_state = 1;
			LOG_DBG("Purification Air Started! \n");
			leds_set(LEDS_NUM_TO_MASK(LEDS_GREEN));
		} else if(strncmp(state, "OFF", len) == 0) {
			air_state = 0;
			LOG_DBG("Purification Air Stopped \n");
			leds_set(LEDS_NUM_TO_MASK(LEDS_RED));
		// if (atoi(status) == 1){
		// 	air_state = 1;
		// 	LOG_DBG("Purification Air Started! \n");
		// 	leds_set(LEDS_NUM_TO_MASK(LEDS_GREEN));
			
		// }
		
		// else if(atoi(status) == 0){
		// 	air_state = 0;
		// 	LOG_DBG("Purification Air Stopped! \n");
		// 	leds_set(LEDS_NUM_TO_MASK(LEDS_RED));

		}
		else{
			success = 0;
		}
		//air_state = atoi(status);
		
		
		
	}
	else{
		success = 0;
	}

	if (success){
		coap_set_status_code(response, CHANGED_2_04);
	}
	else{
		coap_set_status_code(response, BAD_REQUEST_4_00);
	}
		
}