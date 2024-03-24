package com.aptech.ticketshow.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "favorites")
public class Feedback extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    @Column(name = "admin_reply")
    private String adminReply;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;
}
