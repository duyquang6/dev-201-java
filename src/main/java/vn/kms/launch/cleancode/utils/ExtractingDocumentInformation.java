package vn.kms.launch.cleancode.utils;

import vn.kms.launch.cleancode.annotation.Document;

import java.lang.annotation.Annotation;

public class ExtractingDocumentInformation {
    public static String getDocumentName(Class theClass) {
        Annotation annotation = theClass.getAnnotation(Document.class);
        Document document = (Document) annotation;
        return document.reportName();
    }
}
