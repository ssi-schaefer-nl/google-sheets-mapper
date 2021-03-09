import nl.ssi.googlesheetsmapper.serde.SheetsDeserializer;
import nl.ssi.googlesheetsmapper.serde.SheetsSerializer;
import nl.ssi.googlesheetsmapper.serde.Utils;
import nl.ssi.googlesheetsmapper.serde.exceptions.ClassInstatiationException;
import nl.ssi.googlesheetsmapper.serde.exceptions.NoAnnotatedFieldsException;
import nl.ssi.googlesheetsmapper.serde.exceptions.NoMappedFieldsFoundException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class SerdeTest {

    @Test
    void shouldSerialize() {
        List<String> testHeader = Arrays.asList("sku", "status", "total available stock count", "description", "barcode", "StockItemsRef");

        SheetsSerializer serializer = new SheetsSerializer();
        ArticleDTO articleDTO = new ArticleDTO()
                .setSku("1")
                .setStatus("status")
                .setTotalAvailableStockCount(5)
                .setDescription("description")
                .setBarcode("1")
                .setStockItemsRef(Arrays.asList("a", "b", "c"));

        try {
            List<Object> serializedResult = serializer.serialize(testHeader, articleDTO);
            serializedResult.forEach(field -> System.out.println(field.getClass() + " : " + field));
        } catch (NoAnnotatedFieldsException | NoMappedFieldsFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldDeserialize() {
        String sku = "sku";
        String status = "status";
        Integer ttlStockCount = 1;
        String description = "description";
        String barcode = "barcode";
        String stockItemsRef = "ref1, ref2, ref3";
        String integerList = "1, 2, 3, 4";

        List<String> expectedStringList = Arrays.asList(stockItemsRef.replaceAll("\\s+", "").split(","));
        List<Integer> expectedIntegerList = Arrays.asList(1, 2, 3, 4);

        List<String> testHeader = Arrays.asList("sku", "status", "total available stock count", "description", "barcode", "StockItemsRef", "integerList");
        List<Object> testData = Arrays.asList(sku, status, ttlStockCount, description, barcode, stockItemsRef, integerList);

        SheetsDeserializer deserializer = new SheetsDeserializer();
        ArticleDTO articleDTO = null;
        try {
            articleDTO = deserializer.deserialize(testHeader, testData, ArticleDTO.class);
            assertEquals(sku, articleDTO.getSku());
            assertEquals(status, articleDTO.getStatus());
            assertEquals(ttlStockCount, articleDTO.getTotalAvailableStockCount());
            assertEquals(description, articleDTO.getDescription());
            assertEquals(barcode, articleDTO.getBarcode());
            assertTrue(expectedStringList.equals(articleDTO.getStockItemsRef()));
            assertTrue(expectedIntegerList.equals(articleDTO.getIntegerList()));
        } catch (ClassInstatiationException | NoAnnotatedFieldsException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(articleDTO);
    }
}
