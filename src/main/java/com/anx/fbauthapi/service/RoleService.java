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

import com.anx.fbauthapi.dao.RolesRepository;
import com.anx.fbauthapi.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Value("${anx.auth.defaults.role}")
    private String roleName;

    private final RolesRepository rolesRepository;

    @Autowired
    public RoleService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Cacheable(value = "${anx.cache.value}", key = "'DEFAULT#ROLE'")
    public Roles getDefsultRole() throws Exception {

        return rolesRepository.findByRoleNameAndIsActiveAndIsDeleted(roleName, true, false);
    }

}
