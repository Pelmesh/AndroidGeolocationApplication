package com.diplom.diplom.service;

import com.diplom.diplom.model.DaysHistory;
import com.diplom.diplom.model.Geolocation;
import com.diplom.diplom.model.User;
import org.springframework.data.domain.Sort;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface GeolocationService {

    Geolocation save(Geolocation geolocation);

    Optional getById(Long id);

    List<DaysHistory> getDaysHistory(User user);

    List<Geolocation> findByUserAndDate(User user, Date date, Sort sort);

}
