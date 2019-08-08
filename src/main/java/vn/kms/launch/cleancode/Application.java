package vn.kms.launch.cleancode;

import vn.kms.launch.cleancode.doctype.*;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static vn.kms.launch.cleancode.Util.*;

public class Application {
    private static final List<Class> REPORT_TYPES = Arrays.asList(
            InvalidContactDetails.class,
            InvalidContactSummary.class,
            ContactPerState.class,
            ContactPerAgeGroup.class
    );

    public static void main(String[] args) throws Exception {
        Analysis analysis = new Analysis();
        analysis.setReportTypes(REPORT_TYPES);
        analysis.analyzeData("data/contacts.tsv");
        exportFile(analysis);
    }
}
