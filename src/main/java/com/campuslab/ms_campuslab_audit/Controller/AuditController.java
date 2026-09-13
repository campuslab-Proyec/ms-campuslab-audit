package com.campuslab.ms_campuslab_audit.Controller;

import com.campuslab.ms_campuslab_audit.Dto.AuditEventRequest;
import com.campuslab.ms_campuslab_audit.Dto.AuditEventResponse;
import com.campuslab.ms_campuslab_audit.Service.AuditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService service;

    @PostMapping("/events")
    public ResponseEntity<AuditEventResponse> register(@Valid @RequestBody AuditEventRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(req));
    }

    @GetMapping("/events")
    public ResponseEntity<List<AuditEventResponse>> query(
            @RequestParam(required = false) String bookingId,
            @RequestParam(required = false) String actorId) {
        return ResponseEntity.ok(service.search(bookingId, actorId));
    }
}
