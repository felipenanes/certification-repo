package com.nnsgroup.certification.users.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
    
    // Método para gerar senhas criptografadas para uso nos scripts Liquibase
    public static void main(String[] args) {
        if (args.length > 0) {
            String password = args[0];
            System.out.println("Senha original: " + password);
            System.out.println("Senha criptografada: " + encode(password));
        } else {
            System.out.println("Uso: java PasswordEncoderUtil <senha>");
            System.out.println("Exemplo: java PasswordEncoderUtil admin123");
        }
    }
} 