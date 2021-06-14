CONTIKI_PROJECT = coap-air-node
all: $(CONTIKI_PROJECT)
CONTIKI = ../..


CFLAGS += -DPROJECT_CONF_H=\"project-conf.h\"

# Include optional target-specific module
include $(CONTIKI)/Makefile.identify-target
MODULES_REL += $(TARGET)

# Include CoAP resources
MODULES_REL += ./resources



# Include the CoAP implementation
include $(CONTIKI)/Makefile.dir-variables
MODULES += $(CONTIKI_NG_APP_LAYER_DIR)/coap

include $(CONTIKI)/Makefile.include