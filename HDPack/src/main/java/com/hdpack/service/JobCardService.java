package com.hdpack.service;

import com.hdpack.client.models.JobViewModel;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

public class JobCardService {

    private static final Font FONT_HEADER = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.ORANGE);
    private static final Font FONT_SUBHEADER = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
    private static final Font FONT_WHITE_BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
    private static final Font FONT_LABEL = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK);
    private static final Font FONT_VALUE = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
    private static final Font FONT_RED_BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.RED);

    public void generateJobCard(JobViewModel job, String dest) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        // 1. HEADER (Logo & Company Info)
        addHeader(document);

        // 2. BLUE TITLE BAR (Die No, Job Card, Date)
        addBlueTitleBar(document, job);

        // 3. JOB DETAILS GRID (Size, Material, Teeth, etc.)
        addJobDetailsTable(document, job);

        // 4. VISUAL DIAGRAM (The big rectangle with dimensions)
        addVisualDiagram(document, job, writer);

        // 5. FOOTER (Technical Details: Varnish, Colors, etc.)
        addFooterDetails(document, job);

        document.close();
        System.out.println("Job Card generated successfully at: " + dest);
    }

    private void addHeader(Document doc) throws DocumentException {
        // Use a Table for layout to keep Logo left and Text right
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{1, 4}); // Logo takes 1 part, Text takes 4 parts

        // Logo Placeholder (Replace with actual Image loading if you have the file)
        PdfPCell logoCell = new PdfPCell(new Phrase("HD LOGO", FONT_HEADER));
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerTable.addCell(logoCell);

        // Company Text
        Paragraph p = new Paragraph("HD PACK SOLUTIONS PVT. LTD.", FONT_HEADER);
        p.setAlignment(Element.ALIGN_CENTER);

        Paragraph sub = new Paragraph("AN ISO 9001:2015 CERTIFIED COMPANY\n" +
                "Factory: Shop No 5, Hasti Industry, Mahape, Navi Mumbai - 400710\n" +
                "+91 81080 48686 | hdpacksolutions@gmail.com", FONT_SUBHEADER);
        sub.setAlignment(Element.ALIGN_CENTER);

        PdfPCell textCell = new PdfPCell();
        textCell.addElement(p);
        textCell.addElement(sub);
        textCell.setBorder(Rectangle.NO_BORDER);
        textCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTable.addCell(textCell);

        doc.add(headerTable);
        doc.add(new Paragraph("\n")); // Spacer
    }

    private void addBlueTitleBar(Document doc, JobViewModel job) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 2, 1}); // Middle is wider

        // Cell 1: Die No
        PdfPCell cell1 = createColoredCell("Die No. 127", new Color(57, 108, 179));
        // Cell 2: JOB CARD (Centered)
        PdfPCell cell2 = createColoredCell("JOB CARD", new Color(57, 108, 179));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        // Cell 3: Job Date
        PdfPCell cell3 = createColoredCell("REP JOB " + job.getStartDate(), new Color(57, 108, 179));
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        doc.add(table);
    }

    private void addJobDetailsTable(Document doc, JobViewModel job) throws DocumentException {
        PdfPTable table = new PdfPTable(4); // 4 Columns
        table.setWidthPercentage(100);
        table.setSpacingBefore(5);

        // Row 1
        addLabelVal(table, "Job Name :", job.getBrand(), 2);
        addLabelVal(table, "Date :", job.getDeadline().toString(), 2);

        // Row 2
        addLabelVal(table, "Size :", "200x100 mm", 2); // Map this from your Job entity
        addLabelVal(table, "Teeth :", "67 (PLATE 1.70)", 2);

        // Row 3
        addLabelVal(table, "Material :", "PP White", 1);
        // Special RED text for size gap
        PdfPCell sizeGap = new PdfPCell(new Phrase("size : 109 MM", FONT_RED_BOLD));
        sizeGap.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(sizeGap);

        addLabelVal(table, "Around :", "1 (GAP 12.7 mm)", 2);

        // Row 4
        addLabelVal(table, "Adhesive :", "Hotmelt", 2);
        addLabelVal(table, "Across :", "1 (GAP 3.00 mm)", 2);

        doc.add(table);
    }

    private void addVisualDiagram(Document doc, JobViewModel job, PdfWriter writer) throws DocumentException {
        doc.add(new Paragraph("\n")); // Space

        // We use a simplified representation: A Table with a fixed height cell
        PdfPTable diagramTable = new PdfPTable(1);
        diagramTable.setWidthPercentage(100);

        PdfPCell canvasCell = new PdfPCell();
        canvasCell.setFixedHeight(200f); // Height of the sticker visual
        canvasCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        canvasCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        // Add the Job Name inside the box (simulating the artwork)
        Paragraph artText = new Paragraph(job.getBrand().toUpperCase() + "\n\n(Artwork Visual)",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLACK));
        artText.setAlignment(Element.ALIGN_CENTER);
        canvasCell.addElement(artText);

        diagramTable.addCell(canvasCell);
        doc.add(diagramTable);

        // Note: For actual dimension arrows (blue lines), we would use `writer.getDirectContent()`
        // to draw lines at exact coordinates, but that is very complex for a basic implementation.
        // The table border acts as the "Sticker Outline" for now.

        Paragraph p = new Paragraph("HDPS 00480 " + job.getBrand() + " Sticker Sep",
                FontFactory.getFont(FontFactory.HELVETICA, 14, Color.BLACK));
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingBefore(5);
        doc.add(p);
    }

    private void addFooterDetails(Document doc, JobViewModel job) throws DocumentException {
        // Main Footer Table with 3 columns: Web Direction | Colors/Varnish | Print/Punch Details
        PdfPTable footer = new PdfPTable(3);
        footer.setWidthPercentage(100);
        footer.setWidths(new float[]{1.5f, 3.5f, 2f});
        footer.setSpacingBefore(10);

        // 1. Web Direction Column
        PdfPCell webDirCell = new PdfPCell();
        webDirCell.addElement(new Paragraph("WEB DIRECTION", FONT_WHITE_BOLD));
        webDirCell.setBackgroundColor(Color.GRAY);
        // (In a real app, you'd insert images of the rolls here)
        webDirCell.addElement(new Paragraph("\n[Image: Roll #3]\n"));
        footer.addCell(webDirCell);

        // 2. Colors & Varnish Column
        PdfPCell colorCell = new PdfPCell();
        colorCell.addElement(new Paragraph("ARTWORK COLOUR DETAILS", FONT_WHITE_BOLD));
        colorCell.setBackgroundColor(Color.GRAY);
        // Add simple checkboxes using text
        colorCell.addElement(new Paragraph(" [X] Cyan  [X] Magenta  [ ] Yellow  [X] Black", FONT_VALUE));

        colorCell.addElement(new Paragraph("VARNISH DETAILS", FONT_WHITE_BOLD));
        // Checkboxes
        colorCell.addElement(new Paragraph("[ ] Full  [ ] Window  [X] Non  [ ] Gloss", FONT_VALUE));
        footer.addCell(colorCell);

        // 3. Printing/Punching Details Column
        PdfPCell detailsCell = new PdfPCell();

        // Inner table for alignment
        PdfPTable inner = new PdfPTable(2);
        inner.setWidthPercentage(100);

        inner.addCell(createFooterLabel("Quantity"));
        inner.addCell(createFooterValue(String.valueOf(job.getQuantity())));

        inner.addCell(createFooterLabel("Running Meters"));
        inner.addCell(createFooterValue("639 + 100"));

        inner.addCell(createFooterLabel("Core Size"));
        inner.addCell(createFooterValue("1 Inch"));

        inner.addCell(createFooterLabel("Qty per Roll"));
        inner.addCell(createFooterValue("1000"));

        detailsCell.addElement(inner);
        footer.addCell(detailsCell);

        doc.add(footer);
    }

    // --- Helper Methods ---

    private PdfPCell createColoredCell(String text, Color bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FONT_WHITE_BOLD));
        cell.setBackgroundColor(bgColor);
        cell.setPadding(5);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private void addLabelVal(PdfPTable table, String label, String value, int colSpan) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(colSpan);
        Phrase ph = new Phrase();
        ph.add(new Chunk(label + " ", FONT_LABEL));
        ph.add(new Chunk(value, FONT_VALUE));
        cell.addElement(ph);
        cell.setPadding(4);
        table.addCell(cell);
    }

    private PdfPCell createFooterLabel(String text) {
        PdfPCell c = new PdfPCell(new Phrase(text, FONT_SUBHEADER));
        c.setBorder(Rectangle.NO_BORDER);
        return c;
    }

    private PdfPCell createFooterValue(String text) {
        PdfPCell c = new PdfPCell(new Phrase(": " + text, FONT_SUBHEADER));
        c.setBorder(Rectangle.NO_BORDER);
        return c;
    }
}