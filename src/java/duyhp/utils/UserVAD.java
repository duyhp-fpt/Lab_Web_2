/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Huynh Duy
 */
public class UserVAD {
       private static final String EMAIL_PATTERN = "[a-z0-9-.]{5,32}@[a-z0-9-.]{5,32}(.[a-z0-9-]{2,5}){1,2}";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    public static boolean checkEmail(String email) {
        Matcher match = pattern.matcher(email);
        return match.find();
    }
    public static String encodePassword(String password){
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(password); 
    }
}
