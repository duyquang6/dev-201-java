package vn.kms.launch.cleancode.doctype;

import vn.kms.launch.cleancode.annotation.Header;
import vn.kms.launch.cleancode.annotation.Report;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static vn.kms.launch.cleancode.Util.getReportName;

@Report(reportName = "contact-per-age-group")
public class ContactPerAgeGroup extends Document {
    private static ContactPerAgeGroup instance;
    @Header(value = "group")
    private String group;
    @Header(value = "number_of_contact")
    private int numberOfContact;
    @Header(value = "percentage_of_contact")
    private String percentageOfContact;

    private ContactPerAgeGroup() {
    }

    public static ContactPerAgeGroup getInstance() {
        if (instance == null)
            instance = new ContactPerAgeGroup();
        return instance;
    }

    @Override
    public void writeDoc(Writer writer) throws IOException {
        Map<String, Integer> report =
                (Map<String, Integer>) getAnalysisResult().getReports().get(getReportName(this.getClass()));
        int total = 0;
        for (Integer v : report.values()) {
            total += v;
        }
        // Sort by age-group followed the requirement
        String[] ageGroups = {"Children", "Adolescent", "Adult", "Middle Age", "Senior"};
        for (String item : ageGroups) {
            writer.write(item + "\t" + report.get(item) + "\t" + (int) ((report.get(item) * 100.0f) / total)
                    + "%\r\n");
        }
    }
}
