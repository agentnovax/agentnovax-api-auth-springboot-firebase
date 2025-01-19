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

package com.anx.fbauthapi.dto.request;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class LoginRequest {

    private String email;
    private String fullName;
    private String firstName;
    private String lastName;
    private String middleName;
    private String userAgent;
    private String domainName;
    private UUID sessionId;

    public void setFullName(String fullName) {
        this.fullName = fullName;
        if (this.fullName != null) {
            String[] names = this.fullName.split(" ");
            this.firstName = names[0];
            if (names.length > 1)
                this.lastName = names[names.length - 1];
            if (names.length > 2)
                this.middleName = names[names.length - 2];
        }
    }

}
