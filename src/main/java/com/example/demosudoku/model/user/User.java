package com.example.demosudoku.model.user;

/**
 * Class used for representing the user currently playing the game.
 */

public class User {

    /**
     * The nickname of the user.
     */

    private String nickname;

    /**
     * The constructor for this User class.
     * @param nickname the nickname typed in by the user.
     */

    public User(String nickname) {
        this.nickname = nickname;
    }

    /**
     * getter for the user's nickname
     * @return the user's nickname
     */

    public String getNickname() {
        return nickname;
    }

    /**
     * Setter for the user's nickname
     * @param nickname the nickname to be set as the user's nickname
     */

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
