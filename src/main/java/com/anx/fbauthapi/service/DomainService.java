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

import com.anx.fbauthapi.dao.DomainsRepository;
import com.anx.fbauthapi.model.Domains;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DomainService {

    private final DomainsRepository domainsRepository;

    public DomainService(DomainsRepository domainsRepository) {
        this.domainsRepository = domainsRepository;
    }

    @Cacheable(value = "${anx.cache.value}", key = "'DEFAULT' + #domainName")
    public Domains getDomain(String domainName) throws Exception {
        return domainsRepository.findByDomainNameAndIsActiveAndIsDeleted(domainName, true, false);
    }

}
