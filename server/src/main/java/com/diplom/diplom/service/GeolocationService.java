package com.diplom.diplom.service;

import com.diplom.diplom.model.Geolocation;

import java.util.Optional;

public interface GeolocationService {

    Geolocation save(Geolocation geolocation);

    Optional getById(Long id);

}
