package com.constants;

public class InputValidator {
    
    public static String validatePhoneString(String text){
        text = text.replaceAll(" ", "");
        while(text.startsWith("0") || text.startsWith(" ") || text.endsWith(" ")){
            text = text.trim();
            text = text.startsWith("0") ? text.substring(1) : text;
        }
        return text;
    }
    
    public static String validateText( String text ){
        if(text != null ) {
            while (text.startsWith(" ") || text.endsWith(" ")) {
                text = text.trim();
            }
        }
        return text;
    }
}
