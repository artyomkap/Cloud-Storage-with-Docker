package com.mycompany.syssoft_assignment;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**

UserSession class provides a static method to store and retrieve the user ID.
The user ID is stored in a private static integer variable called UserId.
It is accessed through static getter and setter methods.
*/


/**
 *
 * @author ntu-user
 */
public class UserSession {
    private static int UserId;

    /**
 * getUserID is a static method to get the user ID.
 *
 * @return UserId The current user ID.
 */
    public static int getUserID() {
        return UserId;
    }
/**
 * setUserID is a static method to set the user ID.
 *
 * @param userId The user ID to set.
 */
    public static void setUserID(int userId) {
        UserSession.UserId = userId;
        System.out.println(UserId);
    }
}
