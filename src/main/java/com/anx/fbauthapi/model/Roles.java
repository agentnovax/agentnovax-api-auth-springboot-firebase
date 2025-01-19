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
@Table(schema = "auth", name = "roles")
public class Roles extends CommonFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @ToString.Include
    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RoleDomains> roleDomains;

    public void addRoleDomain(RoleDomains roleDomain) {
        if (roleDomains == null) {
            roleDomains = new MyTreeSet<>(Constants.MY_CUSTOM_COMPARATOR_ROLE_DOMAINS);
            roleDomains.add(roleDomain);
        } else {
            roleDomains.add(roleDomain);
        }
    }

    public void removeRoleDomain(RoleDomains roleDomain) {
        if (roleDomains != null) {
            roleDomains.remove(roleDomain);
        }
    }

    public Roles(Long updatedBy) {
        super(updatedBy);
    }

    // ...

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Roles other = (Roles) obj;
        return Objects.equals(roleName, other.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }

}
