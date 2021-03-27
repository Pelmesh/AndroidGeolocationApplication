package com.diplom.diplom.api;

import com.diplom.diplom.model.Geolocation;
import com.diplom.diplom.service.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private GeolocationService geolocationService;

    @PostMapping()
    public Object saveLocation(@RequestBody Geolocation geolocation) {
        geolocation.setDate(new Date());
        System.out.println(geolocation.toString());
        geolocationService.save(geolocation);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping()
    public Object getLocation(@RequestBody Geolocation geolocation) {
        return geolocationService.getById(geolocation.getId());
    }

}
