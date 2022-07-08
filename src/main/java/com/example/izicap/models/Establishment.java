package com.example.izicap.models;

import lombok.Data;

import java.util.Date;

@Data
public class Establishment {
    private Long id;
    private String nic;
    private String geo_adresse;
    private Date date_creation;
    private LegalUnit unite_legale;
    private String denomination;
    private String numero_tva_intra;

    public String getNumero_tva_intra() {
        return unite_legale.getNumero_tva_intra();
    }

    public String getDenomination() {
        return unite_legale.getDenomination();
    }
}

