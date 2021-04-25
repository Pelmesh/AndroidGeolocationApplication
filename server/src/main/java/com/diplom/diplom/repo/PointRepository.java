package com.diplom.diplom.repo;

import com.diplom.diplom.model.Point;
import com.diplom.diplom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    void delete(Point point);

    List<Point> getByUser(User user);

}

