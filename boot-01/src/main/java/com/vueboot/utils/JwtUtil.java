package com.vueboot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "jwt.config")
public class JwtUtil {
    /**
     * 私钥
     */
    private String key;

    /**
     * token失效时间
     */
    private Long ttl;

    /**
     * 生成token
     *
     * @param id   用户id
     * @param name 用户名称
     * @param map  参数
     * @return token
     */
    public String createJwt(Integer id, String name, Map<String, Object> map) {
        // 设置失效时间
        long now = System.currentTimeMillis();
        long exp = now + ttl;

        // 创建 JwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(id.toString()).setSubject(name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        // 设置claims
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            jwtBuilder.claim(entry.getKey(), entry.getValue());
        }
        jwtBuilder.setExpiration(new Date(exp));
        // 生成token
        return jwtBuilder.compact();
    }

    /**
     * 解析token 获取Claims
     *
     * @param token jwt生成的token
     * @return Claims
     */
    public Claims parseJwt(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
