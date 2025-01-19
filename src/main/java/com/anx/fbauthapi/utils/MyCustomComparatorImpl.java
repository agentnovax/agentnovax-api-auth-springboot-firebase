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

import com.anx.fbauthapi.model.CommonFields;

public class MyCustomComparatorImpl<T> implements MyCustomComparator<T> {

    @Override
    public int compare(T o1, T o2) {

        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            return 1;
        }

        CommonFields ob1 = (CommonFields) o1;
        CommonFields ob2 = (CommonFields) o2;

        return ob1.getUpdatedAt().compareTo(ob2.getUpdatedAt());
    }
    
}
