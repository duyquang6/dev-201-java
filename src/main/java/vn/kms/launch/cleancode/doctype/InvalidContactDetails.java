package vn.kms.launch.cleancode.doctype;

import vn.kms.launch.cleancode.annotation.Header;
import vn.kms.launch.cleancode.annotation.Report;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@Report(reportName = "invalid-contact-details")
public class InvalidContactDetails extends Document{
    private static InvalidContactDetails instance;

    @Header(value = "contact_id")
    private int contactID;
    @Header(value = "error_field")
    private String errorField;
    @Header(value = "error_message")
    private String errorMessage;

    private InvalidContactDetails() {
    }

    public static InvalidContactDetails getInstance() {
        if (instance == null)
            instance = new InvalidContactDetails();
        return instance;
    }

    @Override
    public void writeDoc(Writer writer) throws IOException {
        for (Map.Entry<Integer, Map<String, String>> invalidContactEntry : getAnalysisResult().getInvalidContacts().entrySet()) {
            int contactID = invalidContactEntry.getKey();
            Map<String, String> errorByFields = invalidContactEntry.getValue();
            for (Map.Entry<String, String> errorEntry : errorByFields.entrySet()) {
                String fieldName = errorEntry.getKey();
                writer.write(contactID + "\t" + fieldName + "\t" + errorByFields.get(fieldName) + "\r\n");
            }
        }
    }
}
