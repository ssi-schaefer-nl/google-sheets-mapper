package nl.ssi.googlesheetsmapper.serde;

import nl.ssi.googlesheetsmapper.serde.exceptions.NoAnnotatedFieldsException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SheetsSerializer {
    public List<Object> serialize(List<String> headers, Object object) throws NoAnnotatedFieldsException {
        List<Field> annotatedFields = Utils.getAnnotatedFields(object);
        return getOrderedDataFromFields(headers, annotatedFields, object);
    }

    private List<Object> getOrderedDataFromFields(List<String> headers, List<Field> annotatedFields, Object object) {
        Map<String, Field> fieldNameToField = Utils.mapFieldNameToField(annotatedFields);
        return headers.stream()
                .map(Utils::normalizeName)
                .map(headerName -> getDataFromField(object, fieldNameToField.get(headerName)).orElse(""))
                .collect(Collectors.toList());
    }


    private Optional<Object> getDataFromField(Object object, Field field) {
        try {
            field.setAccessible(true);
            Object data = field.get(object);
            if (data instanceof List) {
                data = convertListToCommaSeparatedString(data);
            }
            return Optional.ofNullable(data);
        } catch (NullPointerException | IllegalAccessException e) {
            return Optional.empty();
        }
    }

    private Object convertListToCommaSeparatedString(Object data) {
        return ((List<?>) data).stream().map(String::valueOf).collect(Collectors.joining(","));
    }






}
