package com.graduation.mangaka.model.TypeAndRole;

public enum MangaStatus {
    PENDING,    // Submitted by user, waiting for admin review
    APPROVED,   // Approved by admin, visible to public
    REJECTED,   // Rejected by admin, not shown to public
    HIDDEN,     // Temporarily hidden from public (optional)
    DELETED,     // Marked as deleted (soft delete)
    UPDATE, // Marked for new manga update
}

