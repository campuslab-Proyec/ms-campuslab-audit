package com.campuslab.ms_campuslab_audit.Repository;

import com.campuslab.ms_campuslab_audit.Model.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuditEventRepository extends JpaRepository<AuditEvent, Long> {

    @Query("SELECT a FROM AuditEvent a WHERE " +
            "(:bookingId IS NULL OR a.bookingId = :bookingId) AND " +
            "(:actorId IS NULL OR a.actorId = :actorId) " +
            "ORDER BY a.timestamp DESC")
    List<AuditEvent> search(String bookingId, String actorId);
}
