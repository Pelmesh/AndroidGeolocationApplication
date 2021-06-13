package com.diplom.diplom.api;

import com.diplom.diplom.model.Geolocation;
import com.diplom.diplom.model.User;
import com.diplom.diplom.repo.GeolocationRepository;
import com.diplom.diplom.repo.UserRepository;
import com.diplom.diplom.service.GeolocationService;

import com.diplom.diplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private GeolocationService geolocationService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public Object saveLocation(@RequestBody Geolocation geolocation) {
        java.util.Date date = new java.util.Date();
        geolocation.setDate(new java.sql.Date(date.getTime()));
        geolocation.setTime(new Time(date.getTime()));
        geolocationService.save(geolocation);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping()
    public Object getLocation(@RequestBody Geolocation geolocation) {
        return geolocationService.getById(geolocation.getId());
    }

    @GetMapping("history/{id}")
    public Object getHistory(@PathVariable Long id) {
        return  geolocationService.getDaysHistory(userService.findById(id).get());
    }

    @GetMapping("history/{id}/{date}")
    public Object getDayHistory(@PathVariable Long id, @PathVariable Date date) {
        return  geolocationService
                .findByUserAndDate(
                        userService.findById(id)
                                .get(),date,
                        Sort.by(Sort.Direction.ASC,
                                "time"));
    }
}