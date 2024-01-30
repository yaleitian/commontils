package com.example.demoboot.utils.readfile;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import java.io.*;


public class FileUtils {

    /**
     * byte转MultipartFile
     *
     * @param fileByte
     * @return
     */
    public static File byteToMultiPartFile(byte[] fileByte, String fileName) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(fileByte);


        FileItemFactory fileItemFactory = new DiskFileItemFactory();
        FileItem item = fileItemFactory.createItem(fileName, "", true, fileName);

        try (OutputStream os = item.getOutputStream()) {
            org.apache.commons.io.IOUtils.copy(inputStream, os);
        } catch (Exception e) {
        }

        return new File(String.valueOf(item));
    }
    public static void main(String[] args) {
        try {
            // 创建一些示例数据
            String originalFileName = "testFile.txt";
            byte[] fileContent = "Hello, World!".getBytes();

            // 调用方法
            File resultFile = byteToMultiPartFile(fileContent, originalFileName);

            // 检查和打印结果
            if (resultFile.exists()) {
                System.out.println("文件创建成功");
                System.out.println("文件路径: " + resultFile.getAbsolutePath());
                System.out.println("文件大小: " + resultFile.length());
            } else {
                System.out.println("文件创建失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
