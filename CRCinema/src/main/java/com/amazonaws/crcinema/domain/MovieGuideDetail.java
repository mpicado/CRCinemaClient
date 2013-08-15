package com.amazonaws.crcinema.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by mpicado on 8/14/13.
 */
public class MovieGuideDetail {
    private Movie movie;
    private String cinemaLocation;
    private List<Date> times;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getCinemaLocation() {
        return cinemaLocation;
    }

    public void setCinemaLocation(String cinemaLocation) {
        this.cinemaLocation = cinemaLocation;
    }

    public List<Date> getTimes() {
        return times;
    }

    public void setTimes(List<Date> times) {
        this.times = times;
    }
}
