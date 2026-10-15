-- actor_id es el "sub" de Entra ID y actor_role puede contener varios roles separados por coma
ALTER TABLE audit_events MODIFY actor_id VARCHAR(255) NOT NULL;
ALTER TABLE audit_events MODIFY actor_role VARCHAR(255) NOT NULL;
