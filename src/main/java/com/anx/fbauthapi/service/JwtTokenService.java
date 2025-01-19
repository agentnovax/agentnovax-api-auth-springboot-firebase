/*
 * Copyright 2024 AgentNovaX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anx.fbauthapi.service;

import com.anx.fbauthapi.dto.request.LoginRequest;
import com.anx.fbauthapi.dto.response.DomainsResponse;
import com.anx.fbauthapi.dto.response.RolesResponse;
import com.anx.fbauthapi.model.UserRoleDomainSessions;
import com.anx.fbauthapi.model.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Value(value = "${security.jwt.issuer}")
    private String issuer;

    public String generateToken(Users user, LoginRequest loginRequest) {
        Map<String, Object> claims = new HashMap<>();
        if (user.getUserDomains() != null && !user.getUserDomains().isEmpty()) {
            List<DomainsResponse> roles = user.getUserDomains().stream()
                    .map(userDomains -> new DomainsResponse(userDomains.getDomain().getDomainName(),
                                userDomains.getUserRoleDomains().stream().map(
                                        userRoleDomains -> new RolesResponse(
                                                userRoleDomains.getRoleDomains().getRole().getRoleName(),
                                                userRoleDomains.getUserRoleDomainSessions().stream().map(
                                                                UserRoleDomainSessions::getUserAgent)
                                                        .collect(Collectors.toSet()))
                                ).collect(Collectors.toSet()))
                        ).toList();
            claims.put("roles", roles);
        }
        claims.put("name", user.getUsername());
        loginRequest.setFullName(user.getUsername());
        claims.put("firstName", loginRequest.getFirstName());
        claims.put("lastName", loginRequest.getLastName());
        claims.put("email", user.getEmail());
        return generateToken(claims, new User(user.getGuid().toString(), "", new ArrayList<>()));
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}