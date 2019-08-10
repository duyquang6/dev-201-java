// Copyright (c) 2019 KMS Technology, Inc.
package vn.kms.launch.cleancode;

import vn.kms.launch.cleancode.doctype.*;

import java.util.Arrays;
import java.util.List;

import static vn.kms.launch.cleancode.utils.ExportingDocument.exportFile;

/**
 * A demo application for analyzing,
 * validating contacts data and exporting result documents
 *
 * @author trungnguyen
 */

public class Application {
    private static final List<Class> DOCUMENT_TYPES = Arrays.asList(
            InvalidContactDetails.class,
            InvalidContactSummary.class,
            ContactPerState.class,
            ContactPerAgeGroup.class,
            ValidContacts.class
    );

    public static void main(String[] args) throws Exception {
        Analysis analysis = new Analysis();
        analysis.setDocTypes(DOCUMENT_TYPES);
        analysis.analyzeData("data/contacts.tsv");
        exportFile(analysis);
    }
}
