package com.aptech.ticketshow.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "vouchers")
public class Voucher extends Auditable {

	private static final long serialVersionUID = -3106159136345065847L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private Double value;

    @Column(name = "minOrderTotal")
    private Double minOrderTotal;

    @Column(name = "started_at")
    private Date startedAt;

    @Column(name = "ended_at")
    private Date endedAt;

    @Column(name = "code")
    private String code;
}
