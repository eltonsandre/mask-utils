package com.github.eltonsandre.maskutils.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para marcar atributo como tipo complexo acessar atributos e ofuscar
 * @author eltonsandre
 * date 03/03/2019 21:18
 */
@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaskObjectData {

    String[] nameGroups() default {};

}