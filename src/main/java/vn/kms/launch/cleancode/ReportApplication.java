package vn.kms.launch.cleancode;

import vn.kms.launch.cleancode.common.CommonVariables;
import java.util.Map;

public class ReportApplication extends CommonVariables {

    public static void main(String[] args) {
        reports = makeReports(fieldErrorCounts, contactDetails);
        handleFileUtils.storeReportsToFile();
    }

    private static Map makeReports(Map<String, Integer> counts, Map<Integer, Map<String, String>> contactDetails) {
        Map<String, Integer> generateReport;

        handleFileUtils.loadDataFromFile();
        convertDataUtil.convertDataToEntity();
        validateContactUtil.validateContact(counts, contactDetails);// TODO: refection & annotations to isValid contact data -> undone.
        sortContactUtil.sortContactById(contactDetails);//TODO: changed to sort by id -> done.
        handleFileUtils.storeValidContactsReportToFile();
        generateReport = (Map<String, Integer>) generateReportUtil.generateContactPerStateReport(contactDetails);

        return generateReport;
    }

}
