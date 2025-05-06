package com.graduation.mangaka.model;

import com.graduation.mangaka.model.TypeAndRole.ChangeType;
import com.graduation.mangaka.model.TypeAndRole.LogType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(nullable = false)
    private LogType logType;
    @Column(nullable = false)
    private Long recordId;
    @Column(nullable = false)
    private ChangeType changeType;
    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdAt;
}
