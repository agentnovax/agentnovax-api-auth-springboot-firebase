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

import com.anx.fbauthapi.dao.UsersRepository;
import com.anx.fbauthapi.dto.request.LoginRequest;
import com.anx.fbauthapi.dto.response.LoginResponse;
import com.anx.fbauthapi.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final JwtTokenService jwtTokenService;
    private final DomainService domainService;
    private final RoleDomainService roleDomainService;

    @Autowired
    public UserService(UsersRepository usersRepository, JwtTokenService jwtTokenService,
                       DomainService domainService, RoleDomainService roleDomainService) {
        this.usersRepository = usersRepository;
        this.jwtTokenService = jwtTokenService;
        this.domainService = domainService;
        this.roleDomainService = roleDomainService;
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        Users user = getUserFromDatabase(loginRequest.getEmail());
        if (user == null) {
            user = createUser(loginRequest);
        } else {
            if (user.getUserDomains() == null || user.getUserDomains().isEmpty()) {
                user.addUserDomain(createUserDomain(user, loginRequest));
            } else {
                boolean flag = false;
                for (UserDomains userDomains : user.getUserDomains()) {
                    if (userDomains.getDomain().equals(domainService.getDomain(
                            loginRequest.getDomainName()))) {
                        if (userDomains.getUserRoleDomains() == null
                                || userDomains.getUserRoleDomains().isEmpty()) {
                            userDomains.addUserRoleDomain(createUserRoleDomain(userDomains,
                                    loginRequest));
                        } else {
                            for (UserRoleDomains userRoleDomains : userDomains.getUserRoleDomains()) {
                                if (userRoleDomains.getRoleDomains().equals(
                                        roleDomainService.getRoleDomains(userDomains.getDomain()))) {
                                    userRoleDomains
                                            .addUserRoleDomainSessions(createUserRoleDomainSessions(
                                                    userRoleDomains, loginRequest));
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                             userDomains.addUserRoleDomain(createUserRoleDomain(userDomains,
                                     loginRequest));
                             flag = true;
                             break;
                            }
                        }
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    user.addUserDomain(createUserDomain(user,
                            loginRequest));
                }
            }
        }
        usersRepository.saveAndFlush(user);
        System.out.println(user);
        System.out.println(user.getUserDomains().stream().findFirst().get().getUserRoleDomains());
        return new LoginResponse(jwtTokenService.generateToken(user, loginRequest),
                loginRequest.getSessionId());
    }

    private Users getUserFromDatabase(String email) throws Exception {
        return usersRepository.findByEmailAndIsActiveAndIsDeleted(email, true, false);
    }

    private Users createUser(LoginRequest loginRequest) throws Exception {
        Users user = new Users(0L);
        user.setEmail(loginRequest.getEmail());
        user.setUsername(loginRequest.getFullName());
        user.addUserDomain(createUserDomain(user, loginRequest));
        return user;
    }

    private UserDomains createUserDomain(Users users, LoginRequest loginRequest)
            throws Exception {
        Domains domains = domainService.getDomain(loginRequest.getDomainName());
        UserDomains userDomains = new UserDomains(0L);
        userDomains.setUser(users);
        userDomains.setDomain(domains);
        userDomains.addUserRoleDomain(createUserRoleDomain(userDomains, loginRequest));
        return userDomains;
    }

    private UserRoleDomains createUserRoleDomain(UserDomains userDomains, LoginRequest loginRequest)
            throws Exception {
        UserRoleDomains userRoleDomains = new UserRoleDomains(0L);
        userRoleDomains.setUserDomains(userDomains);
        userRoleDomains.setRoleDomains(roleDomainService.getRoleDomains(userDomains.getDomain()));
        userRoleDomains.addUserRoleDomainSessions(createUserRoleDomainSessions(
                userRoleDomains, loginRequest));
        return userRoleDomains;
    }

    private UserRoleDomainSessions createUserRoleDomainSessions(
            UserRoleDomains userRoleDomains, LoginRequest loginRequest) throws Exception {
        UserRoleDomainSessions userRoleDomainSessions = new UserRoleDomainSessions(0L);
        userRoleDomainSessions.setUserAgent(loginRequest.getUserAgent());
        userRoleDomainSessions.setUserRoleDomains(userRoleDomains);
        loginRequest.setSessionId(userRoleDomainSessions.getGuid());
        return userRoleDomainSessions;
    }

}
