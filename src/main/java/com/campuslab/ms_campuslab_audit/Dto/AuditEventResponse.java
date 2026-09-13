package com.campuslab.ms_campuslab_audit.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuditEventResponse {
    private Long id;
    private String bookingId;
    private String action;
    private String actorId;
    private String actorRole;
    private LocalDateTime timestamp;
    private String detail;
}
