package com.diplom.diplom.api;

import com.diplom.diplom.model.Point;
import com.diplom.diplom.model.User;
import com.diplom.diplom.service.PointService;
import com.diplom.diplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public Object savePoint(@RequestBody Point point) {
        pointService.save(point);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/user/{id}")
    public Object getPointByUser(@PathVariable Long id) {
        User user = userService.findById(id).get();
        return pointService.getByUser(user);
    }

    @GetMapping("{id}")
    public Object getPointById(@PathVariable Long id) {
        return pointService.getById(id).get();
    }

    @DeleteMapping("{id}")
    public Object getPoint(@PathVariable Long id) {
        pointService.deletePoint(pointService.getById(id).get());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
