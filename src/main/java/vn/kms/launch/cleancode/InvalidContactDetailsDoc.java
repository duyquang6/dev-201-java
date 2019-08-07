package vn.kms.launch.cleancode;

@Report(reportName = "invalid-contact-details")
public class InvalidContactDetailsDoc {
    @Column(header = "contact_id")
    private int contactID;
    @Column(header = "error_field")
    private String errorField;
    @Column(header = "error_message")
    private String errorMessage;
}
