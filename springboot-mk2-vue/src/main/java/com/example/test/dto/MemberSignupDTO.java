package com.example.test.dto;

import java.time.LocalDate;

public class MemberSignupDTO {
  private String email;
  private String password;
  private String name;
  private LocalDate birth;
  private String phone_number;
  private String gender;

  // Getters and setters
  public String getEmail() {
      return email;
  }

  public void setEamil(String email) {
      this.email = email;
  }

  public String getPassword() {
      return password;
  }

  public void setPassword(String password) {
      this.password = password;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public LocalDate getBirth() {
      return birth;
  }

  public void setBirth(LocalDate birth) {
      this.birth = birth;
  }

  public String getPhone_number() {
      return phone_number;
  }

  public void setPhone_number(String phone_number) {
      this.phone_number = phone_number;
  }

  public String getGender() {
      return gender;
  }

  public void setGender(String gender) {
      this.gender = gender;
  }

  @Override
  public String toString() {
      return "MemberRequestDto{" +
              "eamil='" + email + '\'' +
              ", password='" + password + '\'' +
              ", name='" + name + '\'' +
              ", birth='" + birth + '\'' +
              ", phone_number='" + phone_number + '\'' +
              ", gender='" + gender + '\'' +
              '}';
  }
}
