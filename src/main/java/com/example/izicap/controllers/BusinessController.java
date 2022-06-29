package com.example.izicap.controllers;

import com.example.izicap.models.Establishment;
import com.example.izicap.services.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @GetMapping
    public ResponseEntity<List<Establishment>> getData() {
        List<String> siretList = new ArrayList<>();
        List<Establishment> establishments = new ArrayList<>();

        siretList.add("31302979500017");
        siretList.add("41339442000033");
        siretList.add("41339442000090");
        siretList.add("41339442000116");
        siretList.add("41776304200013");
        siretList.add("43438147100011");
        siretList.add("45251723800013");
        siretList.add("52170053400014");
        siretList.add("75254783600011");
        siretList.add("47962817400042");
        siretList.add("97080195700014");

        for(String siret: siretList) {
            try {
                Establishment establishment = businessService.getEstablishment(siret);
                if(establishment != null) {
                    establishments.add(establishment);
                }
            }
            catch (final HttpClientErrorException e) {
                System.out.println(e.getStatusCode());
                System.out.println(e.getResponseBodyAsString());
            }

        }
        return ResponseEntity.ok().body(establishments);
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=establishments_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<String> siretList = new ArrayList<>();

        siretList.add("31302979500017");
        siretList.add("41339442000033");
        siretList.add("41339442000090");
        siretList.add("41339442000116");
        siretList.add("41776304200013");
        siretList.add("43438147100011");
        siretList.add("45251723800013");
        siretList.add("52170053400014");
        siretList.add("75254783600011");
        siretList.add("47962817400042");
        siretList.add("97080195700014");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Id", "Nic", "Full Address", "Creation date", "Full name", "TVA number"};
        String[] nameMapping = {"id", "nic", "geo_adresse", "date_creation", "denomination", "numero_tva_intra"};

        csvWriter.writeHeader(csvHeader);

        for(String siret: siretList) {
            try {
                Establishment establishment = businessService.getEstablishment(siret);
                if(establishment != null) {
                    csvWriter.write(establishment, nameMapping);
                }
            }
            catch (final HttpClientErrorException e) {
                System.out.println(e.getStatusCode());
                System.out.println(e.getResponseBodyAsString());
            }
        }

        csvWriter.close();
    }
}
