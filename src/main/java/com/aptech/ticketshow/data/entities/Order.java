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
@Table(name = "orders")
public class Order extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email_receive")
    private String emailReceive;

    @Column(name = "ticket_pdf_path")
    private String ticketPdfPath;

    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "business_tax")
    private Long businessTax;

    @Column(name = "business_address")
    private String businessAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;
}
