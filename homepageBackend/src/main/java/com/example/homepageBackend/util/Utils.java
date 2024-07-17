package com.example.homepageBackend.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class Utils {

    // Méthode pour trouver la méthode getter correspondante pour une propriété donnée
    public static Method findGetterMethod(Object object, String fieldName) {
        String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(getterMethodName)) {
                return method;
            }
        }
        return null;
    }

    // Convertit le nom de colonne en nom de champ Java
    public static String convertColumnNameToFieldName(String columnName) {
        StringBuilder fieldNameBuilder = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < columnName.length(); i++) {
            char c = columnName.charAt(i);
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    fieldNameBuilder.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    fieldNameBuilder.append(Character.toLowerCase(c));
                }
            }
        }
        return fieldNameBuilder.toString();
    }
}
