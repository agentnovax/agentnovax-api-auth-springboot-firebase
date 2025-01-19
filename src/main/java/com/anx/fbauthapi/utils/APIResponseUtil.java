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

package com.anx.fbauthapi.utils;

import com.anx.fbauthapi.constants.Constants;
import com.anx.fbauthapi.dto.response.APIResponse;
import com.anx.fbauthapi.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public final class APIResponseUtil<T extends Response> {

    public APIResponse<T> getSuccessResponse(T data) {
        return new APIResponse<T>(Constants.SUCCESS_CODE, Constants.SUCCESS_STATUS, Constants.SUCCESS_MESSAGE, data);
    }
    public APIResponse<T> getErrorResponse(T data) {
        return new APIResponse<T>(Constants.ERROR_CODE, Constants.ERROR_STATUS, Constants.ERROR_MESSAGE, data);
    }
    public APIResponse<T> getAccessDeniedResponse(T data) {
        return new APIResponse<T>(Constants.ACCESS_DENIED_CODE, Constants.ACCESS_DENIED_STATUS, Constants.ACCESS_DENIED_MESSAGE, data);
    }
    public APIResponse<T> getUnverifiedResponse(T data) {
        return new APIResponse<T>(Constants.UNVERIFIED_CODE, Constants.UNVERIFIED_STATUS, Constants.UNVERIFIED_MESSAGE, data);
    }

    public ResponseEntity<APIResponse<T>> getApiResponse(APIResponse<T> apiResponse) {
        if (apiResponse == null) {
            apiResponse = getErrorResponse(null);
        }
        return new ResponseEntity<>(apiResponse, apiResponse.code());
    }

}
