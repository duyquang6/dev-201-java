package vn.kms.launch.cleancode.utils;

import vn.kms.launch.cleancode.common.CommonVariables;
import vn.kms.launch.cleancode.entities.Contact;

import java.util.Map;

public class GenerateReportUtil extends CommonVariables{
    private static CalculateAgeUtil calculateAgeUtil = new CalculateAgeUtil();

    public static Map generateContactPerStateReport(Map<Integer, Map<String, String>> contactDetails) {
        for (Contact contact : allContacts) {
            if (!contactDetails.containsKey(contact.getId())) {
                int stateCount = 0;
                if (contactPerStates.containsKey(contact.getState())) {
                    stateCount = contactPerStates.get(contact.getState());
                }
                contactPerStates.put(contact.getState(), stateCount + 1);

                int ageGroupCount = 0;
                if (contactPerAgeGroups.containsKey(calculateAgeUtil.calculateAgeGroup(contact.getAge()))) {
                    ageGroupCount = contactPerAgeGroups.get(calculateAgeUtil.calculateAgeGroup(contact.getAge()));
                }
                contactPerAgeGroups.put(calculateAgeUtil.calculateAgeGroup(contact.getAge()), ageGroupCount + 1);
            }
        }
        contactReports.put("contact-per-state", contactPerStates);
        contactReports.put("contact-per-age-group", contactPerAgeGroups);
        return contactReports;
    }

}
