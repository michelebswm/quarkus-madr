package br.com.madr.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {

    public static String encrypt(String senha){
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public static boolean verify(String senha, String hash){
        return BCrypt.checkpw(senha, hash);
    }

}
