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

import com.google.firebase.auth.FirebaseToken;
import com.anx.fbauthapi.dto.request.LoginRequest;
import com.anx.fbauthapi.dto.response.APIResponse;
import com.anx.fbauthapi.dto.response.LoginResponse;
import com.anx.fbauthapi.utils.APIResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final FirebaseService firebaseService;
    private final APIResponseUtil<LoginResponse> apiResponseUtil;
    private final UserService userService;

    @Autowired
    public AuthService(FirebaseService firebaseService, APIResponseUtil<LoginResponse> apiResponseUtil, UserService userService) {
        this.firebaseService = firebaseService;
        this.apiResponseUtil = apiResponseUtil;
        this.userService = userService;
    }

    public ResponseEntity<APIResponse<LoginResponse>> login(String idToken, String userAgent, String domainName) {
        APIResponse<LoginResponse> apiResponse = apiResponseUtil.getErrorResponse(null);
        try {
            FirebaseToken decodedToken = firebaseService.verifyIdToken(idToken.split("Bearer ")[1]);
            LoginRequest loginRequest;
            if (decodedToken != null) {
                loginRequest = new LoginRequest();
                loginRequest.setEmail(decodedToken.getEmail());
                loginRequest.setFullName(decodedToken.getName());
                loginRequest.setUserAgent(userAgent);
                loginRequest.setDomainName(domainName);
                if (decodedToken.isEmailVerified())
                    apiResponse = apiResponseUtil.getSuccessResponse(userService.login(loginRequest));
                else
                    apiResponse = apiResponseUtil.getUnverifiedResponse(userService.login(loginRequest));
            } else
                apiResponse = apiResponseUtil.getAccessDeniedResponse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponseUtil.getApiResponse(apiResponse);
    }

}
