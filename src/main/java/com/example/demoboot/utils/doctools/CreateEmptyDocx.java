package com.example.demoboot.utils.doctools;

import com.example.demoboot.utils.deletefile.DirectoryCleanerMaven;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateEmptyDocx {
    private static final Logger LOGGER = Logger.getLogger(DirectoryCleanerMaven.class.getName());

    public static void main(String[] args) {
        try (XWPFDocument document = new XWPFDocument()) {
            for (int i = 0; i < 500; i++) {
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();

                // 在每个段落后添加分页符
                if (i < 499) { // 避免在最后一页后添加分页符
                    run.addBreak(org.apache.poi.xwpf.usermodel.BreakType.PAGE);
                }
            }

            try (FileOutputStream out = new FileOutputStream("/Users/tianyalei/Desktop/Empty500PageDoc.docx")) {
                document.write(out);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to set up logger FileHandler", e);
        }
    }
}
