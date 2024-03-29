package vn.kms.launch.cleancode.doctype;

import vn.kms.launch.cleancode.annotation.Document;
import vn.kms.launch.cleancode.annotation.Header;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@Document(reportName = "invalid-contact-summary")
public class InvalidContactSummary extends vn.kms.launch.cleancode.doctype.Document {
    private static InvalidContactSummary instance;
    @Header(value = "field_name")
    private String fieldName;
    @Header(value = "number_of_invalid_contact")
    private String numberOfInvalidContact;

    private InvalidContactSummary() {
    }

    public static InvalidContactSummary getInstance() {
        if (instance == null)
            instance = new InvalidContactSummary();
        return instance;
    }

    public void writeDoc(Writer writer) throws IOException {
        for (Map.Entry entry : getAnalysisResult().getFieldErrorCounts().entrySet()) {
            writer.write(entry.getKey() + "\t" + entry.getValue() + "\r\n");
        }
    }
}