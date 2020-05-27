package com.example.cinemhub.trailer;

public class VideoStream {

    private String mtitle;

    private String mhostUrl;

    private String mUrl;

    public VideoStream(String title, String hostUrl) {
        mtitle = title;
        mhostUrl = hostUrl;
    }

    public String getTitle() {
        return mtitle;
    }

    public String getHostUrl() {
        return mhostUrl;
    }

}