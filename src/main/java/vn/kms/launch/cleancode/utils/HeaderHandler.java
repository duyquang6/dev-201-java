package vn.kms.launch.cleancode.utils;

import vn.kms.launch.cleancode.annotation.Column;
import vn.kms.launch.cleancode.annotation.Header;
import vn.kms.launch.cleancode.model.Address;
import vn.kms.launch.cleancode.model.Contact;
import vn.kms.launch.cleancode.model.Person;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static vn.kms.launch.cleancode.utils.Other.findHeaderIndex;

public class HeaderHandler {
    public static ArrayList<String> getColumnHeaders(Class theClass) {
        ArrayList<String> columnHeaders = new ArrayList<>();
        for (Method method : theClass.getMethods()) {
            Column column = method.getAnnotation(Column.class);
            if (column != null)
                columnHeaders.add(column.header());
        }
        return columnHeaders;
    }

    static ArrayList<String> getHeaders(Class theClass) {
        ArrayList<String> columnHeaders = new ArrayList<>();
        for (Field field : theClass.getDeclaredFields()) {
            Header header = field.getAnnotation(Header.class);
            if (header != null)
                columnHeaders.add(header.value());
        }
        return columnHeaders;
    }

    public static String columnHeadersToString(Class theClass) {
        ArrayList<String> columnHeaders = getHeaders(theClass);
        StringBuilder stringResult = new StringBuilder();
        Iterator<String> iterator = columnHeaders.iterator();
        while (iterator.hasNext()) {
            String columnHeader = iterator.next();
            if (iterator.hasNext()) {
                stringResult.append(columnHeader + "\t");
            } else {
                stringResult.append(columnHeader + "\r\n");
            }
        }
        return stringResult.toString();
    }

    /**
     * Read first row and column header name from annotation
     * Getting column index corresponding to annotation
     *
     * @return A Map from header name to column index
     */
    public static Map<String, Integer> findHeaderIndexsByFieldNames(String headerLine) {
        ArrayList<String> contactHeaders = getColumnHeaders(Contact.class);
        ArrayList<String> personHeaders = getColumnHeaders(Person.class);
        ArrayList<String> addressHeaders = getColumnHeaders(Address.class);

        ArrayList<String> headers = new ArrayList<>();
        headers.addAll(contactHeaders);
        headers.addAll(personHeaders);
        headers.addAll(addressHeaders);

        Map<String, Integer> headerIndexByFieldName = new HashMap<>();
        for (String header : headers) {
            int headerIndex = findHeaderIndex(headerLine, header);
            headerIndexByFieldName.put(header, headerIndex);
        }
        return headerIndexByFieldName;
    }
}
