package com.diplom.diplom.api;

import com.diplom.diplom.model.Geolocation;
import com.diplom.diplom.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Test {

//    @GetMapping
//    public Object getAll(@PageableDefault(sort = {"id"}, size = 20, direction = Sort.Direction.ASC) Pageable pageable) {
//        return null;
//    }

    @GetMapping
    public Object post() {
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitudes(1);
        return geolocation;
    }

    @PostMapping
    public Object get(@RequestBody Geolocation geolocation) {

        return geolocation;
    }

}
