package com.amazonaws.crcinema.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by mpicado on 8/14/13.
 */
public class MovieGuide {
    private Date date;
    private List<MovieGuideDetail> movieDetails;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<MovieGuideDetail> getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(List<MovieGuideDetail> movieDetails) {
        this.movieDetails = movieDetails;
    }
}
