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

package com.anx.fbauthapi.constants;

import com.anx.fbauthapi.model.RoleDomains;
import com.anx.fbauthapi.model.UserDomains;
import com.anx.fbauthapi.model.UserRoleDomainSessions;
import com.anx.fbauthapi.model.UserRoleDomains;
import com.anx.fbauthapi.utils.MyCustomComparator;
import com.anx.fbauthapi.utils.MyCustomComparatorImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public final class Constants {

    public static final String SUCCESS_MESSAGE = "Success";
    public static final String ERROR_MESSAGE = "Error";
    public static final String ACCESS_DENIED_MESSAGE = "Access denied";
    public static final String UNVERIFIED_MESSAGE = "Unverified";
    public static final String SUCCESS_STATUS = "ANX_200";
    public static final String ERROR_STATUS = "ANX_500";
    public static final String ACCESS_DENIED_STATUS = "ANX_401";
    public static final String UNVERIFIED_STATUS = "ANX_202";
    public static final HttpStatusCode SUCCESS_CODE = HttpStatus.OK;
    public static final HttpStatusCode ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR;
    public static final HttpStatusCode ACCESS_DENIED_CODE = HttpStatus.UNAUTHORIZED;
    public static final HttpStatusCode UNVERIFIED_CODE = HttpStatus.ACCEPTED;
    public static final MyCustomComparator<UserDomains> MY_CUSTOM_COMPARATOR_USER_DOMAINS = new MyCustomComparatorImpl<>();
    public static final MyCustomComparator<RoleDomains> MY_CUSTOM_COMPARATOR_ROLE_DOMAINS = new MyCustomComparatorImpl<>();
    public static final MyCustomComparator<UserRoleDomains> MY_CUSTOM_COMPARATOR_USER_ROLE_DOMAINS= new MyCustomComparatorImpl<>();
    public static final MyCustomComparator<UserRoleDomainSessions> MY_CUSTOM_COMPARATOR_USER_ROLE_DOMAIN_SESSIONS = new MyCustomComparatorImpl<>();

}
