package com.diplom.diplom.service.impl;

import com.diplom.diplom.model.Geolocation;
import com.diplom.diplom.repo.GeolocationRepository;
import com.diplom.diplom.service.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeolocationServiceImpl implements GeolocationService {

    @Autowired
    GeolocationRepository geolocationRepository;

    @Override
    public Geolocation save(Geolocation geolocation) {
        return geolocationRepository.save(geolocation);
    }

    @Override
    public Optional<Geolocation> getById(Long id) {
        return geolocationRepository.findById(id);
    }
}
