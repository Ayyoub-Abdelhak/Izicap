package com.example.izicap.services;

import com.example.izicap.models.Business;
import com.example.izicap.models.Establishment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessServiceImpl implements BusinessService{
    private final RestTemplate restTemplate;

    @Override
    public List<Establishment> getData() {
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
                Business business = restTemplate.getForObject(
                        "https://entreprise.data.gouv.fr/api/sirene/v3/etablissements/{siret}",
                        Business.class,
                        siret
                );
                if(business != null) {
                    Establishment establishment = business.getEtablissement();
                    log.info("Establishment {}", establishment);
                    establishments.add(establishment);
                }
            }
            catch (final HttpClientErrorException e) {
                System.out.println(e.getStatusCode());
                System.out.println(e.getResponseBodyAsString());
            }

        }
        return establishments;
    }
}
