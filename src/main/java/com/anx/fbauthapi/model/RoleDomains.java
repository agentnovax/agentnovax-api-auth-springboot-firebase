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

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "auth", name = "role_domains")
public class RoleDomains extends CommonFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_domain_id", nullable = false)
    private Long roleDomainId;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Roles role;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "domain_id", referencedColumnName = "domain_id")
    private Domains domain;

    @OneToMany(mappedBy = "roleDomains", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserRoleDomains> userRoleDomains;

    public void addUserDomain(UserRoleDomains userRoleDomain) {
        if (userRoleDomains == null) {
            userRoleDomains = new MyTreeSet<>(Constants.MY_CUSTOM_COMPARATOR_USER_ROLE_DOMAINS);
            userRoleDomains.add(userRoleDomain);
        } else {
            userRoleDomains.add(userRoleDomain);
        }
    }

    public void removeUserDomain(UserRoleDomains userRoleDomain) {
        if (userRoleDomains != null) {
            userRoleDomains.remove(userRoleDomain);
        }
    }

    public RoleDomains(Long updatedBy) {
        super(updatedBy);
    }

    // ...

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RoleDomains other = (RoleDomains) obj;
        return Objects.equals(role, other.role) && Objects.equals(domain, other.domain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, domain);
    }

}
