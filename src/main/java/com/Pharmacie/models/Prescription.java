package com.Pharmacie.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le patient émetteur est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @NotNull(message = "La pharmacie destinataire est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

    @NotBlank(message = "Le lien du fichier de l'ordonnance est obligatoire")
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PrescriptionStatus status = PrescriptionStatus.RECUE;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Prescription() {}

    public Prescription(User patient, Pharmacy pharmacy, String filePath) {
        this.patient = patient;
        this.pharmacy = pharmacy;
        this.filePath = filePath;
        this.status = PrescriptionStatus.RECUE;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getPatient() { return patient; }
    public void setPatient(User patient) { this.patient = patient; }
    public Pharmacy getPharmacy() { return pharmacy; }
    public void setPharmacy(Pharmacy pharmacy) { this.pharmacy = pharmacy; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public PrescriptionStatus getStatus() { return status; }
    public void setStatus(PrescriptionStatus status) { this.status = status; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}