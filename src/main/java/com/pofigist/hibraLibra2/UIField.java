package com.pofigist.hibraLibra2;

import java.lang.annotation.*;

@Target(value=ElementType.FIELD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface UIField {
	String title() default "";
	boolean useInCreateDialog() default false;
	boolean showInTable() default false;
	String source() default "";
}
