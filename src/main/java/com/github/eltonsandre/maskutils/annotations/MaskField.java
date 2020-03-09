package com.github.eltonsandre.maskutils.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para marcar atributo com ofuscação
 * @author eltonsandre
 * date 03/03/2019 19:21
 */
@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaskField {

    String regex() default ".";

    String replacement() default "\\*";

    boolean remove() default false;

    String[] namesGroup() default {};

    //TODO groups via class
    //    Class<?>[] groups() default {};
}
