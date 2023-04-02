package com.example.snailscurlup.ui.leaderboard;

/**
 * Leader class represents a user on the leaderboard. This class contains
 * information about the user such as their username, email, and phone number.
 *
 * @author Richard He
 * @version 1.0
 * @since 2023-04-01
 */
public class Leader {
    private String username;
    private String email;
    private String phone;

    /**
     * Constructs a new Leader with the specified username, email, and phone number.
     *
     * @param username The user's username.
     * @param email The user's email.
     * @param phone The user's phone number.
     */
    Leader(String username, String email, String phone){
        this.username=username;
        this.email=email;
        this.phone=phone;
    }

    /**
     * Returns the user's username.
     *
     * @return The user's username.
     */
    String getUsername(){return this.username;}
    /**
     * Returns the user's phone number.
     *
     * @return The user's phone number.
     */
    String getEmail(){
        return this.email;
    }
    /**
     * Returns the user's phone number.
     *
     * @return The user's phone number.
     */
    String getPhone(){
        return this.phone;
    }
}
