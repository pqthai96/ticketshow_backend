package com.aptech.ticketshow.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event extends Auditable {

	private static final long serialVersionUID = -3867597010973040270L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private boolean type;

    @Column(name = "title")
    private String title;

    @Column(name = "venue_name")
    private String venueName;

    @Column(name = "location_address")
    private String locationAddress;

    @Column(name = "location_province")
    private String locationProvince;

    @Column(name = "location_district")
    private String locationDistrict;

    @Column(name = "location_ward")
    private String locationWard;

    @Column(name = "started_at")
    private Date startedAt;

    @Column(name = "ended_at")
    private Date endedAt;

    @Column(name = "position_image_path")
    private String positionImagePath;

    @Column(name = "banner_image_path")
    private String bannerImagePath;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Lob
    @Column(name = "all_seats", columnDefinition = "LONGTEXT")
    private String allSeats;

    @Lob
    @Column(name = "booked_seats", columnDefinition = "LONGTEXT")
    private String bookedSeats;

    @Column(name = "seat_price")
    private Double seatPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organiser_id")
    private Organiser organiser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "edited_by_admin_id")
    private Admin editedByAdmin;
}
