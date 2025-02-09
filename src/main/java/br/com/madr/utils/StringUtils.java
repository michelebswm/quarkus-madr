package br.com.madr.utils;

public class StringUtils {

    public static String sanitizeName(String name) {
        if (name == null) {
            return null;
        }

        return name.trim().toLowerCase().replaceAll("\\s+", " ");
    }
}
