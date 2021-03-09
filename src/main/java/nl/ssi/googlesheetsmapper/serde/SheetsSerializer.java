package nl.ssi.googlesheetsmapper.serde;

import nl.ssi.googlesheetsmapper.serde.exceptions.NoAnnotatedFieldsException;
import nl.ssi.googlesheetsmapper.serde.exceptions.NoMappedFieldsFoundException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class SheetsSerializer {
    public List<Object> serialize(List<String> headers, Object object) throws NoAnnotatedFieldsException, NoMappedFieldsFoundException {
        List<Field> annotatedFields = Utils.getAnnotatedFields(object);
        return getOrderedDataFromFields(headers, annotatedFields, object);
    }

    private List<Object> getOrderedDataFromFields(List<String> headers, List<Field> annotatedFields, Object object) throws NoMappedFieldsFoundException {
        Map<String, Field> fieldNameToField = Utils.mapFieldNameToField(annotatedFields);
        List<Object> orderedFields = headers.stream()
                .map(Utils::normalizeName)
                .map(headerName -> getDataFromField(object, fieldNameToField.get(headerName)).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (orderedFields.isEmpty()) {
            throw new NoMappedFieldsFoundException("No mapped fields for object of type " + object.getClass().getSimpleName());
        }
        return orderedFields;
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
