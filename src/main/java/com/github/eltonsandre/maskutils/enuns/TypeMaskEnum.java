package com.github.eltonsandre.maskutils.enuns;

import com.github.eltonsandre.maskutils.MaskUtils;
import com.github.eltonsandre.maskutils.annotations.MaskCollectonData;
import com.github.eltonsandre.maskutils.annotations.MaskField;
import com.github.eltonsandre.maskutils.annotations.MaskGroups;
import com.github.eltonsandre.maskutils.annotations.MaskObjectData;
import java.lang.reflect.Field;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author eltonsandre
 * date 06/03/2019 12:18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypeMaskEnum {
    /**
     * tipo atributo
     */
    FIELD {
        @Override
        public void maskField(final Field field, final Object value, final Object object, final String group) {
            MaskUtils.maskField(field, value, object, group);
        }
    },
    /**
     * Agrupamento de campos
     */
    GROUP_FIELD {
        @Override
        public void maskField(final Field field, final Object value, final Object object, final String group) {
            MaskUtils.maskGroupField(field, value, object, group);
        }
    },
    /**
     * tipo de modelo/objeto complexo
     */
    MODEL {
        @SneakyThrows
        @Override
        public void maskField(final Field field, final Object value, final Object object, final String group) {
            field.set(object, MaskUtils.mask(value, group));
        }
    },
    /**
     * Caso a anotação seja do tipo collection/map
     */
    COLLECTION {
        @Override
        public void maskField(final Field field, final Object value, final Object object, final String group) {
            MaskUtils.maskCollection(field, value, object, group);
        }
    },
    /**
     * Não exista anotação conhecida pelo mask
     */
    UNKNOWN {
        @Override
        public void maskField(final Field field, final Object value, final Object object, final String group) {
        }
    };

    /**
     * @param field atributo do objeto
     * @return TypeMaskEnum
     * @see java.lang.reflect.Field
     */
    public static TypeMaskEnum getTypeAnnotation(final Field field) {
        if (Objects.nonNull(field.getAnnotation(MaskField.class))) {
            return TypeMaskEnum.FIELD;
        }
        if (Objects.nonNull(field.getAnnotation(MaskObjectData.class))) {
            return TypeMaskEnum.MODEL;
        }
        if (Objects.nonNull(field.getAnnotation(MaskGroups.class))) {
            return TypeMaskEnum.GROUP_FIELD;
        }
        if (Objects.nonNull(field.getAnnotation(MaskCollectonData.class))) {
            return TypeMaskEnum.COLLECTION;
        }
        return TypeMaskEnum.UNKNOWN;
    }

    public abstract void maskField(final Field field, final Object value, final Object object, final String group);

    public boolean isKnow() {
        return !TypeMaskEnum.UNKNOWN.equals(this);
    }

    public boolean isKnow(final Field field) {
        return !TypeMaskEnum.UNKNOWN.equals(getTypeAnnotation(field));
    }
}
