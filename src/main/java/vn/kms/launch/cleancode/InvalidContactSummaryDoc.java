package vn.kms.launch.cleancode;

@Report(reportName = "invalid-contact-summary")
public class InvalidContactSummaryDoc implements Doc {
    @Column(header = "field_name")
    private String fieldName;
    @Column(header = "number_of_invalid_contact")
    private String numberOfInvalidContact;
}