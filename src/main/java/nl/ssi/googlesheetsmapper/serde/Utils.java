package nl.ssi.googlesheetsmapper.serde;

import nl.ssi.googlesheetsmapper.annotation.SheetsMapping;
import nl.ssi.googlesheetsmapper.serde.exceptions.NoAnnotatedFieldsException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static List<Field> getAnnotatedFields(Object object) throws NoAnnotatedFieldsException {
        List<Field> fields = Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SheetsMapping.class))
                .collect(Collectors.toList());

        if (fields.isEmpty()) {
            throw new NoAnnotatedFieldsException("Missing annotation " + SheetsMapping.class.getSimpleName() + "on all fields");
        }
        return fields;
    }

    public static String normalizeName(String fieldName) {
        return fieldName.toLowerCase().replaceAll("\\s+", "");
    }

    public static Map<String, Field> mapFieldNameToField(List<Field> annotatedFields) {
        Map<String, Field> fieldNameToField = new HashMap<>();
        annotatedFields.forEach(field -> fieldNameToField.put(getNameOfField(field), field));
        return fieldNameToField;
    }

    public static Object stringToObject(Type t, String value) {
        Type type = ((ParameterizedType) t).getActualTypeArguments()[0];
        if (type.equals(Boolean.class)) return Boolean.parseBoolean(value);
        if (type.equals(Byte.class)) return Byte.parseByte(value);
        if (type.equals(Short.class)) return Short.parseShort(value);
        if (type.equals(Integer.class)) return Integer.parseInt(value);
        if (type.equals(Long.class)) return Long.parseLong(value);
        if (type.equals(Float.class)) return Float.parseFloat(value);
        if (type.equals(Double.class)) return Double.parseDouble(value);
        return value;
    }

    private static String getNameOfField(Field field) {
        String fieldName = field.getAnnotation(SheetsMapping.class).name();
        if (fieldName.isEmpty()) {
            fieldName = Utils.normalizeName(field.getName());
        }
        return fieldName;
    }
}
