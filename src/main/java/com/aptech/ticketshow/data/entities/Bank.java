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
@Table(name = "banks")
public class Bank extends Auditable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4682944501113484931L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "number")
    private Long number;

    @Column(name = "valid_date")
    private Date validDate;

    @Column(name = "cvc")
    private Integer cvc;

    @Column(name = "country")
    private String country;

    @Column(name = "zip")
    private Long zip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
