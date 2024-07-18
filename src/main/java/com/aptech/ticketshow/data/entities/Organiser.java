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
@Table(name = "organisers")
public class Organiser extends Auditable {

	private static final long serialVersionUID = 8212787252756185258L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "avatar_image_path")
    private String avatarImagePath;

    @Column(name = "individual_name")
    private String individualName;

    @Column(name = "individual_tax")
    private Long individualTax;

    @Column(name = "individual_indentify")
    private Long individualIdentify;

    @Column(name = "individual_email")
    private String individualEmail;

    @Column(name = "individual_address")
    private String individualAddress;

    @Column(name = "individual_district")
    private String individualDistrict;

    @Column(name = "individual_ward")
    private String individualWard;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "business_tax")
    private Long businessTax;

    @Column(name = "business_issue_place")
    private String businessIssuePlace;

    @Column(name = "business_issue_date")
    private Date businessIssueDate;

    @Column(name = "business_phone")
    private String businessPhone;

    @Column(name = "business_email")
    private String businessEmail;

    @Column(name = "business_address")
    private String businessAddress;

    @Column(name = "business_province")
    private String businessProvince;

    @Column(name = "business_district")
    private String businessDistrict;

    @Column(name = "business_ward")
    private String businessWard;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "default_bank_id")
    private Long defaultBankId;
}
