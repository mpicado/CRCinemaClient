package com.amazonaws.crcinema.domain;

/**
 * Created by mpicado on 8/14/13.
 */
public class Cinema {

    public enum CinemaType {
        NOVA_CINEMAS,
        CINEMARK
    }

    public Cinema(){

    }

    public Cinema(String name, String address, String btnSelectorName){
        //the selector name comes from the api, so that we do not have it hardcoded
        this.setName(name);
        this.setAddress(address);
        //TODO change this when the selector name comes from the api
        if("Nova Cinemas".equals(this.name)){
            this.setButtonSlectorName("novacinemas_button_selector");
        }
        else if(this.name.contains("Cinemark")){
            this.setButtonSlectorName("cinemark_button_selector");
        }

    }

    private int id;
    private String name;
    private String address;
    private MovieGuide movieGuide;
    private String buttonSlectorName;

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

    public MovieGuide getMovieGuide() {
        return movieGuide;
    }

    public void setMovieGuide(MovieGuide movieGuide) {
        this.movieGuide = movieGuide;
    }

    public String getButtonSlectorName() {
        return buttonSlectorName;
    }

    public void setButtonSlectorName(String buttonSlectorName) {
        this.buttonSlectorName = buttonSlectorName;
    }
}
