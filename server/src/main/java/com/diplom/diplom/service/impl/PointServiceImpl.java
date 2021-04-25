package com.diplom.diplom.service.impl;

import com.diplom.diplom.model.Point;
import com.diplom.diplom.model.User;
import com.diplom.diplom.repo.PointRepository;
import com.diplom.diplom.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    PointRepository pointRepository;

    @Override
    public Point save(Point point) {
        return pointRepository.save(point);
    }

    @Override
    public Optional<Point> getById(Long id) {
        return pointRepository.findById(id);
    }

    @Override
    public List<Point> getByUser(User user) {
        return pointRepository.getByUser(user);
    }

    @Override
    public void deletePoint(Point point) {
        pointRepository.delete(point);
    }

}
