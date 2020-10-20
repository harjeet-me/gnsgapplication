package org.gnsg.gms.v1.helper;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author harjeet
 *
 */
/**
 * @author harjeet
 *
 */
@Service
public class CsvToPdfConverter {
    public static final Logger log = LoggerFactory.getLogger(CsvToPdfConverter.class);

    /**
     * @param bytes
     * @param reportObj
     * @return
     */
    public static byte[] csvToPdfConverter(byte[] bytes, ReportObj reportObj, Map<String, String> testMap) {
        String str = new String(bytes);

        String[] splitted = Arrays.stream(str.split("\n")).map(String::trim).toArray(String[]::new);
        try {
            List<String> list = Arrays.asList(splitted);
            final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            /** Base Text font */
            Font baseFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 0, 0));

            /** Base Paragraph Text font */
            Font paragraphFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));

            Document document = new Document(PageSize.A4, 2, 2, 2, 2);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, outStream);
            document.open();

            Paragraph toppHeading = new Paragraph(
                "GURU NANAK SIKH GURDWARA SURREY DELTA \n  7050 120TH ST SURREY BC V3W3M8 PH 604 598 1300 " +
                "\n  www.gnsg.org  Email : info@gnsg.ca ",
                paragraphFont
            );

            Paragraph heading = new Paragraph(
                reportObj.getReportType() + " From  " + reportObj.getStartDate() + " To    " + reportObj.getEndDate(),
                paragraphFont
            );

            toppHeading.setAlignment(Element.ALIGN_CENTER);

            heading.setAlignment(Element.ALIGN_CENTER);

            document.add(toppHeading);
            // document.add(heading);

            int headersize = 1;

            if (splitted.length > 1) {
                String[] line = Arrays.stream(splitted[0].split(",")).map(String::trim).toArray(String[]::new);
                headersize = line.length;
            }

            ArrayList<String> headerTable = new ArrayList<>();
            headerTable.add("INVOICE NO ,123455454");
            headerTable.add("MONTH,SEP");
            headerTable.add("NAME , REVENUE");
            headerTable.add("GENRATED ON ,22 AUG 2020 sdsd sd sd sd sd s ds d sd s d sd sd swewewewwrwrw");
            headerTable.add("GENERATED BY , HARJEET SINGH ");

            ArrayList<String> mapToTable = new ArrayList<>();

            testMap.entrySet().forEach(e -> mapToTable.add(e.getKey() + ", " + e.getValue()));

            ArrayList<PdfPTable> pdfPTables = new ArrayList<>();

            //  pdfPTables.add(generateTableFromList(headerTable, baseFont, 2, 20, true, Element.ALIGN_LEFT, null));

            //    pdfPTables.add(generateTableFromList(mapToTable, baseFont, 2, 20, true, Element.ALIGN_RIGHT,null));

            //   document.add(mergeMultiTable(pdfPTables, 100));

            document.add(generateTableFromList(mapToTable, baseFont, 2, 40, true, Element.ALIGN_RIGHT, null));

            // add table
            document.add(
                generateTableFromList(list, baseFont, headersize, 100, false, Element.ALIGN_JUSTIFIED, new int[] { 15, 10, 25, 35 })
            );

            Paragraph bottom = new Paragraph("*** END OF REPORT ***", baseFont);
            bottom.setAlignment(Element.ALIGN_CENTER);
            document.add(bottom);

            /*
             * // add table document.add(generateTableFromList(list,
             * baseFont,headersize,100,false , Element.ALIGN_JUSTIFIED));
             * document.add(generateTableFromList(headerTable, baseFont,2 ,40
             * ,true,Element.ALIGN_RIGHT));
             */
            document.close();
            pdfWriter.close();
            return outStream.toByteArray();
        } catch (DocumentException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * @param bytes
     * @param reportObj
     * @return
     */
    public static byte[] pathiReportcsvToPdfConverter(String json, String pathJson, ReportObj reportObj, Map<String, String> testMap) {
        String[] splitted = Arrays.stream(json.split("\n")).map(String::trim).toArray(String[]::new);

        String[] splitted1 = Arrays.stream(pathJson.split("\n")).map(String::trim).toArray(String[]::new);

        try {
            List<String> list = Arrays.asList(splitted);

            List<String> list1 = Arrays.asList(splitted1);
            final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            BaseFont bf = BaseFont.createFont("raavi.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            /** Base Text font */
            Font baseFont = new Font(bf, 12);

            /** Base Text font */
            Font basePunjabiFont = FontFactory.getFont(BaseFont.IDENTITY_H, 12, Font.NORMAL, new BaseColor(0, 0, 0));

            /** Base Paragraph Text font */
            Font paragraphFont = new Font(bf, 14, Font.BOLD);

            Document document = new Document(PageSize.A4, 2, 2, 2, 2);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, outStream);
            document.open();

            Paragraph toppHeading = new Paragraph(
                "GURU NANAK SIKH GURDWARA SURREY DELTA \n  7050 120TH ST SURREY BC V3W3M8 PH 604 598 1300 " +
                "\n  www.gnsg.org  Email : info@gnsg.ca ",
                paragraphFont
            );

            Paragraph heading = new Paragraph(
                reportObj.getReportType() + " From  " + reportObj.getStartDate() + " To    " + reportObj.getEndDate(),
                paragraphFont
            );

            toppHeading.setAlignment(Element.ALIGN_CENTER);

            heading.setAlignment(Element.ALIGN_CENTER);

            document.add(toppHeading);
            // document.add(heading);

            int headersize = 1;

            if (splitted.length > 1) {
                String[] line = Arrays.stream(splitted[0].split(",")).map(String::trim).toArray(String[]::new);
                headersize = line.length;
            }

            ArrayList<String> headerTable = new ArrayList<>();
            headerTable.add("INVOICE NO ,123455454");
            headerTable.add("MONTH,SEP");
            headerTable.add("NAME , REVENUE");
            headerTable.add("GENRATED ON ,22 AUG 2020 sdsd sd sd sd sd s ds d sd s d sd sd swewewewwrwrw");
            headerTable.add("GENERATED BY , HARJEET SINGH ");

            ArrayList<String> mapToTable = new ArrayList<>();

            testMap.entrySet().forEach(e -> mapToTable.add(e.getKey() + ", " + e.getValue()));

            ArrayList<PdfPTable> pdfPTables = new ArrayList<>();

            pdfPTables.add(generateTableFromList(headerTable, baseFont, 2, 20, true, Element.ALIGN_LEFT, null));

            pdfPTables.add(generateTableFromList(mapToTable, baseFont, 2, 20, true, Element.ALIGN_RIGHT, null));

            //   document.add(mergeMultiTable(pdfPTables, 100));

            document.add(generateTableFromList(mapToTable, baseFont, 2, 60, true, Element.ALIGN_RIGHT, null));

            // add table

            System.out.println(list1);

            document.add(
                generateTableFromList(list, baseFont, headersize, 100, false, Element.ALIGN_JUSTIFIED, new int[] { 15, 35, 15, 15, 35 })
            );

            Paragraph path_bottom = new Paragraph("--- ਇਸ ਰਿਪੋਰਟ ਵਿਚ ਚੁਣੇ ਗਏ ਪਾਠ  ---", new Font(bf, 22));
            path_bottom.setAlignment(Element.ALIGN_CENTER);
            document.add(path_bottom);

            document.add(generateTableFromList(list1, baseFont, 3, 100, true, Element.ALIGN_RIGHT, new int[] { 10, 70, 20 }));
            Paragraph bottom = new Paragraph("*** END OF REPORT ***", baseFont);
            bottom.setAlignment(Element.ALIGN_CENTER);
            document.add(bottom);

            /*
             * // add table document.add(generateTableFromList(list,
             * baseFont,headersize,100,false , Element.ALIGN_JUSTIFIED));
             * document.add(generateTableFromList(headerTable, baseFont,2 ,40
             * ,true,Element.ALIGN_RIGHT));
             */
            document.close();
            pdfWriter.close();
            return outStream.toByteArray();
        } catch (DocumentException e) {
            throw new IllegalStateException(e);
        } catch (IOException e1) {
            throw new IllegalStateException(e1);
        }
    }

    /* method used for generating table in pdf */

    static PdfPTable generateTableFromList(
        List<String> recordList,
        Font baseFont,
        int headersize,
        float tableSizePercentage,
        boolean isAllBorder,
        int allign,
        int[] tableWidth
    )
        throws DocumentException {
        PdfPTable t = new PdfPTable(headersize);
        t.setWidthPercentage(tableSizePercentage);
        if (tableWidth != null && tableWidth.length > 1) {
            log.debug("  ?????????????????????????????? +" + tableWidth.length);
            log.debug("  ????????????   recordList       ????????????????? +" + recordList);
            t.setWidths(tableWidth);
        }

        t.setHorizontalAlignment(allign);
        t.setSpacingBefore(10);
        t.setSpacingAfter(10);
        boolean isHeader = true;
        for (String record : recordList) {
            String[] line = Arrays.stream(record.split(",")).map(String::trim).toArray(String[]::new);
            List<String> feildlist = Arrays.asList(line);
            if (isHeader) {
                for (String header : feildlist) {
                    PdfPCell cell = new PdfPCell(new Phrase(header.toUpperCase(), baseFont));
                    t.addCell(cell);
                }
                isHeader = false;
            } else if (isAllBorder) {
                for (String header : feildlist) {
                    PdfPCell cell = new PdfPCell(new Phrase(header.toUpperCase(), baseFont));
                    t.addCell(cell);
                }
            } else {
                for (String f : feildlist) {
                    PdfPCell cell = new PdfPCell(new Phrase(f, baseFont));
                    /*
                     * cell.disableBorderSide(PdfPCell.LEFT); cell.disableBorderSide(PdfPCell.TOP);
                     * cell.disableBorderSide(PdfPCell.BOTTOM);
                     * cell.disableBorderSide(PdfPCell.RIGHT);
                     */
                    t.addCell(cell);
                }
            }
        }
        return t;
    }

    /** method used for generating table in pdf */

    static PdfPTable mergeMultiTable(List<PdfPTable> pdfPTables, float tableSizePercentage) throws DocumentException {
        PdfPTable t = new PdfPTable(pdfPTables.size());
        t.setWidths(new int[] { 50, 50 });
        t.setWidthPercentage(tableSizePercentage);

        //t.setHorizontalAlignment(allign);
        t.setSpacingBefore(10);
        t.setSpacingAfter(10);
        for (PdfPTable record : pdfPTables) {
            PdfPCell cell = new PdfPCell(record);
            cell.setPaddingLeft(40);
            cell.disableBorderSide(PdfPCell.LEFT);
            cell.disableBorderSide(PdfPCell.TOP);
            cell.disableBorderSide(PdfPCell.BOTTOM);
            cell.disableBorderSide(PdfPCell.RIGHT);
            t.addCell(cell);
        }

        return t;
    }

    /** Convert map to Arraylist String */

    static ArrayList<String> mapToArrayList(HashMap<String, String> testMap) {
        ArrayList<String> mapToTable = new ArrayList<>();
        testMap.entrySet().forEach(e -> mapToTable.add(e.getKey() + ", " + e.getValue()));
        return mapToTable;
    }
}
