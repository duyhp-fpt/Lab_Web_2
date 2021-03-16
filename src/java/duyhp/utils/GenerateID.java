/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.utils;

import java.util.Random;

/**
 *
 * @author Huynh Duy
 */
public class GenerateID {

    public static String getID(String SubjectID) {
        String RANDOM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * RANDOM.length());
            salt.append(RANDOM.charAt(index));
        }
        String randomString = salt.toString();
        return "[" + SubjectID.toUpperCase() + "]" + randomString;
    }
}
