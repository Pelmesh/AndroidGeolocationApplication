package com.diplom.diplom.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "geolocations")
public class Geolocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitudes")
    private float latitudes;

    @Column(name = "longitudes")
    private float longitudes;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(float latitudes) {
        this.latitudes = latitudes;
    }

    public float getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(float longitudes) {
        this.longitudes = longitudes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
