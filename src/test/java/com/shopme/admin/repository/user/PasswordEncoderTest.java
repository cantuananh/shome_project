package com.shopme.admin.repository.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "12345678";
        String encodePassword = passwordEncoder.encode(rawPassword);
        System.out.println(encodePassword);

        boolean matches = passwordEncoder.matches(rawPassword, encodePassword);

        assertThat(matches).isTrue();
    }
}
