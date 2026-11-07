package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record StudentRequest(
        @NotBlank String nom,
        @NotBlank String prenom,
        @NotNull @Past LocalDate dateNaissance
) {
}
