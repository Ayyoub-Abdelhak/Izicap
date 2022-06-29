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
    public Establishment getEstablishment(String siret) {
        Business business = restTemplate.getForObject(
                "https://entreprise.data.gouv.fr/api/sirene/v3/etablissements/{siret}",
                Business.class,
                siret
        );
        if(business != null) {
            return business.getEtablissement();
        }
        return null;
    }
}
