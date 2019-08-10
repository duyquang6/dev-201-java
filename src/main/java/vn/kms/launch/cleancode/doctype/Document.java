package vn.kms.launch.cleancode.doctype;

import vn.kms.launch.cleancode.Analysis;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static vn.kms.launch.cleancode.utils.ExtractingDocumentInformation.getDocumentName;
import static vn.kms.launch.cleancode.utils.FileHandler.openFileWriter;
import static vn.kms.launch.cleancode.utils.FileHandler.openOrCreateFolder;
import static vn.kms.launch.cleancode.utils.HeaderHandler.columnHeadersToString;

public abstract class Document {
    private static final Logger LOGGER = Logger.getLogger(Document.class.getName());
    private Analysis analysisResult;

    public abstract void writeDoc(Writer writer) throws IOException;

    public void writeHeader(Writer writer) throws IOException {
        writer.write(columnHeadersToString(this.getClass()));
    }

    public void exportFile() {
        String fileName = getDocumentName(this.getClass());
        File outputFolder = openOrCreateFolder("output");
        try (Writer writer = openFileWriter(outputFolder, fileName, "tab")) {
            writeHeader(writer);
            writeDoc(writer);
            writer.flush();
            LOGGER.log(Level.INFO, "Generated report output/{0}.tab", fileName);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Got an exception. ", e);
        }
    }

    public Analysis getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(Analysis analysisResult) {
        this.analysisResult = analysisResult;
    }
}
