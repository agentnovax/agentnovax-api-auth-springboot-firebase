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

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class CommonFields implements Serializable {

    @ToString.Include
    @Column(name = "guid")
    private UUID guid;

    @ToString.Include
    @Column(name = "is_active")
    private Boolean isActive;

    @ToString.Include
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ToString.Include
    @Column(name = "created_by")
    private Long createdBy;

    @ToString.Include
    @Column(name = "modified_by")
    private Long updatedBy;

    @ToString.Include
    @Column(name = "created_at")
    private Date createdAt;

    @ToString.Include
    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public CommonFields(Long updatedBy) {
        this.updatedBy = updatedBy;
        this.createdBy = updatedBy;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.guid = UUID.randomUUID();
        this.isActive = true;
        this.isDeleted = false;
    }

    public void update(Long updatedBy) {
        this.updatedBy = updatedBy;
        this.updatedAt = new Date();
    }

}
