package com.enigma.challengebookingroom.service;

import com.enigma.challengebookingroom.dto.response.JWTClaims;
import com.enigma.challengebookingroom.entity.User;

public interface JwtService {
    String generateToken(User userAccount);
    Boolean verifyToken(String token);
    JWTClaims claimToken(String token);
}
