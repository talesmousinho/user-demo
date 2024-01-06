package com.user.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.user.demo.utils.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotBlank(message = "Name is required")
  @Size(max = 25, message = "Name must be less than 25 characters")
  private String name;

  @Column(nullable = false)
  @NotBlank(message = "Surname is required")
  @Size(max = 50, message = "Surname must be less than 50 characters")
  private String surname;

  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  @NotNull(message = "Birthdate is required")
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  private Date birthdate;
}
