package com.example.tourplanner.business.report;

import com.example.tourplanner.business.app.TourManager;
import com.example.tourplanner.business.app.TourManagerImpl;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Report {
    final static TourManager tourManager = TourManagerImpl.getInstance();

    public static void tourReport(Tour tour) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        List<TourLog> tourLogs = tourManager.getTourLogsOfTour(tour);

        int totalTimeSpentOnTour = tourLogs
                .stream()
                .mapToInt(TourLog::getTotalTime)
                .sum();
        double averageTimeSpentOnTour = tourLogs
                .stream()
                .mapToDouble(TourLog::getTotalTime)
                .average()
                .orElseThrow(IllegalStateException::new);
        double averageRating = tourLogs
                .stream()
                .mapToDouble(TourLog::getRating)
                .average()
                .orElseThrow(IllegalStateException::new);

        // Initialize PDF writer and PDF Document
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(tour.getTourName() + "-report.pdf"));

        // Initialize document
        Document document = new Document(pdfDocument, PageSize.A4);

        Text text = new Text("Tour Report for " + tour.getTourName() + " - " + "\n"
                + "Created on " + now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")));

        // Create a PdfFont
        PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont fontItalic = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
        PdfFont fontNormal = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

        // Add Paragraph
        document.add(new Paragraph(text).setFont(fontBold));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Starting Point: "+ tour.getFrom()).setFont(fontItalic));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Destination: " + tour.getTo()).setFont(fontItalic));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Transport Type: " + tour.getTransportType()).setFont(fontItalic));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Distance: " + tour.getDistance()).setFont(fontItalic));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Estimated Time: " + tour.getEstimatedTime()).setFont(fontItalic));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Tour Description: " + tour.getTourDescription()).setFont(fontItalic));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Total time spent on tour (minutes): " + totalTimeSpentOnTour).setFont(fontItalic));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Average time spent on tour (minutes): " + averageTimeSpentOnTour).setFont(fontItalic));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Average rating of tour (stars): " + averageRating).setFont(fontItalic));
        document.add(new Paragraph("\n"));


        document.add(new Paragraph("Tour Logs").setFont(fontBold));

        Table table = new Table(UnitValue.createPercentArray(6))
                .useAllAvailableWidth();

        table.addHeaderCell("ID").setFont(fontNormal);
        table.addHeaderCell("Date").setFont(fontNormal);
        table.addHeaderCell("Difficulty").setFont(fontNormal);
        table.addHeaderCell("Rating").setFont(fontNormal);
        table.addHeaderCell("Duration").setFont(fontNormal);
        table.addHeaderCell("Comment").setFont(fontNormal);

        for (TourLog tourLog : tourLogs) {
            table.addCell(new Paragraph(String.valueOf(tourLog.getTourLogId())).setFont(fontItalic));
            table.addCell(new Paragraph(String.valueOf(tourLog.getDate())).setFont(fontItalic));
            table.addCell(new Paragraph(tourLog.getDifficulty()).setFont(fontItalic));
            table.addCell(new Paragraph(String.valueOf(tourLog.getRating())).setFont(fontItalic));
            table.addCell(new Paragraph(String.valueOf(tourLog.getTotalTime())).setFont(fontItalic));
            table.addCell(new Paragraph(tourLog.getComment()).setFont(fontItalic));
        }

        System.out.println("test1");
        System.out.println(tour.getRouteInformationImageURL());
        System.out.println("tets2");

        document.add(table);
        document.close();


    }

    public static void reportSummaryStats(List<Tour> tours) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        List<TourLog> tourLogs = tourManager.getTourLogs();

        int totalTimeSpentOnTour = tourLogs
                .stream()
                .mapToInt(TourLog::getTotalTime)
                .sum();
        double averageTimeSpentOnTour = tourLogs
                .stream()
                .mapToDouble(TourLog::getTotalTime)
                .average()
                .orElseThrow(IllegalStateException::new);
        double averageRating = tourLogs
                .stream()
                .mapToDouble(TourLog::getRating)
                .average()
                .orElseThrow(IllegalStateException::new);

        // Initialize PDF writer and PDF Document
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter( "tour-summary-report.pdf"));

        // Initialize document
        Document document = new Document(pdfDocument, PageSize.A4);

        Text text = new Text("Tour Summary Report" + " - " + "\n"
                + "Created on " + now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")));

        // Create a PdfFont
        PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont fontItalic = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);


        // Add Paragraph
        document.add(new Paragraph(text).setFont(fontBold));

        document.add(new Paragraph("Total Time spent on all Tours (minutes) -> " + totalTimeSpentOnTour).setFont(fontItalic));
        document.add(new Paragraph("Average Time spent per Tour (minutes) -> " + averageTimeSpentOnTour).setFont(fontItalic));
        document.add(new Paragraph("Average rating of all Tours (stars) -> " + averageRating).setFont(fontItalic));

        document.close();
    }


}
