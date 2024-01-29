package com.example.demoboot.utils.pdf;

import com.example.demoboot.utils.deletefile.DirectoryCleanerMaven;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateEmptyPDF {
    private static final Logger LOGGER = Logger.getLogger(DirectoryCleanerMaven.class.getName());

    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            for (int i = 0; i < 500; i++) {
                document.addPage(new PDPage());
            }
            document.save("/Users/tianyalei/Desktop/Empty500PageDocument.pdf");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to set up logger FileHandler", e);
        }
    }
}
