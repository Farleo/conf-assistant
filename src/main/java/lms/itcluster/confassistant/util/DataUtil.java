package lms.itcluster.confassistant.util;

import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class DataUtil {

    public static <T extends Annotation> void copyFields(final Object from, final Object to, Class<T> annotation) {
        ReflectionUtils.doWithFields(to.getClass(), (ReflectionUtils.FieldCallback) field -> {
            ReflectionUtils.makeAccessible(field);
            copyAccessibleField(field, from, to);
        }, new org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter(annotation));
    }

    private static void copyAccessibleField(Field field, Object from, Object to) throws IllegalAccessException {
        Object fromValue = field.get(from);
        Object toValue = field.get(to);
        if (fromValue == null) {
            if (toValue != null) {
                field.set(to, null);
            }
        } else {
            if (!fromValue.equals(toValue)) {
                field.set(to, fromValue);
            }
        }
    }

}
