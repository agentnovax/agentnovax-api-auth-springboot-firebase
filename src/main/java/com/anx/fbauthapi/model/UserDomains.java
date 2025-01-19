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
@Table(schema = "auth", name = "user_domains")
public class UserDomains extends CommonFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_domain_id", nullable = false)
    private Long userDomainId;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "domain_id", referencedColumnName = "domain_id")
    private Domains domain;

    @OneToMany(mappedBy = "userDomains", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserRoleDomains> userRoleDomains;

    public void addUserRoleDomain(UserRoleDomains userRoleDomain) {
        if (userRoleDomains == null) {
            userRoleDomains = new MyTreeSet<>(Constants.MY_CUSTOM_COMPARATOR_USER_ROLE_DOMAINS);
            userRoleDomains.add(userRoleDomain);
        } else {
            userRoleDomains.add(userRoleDomain);
        }
    }

    public void removeUserRoleDomain(UserRoleDomains userRoleDomain) {
        if (userRoleDomains != null) {
            userRoleDomains.remove(userRoleDomain);
        }
    }

    public UserDomains(Long updatedBy) {
        super(updatedBy);
    }

    // ...

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserDomains other = (UserDomains) obj;
        return Objects.equals(user, other.user) && Objects.equals(domain, other.domain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, domain);
    }

}
