package com.example.izicap.controllers;

import com.example.izicap.models.Establishment;
import com.example.izicap.services.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @GetMapping
    public ResponseEntity<List<Establishment>> getData() {
        return ResponseEntity.ok().body(businessService.getData());
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Establishment> establishments = businessService.getData();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Id", "Nic", "Full Address", "Creation date", "Full name", "TVA number"};
        String[] nameMapping = {"id", "nic", "geo_adresse", "date_creation", "denomination", "numero_tva_intra"};

        csvWriter.writeHeader(csvHeader);

        for(Establishment establishment : establishments) {
            csvWriter.write(establishment, nameMapping);
        }

        csvWriter.close();
    }
}
