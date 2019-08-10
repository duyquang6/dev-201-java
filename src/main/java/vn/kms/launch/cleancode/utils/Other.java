package vn.kms.launch.cleancode.utils;

import vn.kms.launch.cleancode.annotation.Column;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Other {
    private static final Logger LOGGER = Logger.getLogger(Other.class.getName());

    private Other() {
    }

    public static void addFieldERROR(Map<String, Integer> counts, String fieldName) {
        Integer count = counts.get(fieldName);
        if (count == null) {
            count = 0;
        }
        count = count + 1;
        counts.put(fieldName, count);
    }

    public static int tryGetIntWithDefault(String string, int defaultValue) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static void mapDataToObject(String[] dataColumns, Map<String, Integer> headerIndexByFieldName, Object object) {
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            Column column = method.getAnnotation(Column.class);
            if (column != null) {
                try {
                    if (column.type().equals("INTEGER")) {
                        method.invoke(object, Integer.parseInt(dataColumns[headerIndexByFieldName.get(column.header())]));
                    } else {
                        method.invoke(object, dataColumns[headerIndexByFieldName.get(column.header())]);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    LOGGER.log(Level.WARNING, "Got an exception. ", e);
                }
            }
        }
    }

    public static int findHeaderIndex(String line, String header) {
        String[] headers = line.split("\t");
        return Arrays.asList(headers).indexOf(header);
    }
}
