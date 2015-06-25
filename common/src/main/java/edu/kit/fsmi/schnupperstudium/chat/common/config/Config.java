package edu.kit.fsmi.schnupperstudium.chat.common.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a value as configuration value.
 * 
 * @author Daniel Wieland
 * @since Apr 30, 2015
 * @version 1.0.0
 *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface Config {
	/** key used in the configuration file. */
	String key() default "";
}
