package vn.kms.launch.cleancode;

@Report(reportName = "contact-per-state")
public class ContactPerStateDoc {
    @Column(header = "state_code")
    private String stateCode;
    @Column(header = "number_of_contact")
    private String numberOfContact;
}
