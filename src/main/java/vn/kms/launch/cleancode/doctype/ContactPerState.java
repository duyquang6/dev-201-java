package vn.kms.launch.cleancode.doctype;

import vn.kms.launch.cleancode.annotation.Document;
import vn.kms.launch.cleancode.annotation.Header;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;

import static vn.kms.launch.cleancode.utils.ExtractingDocumentInformation.getDocumentName;

@Document(reportName = "contact-per-state")
public class ContactPerState extends vn.kms.launch.cleancode.doctype.Document {
    private static ContactPerState instance;

    @Header(value = "state_code")
    private String stateCode;
    @Header(value = "number_of_contact")
    private String numberOfContact;

    private ContactPerState() {
    }

    public static ContactPerState getInstance() {
        if (instance == null)
            instance = new ContactPerState();
        return instance;
    }

    @Override
    public void writeDoc(Writer writer) throws IOException {
        Map<String, Integer> report =
                (Map<String, Integer>) getAnalysisResult().getReports().get(getDocumentName(this.getClass()));
        Map<String, Integer> reportSortByStateCode = new TreeMap<>(report);
        for (Map.Entry<String, Integer> entry : reportSortByStateCode.entrySet()) {
            String item = entry.getKey();
            writer.write(item + "\t" + report.get(item) + "\r\n");
        }
    }
}
