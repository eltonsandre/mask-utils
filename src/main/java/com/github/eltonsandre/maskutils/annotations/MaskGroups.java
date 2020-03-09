package com.github.eltonsandre.maskutils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para marcar atributo com grupos de ofuscação
 * @author eltonsandre
 * date 06/03/2019 00:23
 */
@Target({ ElementType.FIELD })// ElementType.ANNOTATION_TYPE,
@Retention(RetentionPolicy.RUNTIME)
public @interface MaskGroups {

    MaskField[] value();

}
