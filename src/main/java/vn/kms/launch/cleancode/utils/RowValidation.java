package vn.kms.launch.cleancode.utils;

import static vn.kms.launch.cleancode.utils.Constant.MAX_COLUMN;

public class RowValidation {
    public static boolean isContainBlankField(String[] dataColumns) {
        return dataColumns.length != MAX_COLUMN;
    }

    public static boolean isRowDataInvalid(int rowIndex, String line) {
        String[] dataColumns = line.split("\t");
        return isCurrentRowHeader(rowIndex) ||
                isRowBlank(line) ||
                isContainBlankField(dataColumns);
    }

    public static boolean isCurrentRowHeader(int rowIndex) {
        return rowIndex == 0;
    }

    public static boolean isRowBlank(String line) {
        int countCharInLine = line.trim().length();
        return countCharInLine == 0;
    }
}
