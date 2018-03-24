package vn.kms.launch.cleancode.utils;

import vn.kms.launch.cleancode.common.CommonVariables;
import vn.kms.launch.cleancode.entities.Contact;

public class ConvertDataUtil extends CommonVariables{

    public static void convertDataToEntity() {
        try {
            String s = new String(guestFileSizeToUseBuffer, 0, countTotalCharacterInFile); // all data from file load to string s
            String[] lines = s.split("\r"); // get all lines
            int blankCount = 0;
            int invalidLineCount = 0;

            for (int i = 0; i < lines.length; i++) {
                if (i == 0) { // ignore header line
                    continue;
                }

                // count blank line by trimming space characters and then check length
                if (lines[i].trim().length() == 0) {
                    blankCount++;
                    continue;
                }

                String[] data = lines[i].split("\t"); // get data of a line

                if (data.length != 14) { // invalid line format
                    invalidLineCount++;
                    continue;
                }

                // TODO: I will use reflection & annotations to build contact object later
                Contact contact = new Contact();

                try {
                    // first column is ID
                    contact.setId(Integer.parseInt(data[0]));
                } catch (Exception ex) {
                    // log error
                    System.out.println("It is not ID format");
                    invalidLineCount++;
                    continue;
                }

                // set values for contact's properties needed to show
                contact.setFirstName(data[1]);
                contact.setLastName(data[2]);
                contact.setDateOfBirth(data[4]);
                contact.setAddress(data[5]);
                contact.setCity(data[6]);
                contact.setState(data[8]);
                contact.setZipCode(data[9]);
                contact.setMobilePhone(data[10]);
                contact.setEmail(data[12]);

                if (contact != null) {
                    allContacts.add(contact);
                } else {
                    // for some reason, I think contact object may be null (I am not sure, but I want to log for sure!!!)
                    System.out.println("NameRequired is null, don't know why!!!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
