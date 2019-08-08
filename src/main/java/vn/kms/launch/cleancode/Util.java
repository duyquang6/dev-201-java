package vn.kms.launch.cleancode;

import vn.kms.launch.cleancode.annotation.Column;
import vn.kms.launch.cleancode.annotation.Header;
import vn.kms.launch.cleancode.annotation.Report;
import vn.kms.launch.cleancode.doctype.Document;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private Util() {

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

    static void exportFile(Analysis analysisResult) {
        for (Class reportType : analysisResult.getReportTypes()) {
            String reportName = getReportName( reportType );
            File outputFolder = openOrCreateFolder( "output" );

            try (Writer writer = openFileWriter( outputFolder, reportName, "tab" )) {
                Document doc = (Document) reportType.getMethod( "getInstance" ).invoke( null );
                doc.setAnalysisResult( analysisResult );
                doc.writeHeader( writer );
                doc.writeDoc( writer );
                writer.flush();
                Logger.getLogger( "Util" ).log( Level.INFO, String.format( "Generated report output/%s.tab", reportName ) );
            } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                Logger.getLogger( "Util" ).log( Level.WARNING, "Got an exception. ", e );
            }
        }
    }

    public static void addFieldERROR(Map<String, Integer> counts, String fieldName) {
        Integer count = counts.get( fieldName );
        if (count == null) {
            count = 0;
        }
        count = count + 1;
        counts.put( fieldName, count );
    }

    public static File openOrCreateFolder(String folderPath) {
        File outputFolder = new File( folderPath );
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        return outputFolder;
    }

    public static Writer openFileWriter(File outputFolder, String reportName, String extension) throws IOException {
        return new FileWriter( new File( outputFolder, reportName + "." + extension ) );
    }

    static ArrayList<String> getColumnHeaders(Class theClass) {
        ArrayList<String> columnHeaders = new ArrayList<>();
        for (Method method : theClass.getMethods()) {
            Column column = method.getAnnotation( Column.class );
            if (column != null)
                columnHeaders.add( column.header() );
        }
        return columnHeaders;
    }

    static ArrayList<String> getHeaders(Class theClass) {
        ArrayList<String> columnHeaders = new ArrayList<>();
        for (Field field : theClass.getDeclaredFields()) {
            Header header = field.getAnnotation( Header.class );
            if (header != null)
                columnHeaders.add( header.value() );
        }
        return columnHeaders;
    }

    public static String columnHeadersToString(Class theClass) {
        ArrayList<String> columnHeaders = getHeaders( theClass );
        StringBuilder stringResult = new StringBuilder();
        Iterator<String> iterator = columnHeaders.iterator();
        while (iterator.hasNext()) {
            String columnHeader = iterator.next();
            if (iterator.hasNext()) {
                stringResult.append( columnHeader + "\t" );
            } else {
                stringResult.append( columnHeader + "\r\n" );
            }
        }
        return stringResult.toString();
    }

    static String loadFileData(String dataPath) throws IOException {
        FileReader input = new FileReader( dataPath );
        BufferedReader reader = new BufferedReader( input );
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append( line );
            content.append( System.lineSeparator() );
        }
        return content.toString();
    }

    public static int tryGetIntWithDefault(String string, int defaultValue) {
        try {
            return Integer.parseInt( string );
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static String getReportName(Class theClass) {
        Annotation annotation = theClass.getAnnotation( Report.class );
        Report report = (Report) annotation;
        return report.reportName();
    }

    public static void mapDataToObject(String[] dataColumns, Map<String, Integer> headerIndexByFieldName, Object object) {
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            Column column = method.getAnnotation( Column.class );
            if (column != null) {
                try {
                    if (column.type().equals( "INTEGER" )) {
                        method.invoke( object, Integer.parseInt( dataColumns[headerIndexByFieldName.get( column.header() )] ) );
                    } else {
                        method.invoke( object, dataColumns[headerIndexByFieldName.get( column.header() )] );
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    Logger.getLogger( "Util" ).log( Level.WARNING, "Got an exception. ", e );
                }
            }
        }
    }
}
