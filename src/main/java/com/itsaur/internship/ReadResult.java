package com.itsaur.internship;

public class ReadResult {
    private int currentPosition;
    private int usernameLength;
    private int totalLength;
    private int userPosition;
    User userReadResult;
    private static String pathUser[] = new String[2];

    public static void setPathUser(int position, String path){
        pathUser[position] = path;
    }

    public static String getPathUser(int position){
        return pathUser[position];
    }


    public int getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(int userPosition) {
        this.userPosition = userPosition;
    }

    public ReadResult() {
        userReadResult = new User();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getUsernameLength() {
        return usernameLength;
    }

    public void setUsernameLength(int usernameLength) {
        this.usernameLength = usernameLength;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

}
