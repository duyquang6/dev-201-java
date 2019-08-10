package vn.kms.launch.cleancode.utils;

import vn.kms.launch.cleancode.Analysis;
import vn.kms.launch.cleancode.doctype.Document;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static vn.kms.launch.cleancode.utils.ExtractingDocumentInformation.getDocumentName;
import static vn.kms.launch.cleancode.utils.FileHandler.openFileWriter;
import static vn.kms.launch.cleancode.utils.FileHandler.openOrCreateFolder;

public class ExportingDocument {
    private static final Logger LOGGER = Logger.getLogger(ExportingDocument.class.getName());

    public static void exportFile(Analysis analysisResult) {
        for (Class reportType : analysisResult.getDocTypes()) {
            String reportName = getDocumentName(reportType);
            File outputFolder = openOrCreateFolder("output");

            try (Writer writer = openFileWriter(outputFolder, reportName, "tab")) {
                Document doc = (Document) reportType.getMethod("getInstance").invoke(null);
                doc.setAnalysisResult(analysisResult);
                doc.writeHeader(writer);
                doc.writeDoc(writer);
                writer.flush();
                LOGGER.log(Level.INFO, "Generated report output/{0}.tab", reportName);
            } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.log(Level.WARNING, "Got an exception. ", e);
            }
        }
    }
}
