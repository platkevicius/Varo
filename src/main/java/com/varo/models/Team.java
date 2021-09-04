package com.varo.models;

public class Team {

    private final String user1;
    private final String user2;

    public Team(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

}
