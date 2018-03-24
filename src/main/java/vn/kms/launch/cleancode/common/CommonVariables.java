package vn.kms.launch.cleancode.common;

import vn.kms.launch.cleancode.entities.Contact;
import vn.kms.launch.cleancode.utils.HandleFileUtils;
import vn.kms.launch.cleancode.utils.*;

import java.util.*;

public class CommonVariables {

    protected static final Set<String> VALID_STATE_CODES = new HashSet<>(Arrays.asList(
            "AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA",
            "KS", "KY", "LA", "ME", "MD", "MH", "MA", "MI", "FM", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM",
            "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA",
            "VI", "WA", "WV", "WI", "WY"
    ));
    protected static final Map<String, String> REPORT_HEADERS = new HashMap<String, String>() {{
        put("invalid-contact-details", "contact_id\terror_field\terror_message\r\n");
        put("invalid-contact-summary", "field_name\tnumber_of_invalid_contact\r\n");
        put("contact-per-state", "state_code\tnumber_of_contact\r\n");
        put("contact-per-age-group", "group\tnumber_of_contact\tpercentage_of_contact\r\n");
    }};

    protected static final String VALID_CONTACTS_REPORT_HEADERS = "id\tfirst_name\tlast_name\tday_of_birth\taddress\tcity\tstate\tzip_code\tmobile_phone\temail\r\n";

    protected static List<Contact> allContacts = new ArrayList<>();
    protected static char[] guestFileSizeToUseBuffer = new char[100000]; // guest file size is not greater than 100000 chars;
    protected static Map<String, Integer> fieldErrorCounts = new TreeMap<>(); // I want to sort by key
    protected static Map<Integer, Map<String, String>> contactDetails = new LinkedHashMap<>(); // invalidContacts order by ID
    protected static Map contactReports = new HashMap<>();
    protected static int countTotalCharacterInFile = 0;
    protected static Map reports;
    protected static HandleFileUtils handleFileUtils = new HandleFileUtils();
    protected static ConvertDataUtil convertDataUtil = new ConvertDataUtil();
    protected static ValidateContactUtil validateContactUtil = new ValidateContactUtil();
    protected static SortContactUtil sortContactUtil = new SortContactUtil();
    protected static GenerateReportUtil generateReportUtil = new GenerateReportUtil();
    protected static Map<String, Integer> contactPerStates = new HashMap<>();
    protected static Map<String, Integer> contactPerAgeGroups = new HashMap<>();
    protected static CalculateAgeUtil calculateAgeUtil = new CalculateAgeUtil();
}
