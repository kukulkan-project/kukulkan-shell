package org.springframework.cloud.stream.app.${appName}.processor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Configuration properties for the ${appName?cap_first} Processor module.
 *
 */
@ConfigurationProperties("${appName}")
public class ${appName?cap_first}ProcessorProperties {

}
