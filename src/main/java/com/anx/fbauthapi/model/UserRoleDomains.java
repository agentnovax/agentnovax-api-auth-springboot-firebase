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

import com.anx.fbauthapi.constants.Constants;
import com.anx.fbauthapi.utils.MyTreeSet;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "auth", name = "user_role_domains")
public class UserRoleDomains extends CommonFields {

    @Transient
    @Value("${anx.auth.maxSessions}")
    private int maxSessions;

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_domain_id", nullable = false)
    private Long userRoleDomainId;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "user_domain_id", referencedColumnName = "user_domain_id")
    private UserDomains userDomains;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "role_domain_id", referencedColumnName = "role_domain_id")
    private RoleDomains roleDomains;

    @OneToMany(mappedBy = "userRoleDomains", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserRoleDomainSessions> userRoleDomainSessions;

    public void addUserRoleDomainSessions(UserRoleDomainSessions userRoleDomainSession) {
        System.out.println(userRoleDomainSession);
        if (userRoleDomainSessions == null) {
            userRoleDomainSessions = new MyTreeSet<>(
                    Constants.MY_CUSTOM_COMPARATOR_USER_ROLE_DOMAIN_SESSIONS);
            userRoleDomainSessions.add(userRoleDomainSession);
        } else {
            boolean found = false;
            for (UserRoleDomainSessions session : userRoleDomainSessions) {
                if (userRoleDomainSession.equals(session)) {
                    session.setGuid(userRoleDomainSession.getGuid());
                    session.setUpdatedAt(userRoleDomainSession.getUpdatedAt());
                    session.setUpdatedBy(userRoleDomainSession.getUpdatedBy());
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (userRoleDomainSessions.size() >= maxSessions) {
                    while (userRoleDomainSessions.size() > maxSessions) {
                        userRoleDomainSessions.remove(userRoleDomainSessions.iterator().next());
                    }
                }
                userRoleDomainSessions.add(userRoleDomainSession);
                System.out.println(userRoleDomainSessions);
            }
        }
    }

    public void removeUserRoleDomainSessionSessions(UserRoleDomainSessions userRoleDomainSession) {
        if (userRoleDomainSessions != null) {
            userRoleDomainSessions.remove(userRoleDomainSession);
        }
    }

    public UserRoleDomains(Long updatedBy) {
        super(updatedBy);
    }

    // ...

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserRoleDomains other = (UserRoleDomains) obj;
        return Objects.equals(userDomains, other.userDomains) && Objects.equals(
                roleDomains, other.roleDomains);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDomains, roleDomains);
    }

}
