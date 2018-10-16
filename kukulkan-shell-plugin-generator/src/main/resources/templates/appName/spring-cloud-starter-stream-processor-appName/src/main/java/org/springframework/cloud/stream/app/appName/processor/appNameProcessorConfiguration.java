package org.springframework.cloud.stream.app.${appName}.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MutableMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * A Processor app.
 *
 */
@EnableBinding(Processor.class)
@EnableConfigurationProperties(${appName?cap_first}ProcessorProperties.class)
public class ${appName?cap_first}ProcessorConfiguration {

	@Autowired
	private ${appName?cap_first}ProcessorProperties properties;

	@ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	public Object transform(Message<?> message) {
		return message;
	}

}
