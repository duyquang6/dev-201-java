package vn.kms.launch.cleancode;

import java.util.Arrays;
import java.util.List;

public class Application {
    private static final List<Class> REPORT_TYPES = Arrays.asList(
            InvalidContactDetailsDoc.class,
            InvalidContactSummaryDoc.class,
            ContactPerStateDoc.class,
            ContactPerAgeGroupDoc.class
    );
    public static void main(String[] args) throws Exception {
//        getMethodsFromClass(Contact.class);
        AnalysisResult analysisResult = new AnalysisResult();
        analysisResult.setReportTypes(REPORT_TYPES);
        analysisResult.analyzeData("data/contacts.tsv");
        analysisResult.exportFile();
    }
}
