import nl.ssi.googlesheetsmapper.annotation.SheetsMapping;

import java.util.List;

public class ArticleDTO {
    @SheetsMapping
    private String sku;
    @SheetsMapping
    private String status;
    @SheetsMapping
    private Integer totalAvailableStockCount;
    @SheetsMapping
    private Integer lengthInMm;
    @SheetsMapping
    private Integer heightInMm;
    @SheetsMapping
    private Integer widthInMm;
    @SheetsMapping
    private Integer weightInGm;
    @SheetsMapping
    private String description;
    @SheetsMapping
    private String barcode;
    @SheetsMapping
    private List<String> stockItemsRef;
    @SheetsMapping
    private Integer replenishmentThreshold;
    @SheetsMapping
    private List<Integer> integerList;

    public String getSku() {
        return sku;
    }

    public ArticleDTO setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ArticleDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public Integer getTotalAvailableStockCount() {
        return totalAvailableStockCount;
    }

    public ArticleDTO setTotalAvailableStockCount(Integer totalAvailableStockCount) {
        this.totalAvailableStockCount = totalAvailableStockCount;
        return this;
    }

    public Integer getLengthInMm() {
        return lengthInMm;
    }

    public ArticleDTO setLengthInMm(Integer lengthInMm) {
        this.lengthInMm = lengthInMm;
        return this;
    }

    public Integer getHeightInMm() {
        return heightInMm;
    }

    public ArticleDTO setHeightInMm(Integer heightInMm) {
        this.heightInMm = heightInMm;
        return this;
    }

    public Integer getWidthInMm() {
        return widthInMm;
    }

    public ArticleDTO setWidthInMm(Integer widthInMm) {
        this.widthInMm = widthInMm;
        return this;
    }

    public Integer getWeightInGm() {
        return weightInGm;
    }

    public ArticleDTO setWeightInGm(Integer weightInGm) {
        this.weightInGm = weightInGm;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ArticleDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public ArticleDTO setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public List<String> getStockItemsRef() {
        return stockItemsRef;
    }

    public ArticleDTO setStockItemsRef(List<String> stockItemsRef) {
        this.stockItemsRef = stockItemsRef;
        return this;
    }

    public Integer getReplenishmentThreshold() {
        return replenishmentThreshold;
    }

    public ArticleDTO setReplenishmentThreshold(Integer replenishmentThreshold) {
        this.replenishmentThreshold = replenishmentThreshold;
        return this;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "sku='" + sku + '\'' +
                ", status='" + status + '\'' +
                ", totalAvailableStockCount=" + totalAvailableStockCount +
                ", lengthInMm=" + lengthInMm +
                ", heightInMm=" + heightInMm +
                ", widthInMm=" + widthInMm +
                ", weightInGm=" + weightInGm +
                ", description='" + description + '\'' +
                ", barcode='" + barcode + '\'' +
                ", stockItemsRef=" + stockItemsRef +
                ", replenishmentThreshold=" + replenishmentThreshold +
                ", integerList="+ integerList +
                '}';
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public ArticleDTO setIntegerList(List<Integer> integerList) {
        this.integerList = integerList;
        return this;
    }
}