package com.github.eltonsandre.maskutils.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para marcar atributo como coleção para interar e ofuscar
 * @author eltonsandre
 * date 06/03/2019 02:40
 */
@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaskCollectonData {
}
