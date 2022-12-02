package io.devpl.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 密钥
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretKey {

    private String token;

    private String key;
}
