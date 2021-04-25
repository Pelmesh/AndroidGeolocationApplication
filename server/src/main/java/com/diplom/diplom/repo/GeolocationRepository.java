package com.diplom.diplom.repo;

import com.diplom.diplom.model.DaysHistory;
import com.diplom.diplom.model.Geolocation;
import com.diplom.diplom.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface GeolocationRepository extends JpaRepository<Geolocation, Long> {

    @Query(value = "select Distinct date, count(date) from geolocations where user_id = ? group by date", nativeQuery = true)
    List<DaysHistory> getDaysHistory(User user);

    List<Geolocation> findByUserAndAndDate(User user, Date date, Sort sort);

    List<Geolocation> findByUser(User user);

}