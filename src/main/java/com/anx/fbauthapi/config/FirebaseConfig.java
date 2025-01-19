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

package com.anx.fbauthapi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${anx.firebase.config.path}")
    private String firebaseConfigPath;

    @Bean(value = "firebaseInitializer")
    FirebaseApp initialise() throws Exception {
        InputStream inputStream = TypeReference.class.getResourceAsStream(firebaseConfigPath);


        assert inputStream != null;
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build();

        return FirebaseApp.initializeApp(options);
    }

}