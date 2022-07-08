package com.example.izicap;

import com.example.izicap.models.Business;
import com.example.izicap.services.BusinessService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest
public class BusinessTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    BusinessService businessService;

    @Test
    public void testBusinessService() {
        assertEquals(businessService.getEstablishment("52170053400014").getId(), 1592335243);
    }

    @Test
    public void testRestTemplate() {
        Business business = testRestTemplate.getForObject(
                "https://entreprise.data.gouv.fr/api/sirene/v3/etablissements/{siret}",
                Business.class,
                "52170053400014"
        );
        assertEquals(business.getEtablissement().getId(), 1592335243);
    }
}
