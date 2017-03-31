package com.iptv.signin.bean;

/**
 * Created by ZhangQian on 2017/3/26 0026.
 */

public class BaseMovie {

    /**
     * movieName :
     * movieUrl :
     * movieDesc :
     * movieImage :
     * movietype :
     */

    private String movieName;
    private String movieUrl;
    private String movieDesc;
    private String movieImage;
    private String movietype;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovietype() {
        return movietype;
    }

    public void setMovietype(String movietype) {
        this.movietype = movietype;
    }
}
