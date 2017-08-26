package com.aerofs.takehometest;

/**
 * Created by Larry Osakwe on 8/26/2017.
 */

public class Repo {

    private String repoName;
    private int forkCount;
    private int lastUpdated;

    public Repo(String repoName, int forkCount, int lastUpdated) {
        this.repoName = repoName;
        this.forkCount = forkCount;
        this.lastUpdated = lastUpdated;
    }

    public String getRepoName() {
        return repoName;
    }

    public int getForkCount() {
        return forkCount;
    }

    public int getLastUpdated() {
        return lastUpdated;
    }




}
