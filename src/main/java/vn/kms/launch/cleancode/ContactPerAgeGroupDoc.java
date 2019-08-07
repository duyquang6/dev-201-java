package vn.kms.launch.cleancode;

@Report(reportName = "contact-per-age-group")
public class ContactPerAgeGroupDoc implements Doc {
    @Column(header = "group")
    private String group;
    @Column(header = "number_of_contact")
    private int numberOfContact;
    @Column(header = "percentage_of_contact")
    private String percentageOfContact;
}
