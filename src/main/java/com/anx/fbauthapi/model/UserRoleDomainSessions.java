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

package com.anx.fbauthapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "auth", name = "user_role_domain_sessions")
public class UserRoleDomainSessions extends CommonFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_domain_session_id", nullable = false)
    private Long userRoleDomainSessionId;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "user_role_domain_id", referencedColumnName = "user_role_domain_id")
    private UserRoleDomains userRoleDomains;

    @ToString.Include
    @Column(name = "user_agent")
    private String userAgent;

    public UserRoleDomainSessions(Long updatedBy) {
        super(updatedBy);
    }

    // ...

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserRoleDomainSessions other = (UserRoleDomainSessions) obj;
        return Objects.equals(userRoleDomains, other.userRoleDomains)
                && Objects.equals(userAgent, other.userAgent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRoleDomains, userAgent);
    }

}
