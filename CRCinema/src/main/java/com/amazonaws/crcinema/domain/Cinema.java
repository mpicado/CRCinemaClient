package com.amazonaws.crcinema.domain;

/**
 * Created by mpicado on 8/14/13.
 */
public class Cinema {

    public enum CinemaType {
        NOVA_CINEMAS,
        CINEMARK,
        CINEMARK_ESTE,
        CCM_PASEO,
        CCM_LINCOLN
    }

    private int id;
    private String name;
    private String address;
    private String cinemaImageName;
    private String cinemaType;

    MovieGuide movieGuide;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCinemaImageName() {
        return cinemaImageName;
    }

    public void setCinemaImageName(String cinemaImageName) {
        this.cinemaImageName = cinemaImageName;
    }

    public String getCinemaType() {
        return cinemaType;
    }

    public void setCinemaType(String cinemaType) {
        this.cinemaType = cinemaType;
    }

    public MovieGuide getMovieGuide() {
        return movieGuide;
    }

    public void setMovieGuide(MovieGuide movieGuide) {
        this.movieGuide = movieGuide;
    }
}
