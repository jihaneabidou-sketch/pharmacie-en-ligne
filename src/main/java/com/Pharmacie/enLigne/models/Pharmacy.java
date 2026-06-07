package com.Pharmacie.enLigne.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pharmacies")
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la pharmacie est obligatoire")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "La zone de la pharmacie est obligatoire")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String zone;

    @NotNull(message = "La latitude est obligatoire")
    @Column(nullable = false)
    private Double latitude;

    @NotNull(message = "La longitude est obligatoire")
    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private boolean active = true;

    public Pharmacy() {}

    public Pharmacy(String name, String zone, Double latitude, Double longitude, boolean active) {
        this.name = name;
        this.zone = zone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = active;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}