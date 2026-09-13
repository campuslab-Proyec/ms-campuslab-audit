package com.campuslab.ms_campuslab_audit.Service;

import com.campuslab.ms_campuslab_audit.Dto.AuditEventRequest;
import com.campuslab.ms_campuslab_audit.Dto.AuditEventResponse;
import com.campuslab.ms_campuslab_audit.Model.AuditEvent;
import com.campuslab.ms_campuslab_audit.Repository.AuditEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditEventRepository repository;

    @Transactional
    public AuditEventResponse save(AuditEventRequest req) {
        AuditEvent event = new AuditEvent();
        event.setBookingId(req.getBookingId());
        event.setAction(req.getAction());
        event.setActorId(req.getActorId());
        event.setActorRole(req.getActorRole());
        event.setTimestamp(req.getTimestamp() != null ? req.getTimestamp() : LocalDateTime.now());
        event.setDetail(req.getDetail());

        AuditEvent saved = repository.save(event);
        return toResponse(saved);
    }

    public List<AuditEventResponse> search(String bookingId, String actorId) {
        return repository.search(bookingId, actorId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AuditEventResponse toResponse(AuditEvent event) {
        return new AuditEventResponse(
                event.getId(),
                event.getBookingId(),
                event.getAction(),
                event.getActorId(),
                event.getActorRole(),
                event.getTimestamp(),
                event.getDetail()
        );
    }
}
