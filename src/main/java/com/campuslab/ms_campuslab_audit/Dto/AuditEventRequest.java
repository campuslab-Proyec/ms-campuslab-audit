package com.campuslab.ms_campuslab_audit.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditEventRequest {

    @NotBlank
    private String bookingId;

    @NotBlank
    private String action;

    @NotBlank
    private String actorId;

    @NotBlank
    private String actorRole;

    private LocalDateTime timestamp;

    private String detail;
}