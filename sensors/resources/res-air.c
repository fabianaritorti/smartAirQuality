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
bool status = false;
bool is_auto = true;

static void res_get_handler(coap_message_t *request, coap_message_t *response, uint8_t *buffer, uint16_t preferred_size, int32_t *offset);
static void res_post_put_handler(coap_message_t *request, coap_message_t *response, uint8_t *buffer, uint16_t preferred_size, int32_t *offset);
static void res_event_handler(void);

EVENT_RESOURCE(res_irrigator, "title=\" Air actuator: ?POST/PUT auto=AUTO|MAN&status=ON|OFF\";rt=\"Irrigator\";obs",
	           res_get_handler,
               res_post_put_handler,
               res_post_put_handler,
               NULL,
               res_event_handler);

static void res_event_handler(void) {
	LOG_DBG("sending notification");
  	coap_notify_observers(&res_air);
}

static void res_get_handler(coap_message_t *request, coap_message_t *response, uint8_t *buffer, uint16_t preferred_size, int32_t *offset){
    unsigned int accept = -1;
	coap_get_header_accept(request, &accept);

	if(accept == -1)
		accept = APPLICATION_JSON;

	if(accept == APPLICATION_XML) {
		coap_set_header_content_format(response, APPLICATION_XML);
		snprintf((char *)buffer, COAP_MAX_CHUNK_SIZE, "<state=\"%d\"/>", lightOn);
		coap_set_payload(response, buffer, strlen((char *)buffer));
    
	} else if(accept == APPLICATION_JSON) {
		coap_set_header_content_format(response, APPLICATION_JSON);
		snprintf((char *)buffer, COAP_MAX_CHUNK_SIZE, "{\"state\":%d}", lightOn);
		coap_set_payload(response, buffer, strlen((char *)buffer));
  
	} else {
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
	const char *status = NULL;
    //rivedere bene la parte sia di air.c che delle risorse