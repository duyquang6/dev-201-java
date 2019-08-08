package vn.kms.launch.cleancode.doctype;

import vn.kms.launch.cleancode.Analysis;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static vn.kms.launch.cleancode.Util.*;
import static vn.kms.launch.cleancode.Util.openFileWriter;

public abstract class Document {
    private Analysis analysisResult;
    public abstract void writeDoc(Writer writer) throws IOException;

    public void setAnalysisResult(Analysis analysisResult) {
        this.analysisResult = analysisResult;
    }

    public void writeHeader(Writer writer) throws IOException {
        writer.write(columnHeadersToString(this.getClass()));
    }

    public void exportFile() {
        String fileName = getReportName(this.getClass());
        File outputFolder = openOrCreateFolder("output");
        try (Writer writer = openFileWriter(outputFolder, fileName, "tab")) {
            writeHeader(writer);
            writeDoc(writer);
            writer.flush();
            System.out.println("Generated report " + "output/" + fileName + ".tab");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Analysis getAnalysisResult() {
        return analysisResult;
    }
}
