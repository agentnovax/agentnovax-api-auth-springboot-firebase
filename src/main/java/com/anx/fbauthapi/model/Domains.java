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
@Table(schema = "auth", name = "domains")
public class Domains extends CommonFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "domain_id", nullable = false)
    private Long domainId;

    @ToString.Include
    @Column(name = "domain_name")
    private String domainName;

    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserDomains> userDomains;

    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RoleDomains> roleDomains;

    public void addUserDomain(UserDomains userDomain) {
        if (userDomains == null) {
            userDomains = new MyTreeSet<>(Constants.MY_CUSTOM_COMPARATOR_USER_DOMAINS);
            userDomains.add(userDomain);
        } else {
            userDomains.add(userDomain);
        }
    }

    public void removeUserDomain(UserDomains userDomain) {
        if (userDomains != null) {
            userDomains.remove(userDomain);
        }
    }

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

    public Domains(Long updatedBy) {
        super(updatedBy);
    }

    // ...

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Domains other = (Domains) obj;
        return Objects.equals(domainName, other.domainName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domainName);
    }

}
