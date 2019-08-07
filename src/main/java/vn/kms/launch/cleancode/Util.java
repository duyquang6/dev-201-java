package vn.kms.launch.cleancode;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Util {
    private static int yearOfReport = 2016;
    public static int calculateAgeByYear(String dateOfBirth) {
        String yearStr = dateOfBirth.split("/")[2];
        int year = Integer.parseInt(yearStr);
        return yearOfReport - year;
    }

    // TODO: Calculate age exactly by month/day/year.
    // Chưa hiểu đề
    public static int preciseCalculateAge(String dateOfBirth) {
        return 10;
    }

    static String convertAgeToGroup(int age) {
        if (age <= 9) {
            return "Children";
        } else if (age <= 19) {
            return "Adolescent";
        } else if (age <= 45) {
            return "Adult";
        } else if (age <= 60) {
            return "Middle Age";
        } else {
            return "Senior";
        }
    }

    static void addFieldERROR(Map<String, Integer> counts, String fieldName) {
        Integer count = counts.get(fieldName);
        if (count == null) {
            count = 0;
        }
        count = count + 1;
        counts.put(fieldName, count);
    }

    static File openOrCreateFolder(String folderPath) {
        File outputFolder = new File(folderPath);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        return outputFolder;
    }

    static Writer openFileWriter(File outputFolder, String reportName, String extension) throws IOException {
        return new FileWriter(new File(outputFolder, reportName + "." + extension));
    }

    static String getHeaderFromReportName(Map<String,String> reportHeaders, String reportName) {
        return reportHeaders.get(reportName);
    }

//    static ArrayList<String> getHeadersFromReportType(Class reportType) {
//
//    }

    static void writeHeader(Writer writer,Class theClass) throws IOException {
        writer.write(columnHeadersToString(theClass));
    }

    static void writeContactPerAgeGroupDoc(Writer writer,Map<String, Integer> report) throws IOException {
        int total = 0;
        for (Integer v : report.values()) {
            total += v;
        }
        // I want to sort by age-group followed the requirement
        String[] ageGroups = { "Children", "Adolescent", "Adult", "Middle Age", "Senior" };
        for (String item : ageGroups) {
            writer.write(item + "\t" + report.get(item) + "\t" + (int) ((report.get(item) * 100.0f) / total)
                    + "%\r\n");
        }
    }

    static void writeContactPerStateDoc(Writer writer,Map<String, Integer> report) throws IOException {
        report = new TreeMap<>(report); // I want to sort by state
        for (String item : report.keySet()) {
            writer.write(item + "\t" + report.get(item) + "\r\n");
        }
    }

    static void writeInvalidContactSummaryDoc(Writer writer,Map<String, Integer> fieldErrorCounts) throws IOException {
        for (Map.Entry entry : fieldErrorCounts.entrySet()) {
            writer.write(entry.getKey() + "\t" + entry.getValue() + "\r\n");
        }
    }

    static void writeInvalidContactDetailsDoc(Writer writer,Map<Integer, Map<String, String>> invalidContacts) throws IOException {
        for (Map.Entry<Integer, Map<String, String>> entry : invalidContacts.entrySet()) {
            int contactID = entry.getKey();
            Map<String, String> errorByFields = entry.getValue();
            for (String field_name : errorByFields.keySet()) {
                writer.write(contactID + "\t" + field_name + "\t" + errorByFields.get(field_name) + "\r\n");
            }
        }
    }

    static void writeDocByReportName(Writer writer,String reportName, AnalysisResult analysisResult) throws IOException {
        Map<String, Integer> report = (Map<String, Integer>) analysisResult.getReports().get(reportName);
        switch (reportName) {
            case "contact-per-age-group":
                writeContactPerAgeGroupDoc(writer, report);
                break;
            case "contact-per-state":
                writeContactPerStateDoc(writer, report);
                break;
            case "invalid-contact-summary":
                writeInvalidContactSummaryDoc(writer, analysisResult.getFieldErrorCounts());
                break;
            case "invalid-contact-details":
                writeInvalidContactDetailsDoc(writer, analysisResult.getInvalidContacts());
                break;
            default:
                break;
        }
    }

    static Map<String, String> getFieldsByHeaders(Class theClass) {
         Map<String, String> fieldsByHeaders = new HashMap<>();
        for (Field field: theClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null)
                fieldsByHeaders.put(field.getName(),column.header());
        }
        return fieldsByHeaders;
    }

    static String columnHeadersToString(Class theClass) {
        Map<String,String> columnHeaders = getFieldsByHeaders(theClass);
        StringBuilder stringResult = new StringBuilder();
        Iterator<String> iterator = columnHeaders.values().iterator();
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

    static String loadFileDataToString(String dataPath) throws IOException {
        // 1. Load data from file
        try (InputStream input = new FileInputStream(dataPath)) {
            char[] buff = new char[100000]; // guest file size is not greater than 100000 chars
            int character;
            int countTotalCharInFile = 0; // count total characters in file
            while ((character = input.read()) != -1) {
                buff[countTotalCharInFile] = (char) character;
                countTotalCharInFile++;
            }
            // all data from file load to string s
            return new String(buff, 0, countTotalCharInFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    static String getReportName(Class theClass) {
        Annotation annotation = theClass.getAnnotation(Report.class);
        Report report = (Report) annotation;
        return report.reportName();
    }

    static void callSetMethods(Object object, String[] dataColumns, Map<String, Integer> headerIndexByFieldName) {
            Method[] methods = object.getClass().getMethods();
            for (Method method : methods) {
                Column column = method.getAnnotation(Column.class);
                if (column != null) {
                    try {
                        if (column.header().equals("id")) {
                            method.invoke(object, Integer.parseInt(dataColumns[headerIndexByFieldName.get(column.header())]));
                        } else {
                            method.invoke(object, dataColumns[headerIndexByFieldName.get(column.header())]);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}
