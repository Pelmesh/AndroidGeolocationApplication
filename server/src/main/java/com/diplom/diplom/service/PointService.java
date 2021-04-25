package com.diplom.diplom.service;

import com.diplom.diplom.model.Point;
import com.diplom.diplom.model.User;

import java.util.List;
import java.util.Optional;

public interface PointService {

    Point save(Point point);

    Optional<Point> getById(Long id);

    List<Point> getByUser(User user);

    void deletePoint(Point point);

}
