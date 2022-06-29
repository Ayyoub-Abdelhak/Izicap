package com.example.izicap.services;

import com.example.izicap.models.Establishment;

import java.util.List;

public interface BusinessService {
    Establishment getEstablishment(String siret);
}
