package com.github.eltonsandre.maskutils;

import com.github.eltonsandre.maskutils.annotations.MaskField;
import com.github.eltonsandre.maskutils.annotations.MaskGroups;
import com.github.eltonsandre.maskutils.enuns.TypeMaskEnum;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author eltonandre
 * date 03/03/2019 19:02
 */
public class MaskUtils {

    /**
     * @param obj objeto para obfuscar
     * @param <T> generic
     */
    public static <T> void mask(T obj) {
        mask(obj, null);
    }

    /**
     * @param obj objeto para obfuscar
     * @param <T> generic
     * @return objeto generic
     */
    public static <T> Object maskClone(T obj) {
        final Object object = ObjectUtils.cloneIfPossible(obj);
        return mask(object, null);
    }

    /**
     * @param object objeto para obfuscar
     * @param group grupo para aplicar a ofuscação
     * @param <T> generic
     * @return objeto generic
     */
    public static <T> Object mask(T object, final String group) {
        Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> maskField(field, object, group));
        return object;
    }

    @SneakyThrows({ IllegalAccessException.class })
    private static Field maskField(final Field field, final Object object, final String group) {
        field.setAccessible(true);
        final Object value = field.get(object);

        TypeMaskEnum typeMaskEnum = TypeMaskEnum.getTypeAnnotation(field);
        if (Objects.nonNull(value) && typeMaskEnum.isKnow()) {
            typeMaskEnum.maskField(field, value, object, group);
        }
        return field;
    }

    private static boolean maskFieldByGruop(String group, final String[] groups) {
        return Arrays.asList(groups).contains(group);
    }

    private static boolean maskFieldByGruop(MaskField annotation, String group) {
        return Objects.nonNull(annotation) && ((Objects.isNull(group) && annotation.namesGroup().length == 0)
                || maskFieldByGruop(group, annotation.namesGroup()));
    }

    /**
     * @param field objeto para obfuscar
     * @param value instancia do atributo
     * @param object incial generic (remover)
     * @param group grupo para aplicar a ofuscação
     */
    public static void maskCollection(Field field, final Object value, final Object object, final String group) {
        if (value instanceof Collection<?>) {
            for (Object obs : (Collection<?>) value) {
                mask(obs, group);
            }
        }

        if (value instanceof Map<?, ?>) {
            //TODO criar para Map
        }
    }

    @SneakyThrows({ IllegalAccessException.class })
    private static void complexObject(final Field field, MaskField annotation, final Object value, final Object object,
            final String group) {
        field.set(object, mask(value, group));
    }

    public static void maskGroupField(final Field field, final Object value, final Object object, final String group) {
        final MaskGroups maskGroups = field.getAnnotation(MaskGroups.class);
        if (Objects.nonNull(maskGroups)) {
            for (MaskField maskField : maskGroups.value()) {
                maskField(field, maskField, value, object, group);
            }
        }
        maskField(field, value, object, group);
    }

    public static void maskField(final Field field, final Object value, final Object object, final String group) {
        final MaskField annotation = field.getAnnotation(MaskField.class);
        maskField(field, annotation, value, object, group);
    }

    @SneakyThrows({ IllegalAccessException.class })
    private static void maskField(final Field field, MaskField annotation, final Object value, final Object object, final String group) {
        if (maskFieldByGruop(annotation, group)) {
            final String mask = annotation.remove() ? null
                    : Pattern.compile(annotation.regex())
                    .matcher((String) value)
                    .replaceAll(annotation.replacement());
            field.set(object, mask);
        }
    }

}
