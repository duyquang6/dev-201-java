package vn.kms.launch.cleancode.utils;

import vn.kms.launch.cleancode.common.CommonVariables;
import vn.kms.launch.cleancode.entities.Contact;

import java.util.Map;

public class SortContactUtil extends CommonVariables{

    public static void sortContactById(Map<Integer, Map<String, String>> contactDetails) {
        for (int i = 0; i < allContacts.size(); i++) {
            for (int j = allContacts.size() - 1; j > i; j--) {
                Contact contactA = allContacts.get(i);
                Contact contactB = allContacts.get(j);
                if (!contactDetails.containsKey(contactA.getId()) && !contactDetails.containsKey(contactB.getId())) {
                    int IdA = contactA.getId();
                    int IdB = contactB.getId();
                    if (IdA > IdB) {
                        allContacts.set(i, contactB);
                        allContacts.set(j, contactA);
                    }
                }
            }
        }
    }

}
