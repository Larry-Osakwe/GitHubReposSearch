package com.aerofs.takehometest;

/**
 * Created by Larry Osakwe on 8/26/2017.
 */

public class Repo {

    private String repoName;
    private int forkCount;
    private String lastUpdated;
    private String language;

    public Repo(String repoName, int forkCount, String lastUpdated, String language) {
        this.repoName = repoName;
        this.forkCount = forkCount;
        this.lastUpdated = lastUpdated;
        this.language = language;
    }

    public String getRepoName() {
        return repoName;
    }

    public int getForkCount() {
        return forkCount;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getLanguage() {
        return language;
    }


}
