package com.trustfinity.Tlead.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trustfinity.Tlead.models.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
	
	
	public Users findByEmail(String email);

	public Optional<Users> findByPhoneNumber(String phoneNumber);
	public Users findByPasstoken(String token);
	
	public List<Users> findAllByReferalId(String referalId);
	public List<Users> findByFirstNameContaining(String firstName);

	
	@Query(value="select * from users where first_name like :likePattern", nativeQuery = true)

	List<Users> findByFirstNameLike(String likePattern);

	@Query(value="select * from users where phone_number=:phonenumber", nativeQuery = true)

	public Users findByPhoneNumberr(String phonenumber);
	
	public Users findByMyRefId(String myRefId);
}
