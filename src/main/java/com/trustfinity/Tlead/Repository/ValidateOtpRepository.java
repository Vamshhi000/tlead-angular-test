package com.trustfinity.Tlead.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trustfinity.Tlead.models.ValidateEmailNumber;

public interface ValidateOtpRepository extends JpaRepository<ValidateEmailNumber, String> {
	public ValidateEmailNumber findByEmail(String email);

}
