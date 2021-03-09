package nl.ssi.googlesheetsmapper.serde;

import nl.ssi.googlesheetsmapper.serde.exceptions.ClassInstatiationException;
import nl.ssi.googlesheetsmapper.serde.exceptions.NoAnnotatedFieldsException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SheetsDeserializer {

    public <T> T deserialize(List<String> headers, List<Object> data, Class<T> type) throws ClassInstatiationException, NoAnnotatedFieldsException, IllegalAccessException {
        Map<String, Object> headersToDataMap = mapHeadersToData(headers, data);
        return getPopulatedObject(type, headersToDataMap);
    }

    private <T> T getPopulatedObject(Class<T> type, Map<String, Object> headersToDataMap) throws ClassInstatiationException, NoAnnotatedFieldsException, IllegalAccessException {
        T instanceOfType = getInstanceOfType(type);
        for (Field field : Utils.getAnnotatedFields(instanceOfType)) {
            Object fieldData = headersToDataMap.get(Utils.normalizeName(field.getName()));
            setFieldData(instanceOfType, field, fieldData);
        }
        return instanceOfType;
    }

    private void setFieldData(Object instanceOfType, Field field, Object fieldData) throws IllegalAccessException {
        field.setAccessible(true);
        if (field.getType().isAssignableFrom(List.class)) {
            fieldData = convertCsvStringToTypedList(field, fieldData);
        }
        field.set(instanceOfType, fieldData);
    }

    private List<?> convertCsvStringToTypedList(Field field, Object fieldData) {
        return Arrays.stream(Utils.normalizeName((String) fieldData).split(","))
                .map(element -> Utils.stringToObject(field.getGenericType(), element))
                .collect(Collectors.toList());
    }


    private Map<String, Object> mapHeadersToData(List<String> headers, List<Object> data) {
        return IntStream.range(0, data.size()).boxed().collect(Collectors.toMap(i -> Utils.normalizeName(headers.get(i)), data::get, (a, b) -> b));
    }

    private <T> T getInstanceOfType(Class<T> type) throws ClassInstatiationException {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ClassInstatiationException("Unable to make instance of type " + type.getSimpleName());
        }
    }
}
