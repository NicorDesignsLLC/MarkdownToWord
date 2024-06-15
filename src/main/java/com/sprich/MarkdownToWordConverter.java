package com.sprich;

import org.apache.poi.xwpf.usermodel.*;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MarkdownToWordConverter {
    public static void main(String[] args) {
        // Specify the path to the Markdown file relative to the resources directory
        String markdownFilePath = "markdownFile.md";  // Update with your file name

        // Read the content of the Markdown file
        String markdownContent = readFileContent(markdownFilePath);

        // Check if the content was successfully read
        if (markdownContent == null) {
            System.err.println("Failed to read the Markdown file.");
            return;
        }

        PegDownProcessor pegDownProcessor = new PegDownProcessor();
        RootNode rootNode = pegDownProcessor.parseMarkdown(markdownContent.toCharArray());

        XWPFDocument document = new XWPFDocument();

        for (Node node : rootNode.getChildren()) {
            processNode(node, document);
        }

        try (FileOutputStream out = new FileOutputStream("markdownToWord.docx")) {
            document.write(out);
            System.out.println("Word document created successfully: markdownToWord.docx");
        } catch (IOException e) {
            System.err.println("Failed to write the Word document.");
            e.printStackTrace();
        }
    }

    private static String readFileContent(String filePath) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                System.err.println("File not found: " + filePath);
                return null;
            }
            return new String(toByteArray(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    private static void processNode(Node node, XWPFDocument document) {
        if (node instanceof HeaderNode) {
            HeaderNode headerNode = (HeaderNode) node;
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(extractText(headerNode));
            run.setBold(true);
            run.setFontSize(16);
        } else if (node instanceof TextNode) {
            TextNode textNode = (TextNode) node;
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(textNode.getText());
        } else if (node instanceof TableNode) {
            TableNode tableNode = (TableNode) node;
            XWPFTable table = document.createTable();
            for (Node rowNode : tableNode.getChildren()) {
                if (rowNode instanceof TableRowNode) {
                    TableRowNode row = (TableRowNode) rowNode;
                    XWPFTableRow tableRow = table.createRow();
                    for (Node cellNode : row.getChildren()) {
                        if (cellNode instanceof TableCellNode) {
                            TableCellNode cell = (TableCellNode) cellNode;
                            XWPFTableCell tableCell = tableRow.createCell();
                            tableCell.setText(extractText(cell));
                        }
                    }
                }
            }
        } else {
            for (Node child : node.getChildren()) {
                processNode(child, document);
            }
        }
    }

    private static String extractText(Node node) {
        if (node instanceof TextNode) {
            return ((TextNode) node).getText();
        } else if (node instanceof SuperNode) {
            StringBuilder sb = new StringBuilder();
            for (Node child : ((SuperNode) node).getChildren()) {
                sb.append(extractText(child));
            }
            return sb.toString();
        }
        return "";
    }
}
