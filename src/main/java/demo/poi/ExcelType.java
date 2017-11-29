package demo.poi;

public enum ExcelType {

    XLS(".xls"), XLSX(".xlsx");

    private String suffix;

    ExcelType(String suffix) {
        this.suffix = suffix;
    }

    protected String getSuffix() {
        return suffix;
    }

}
