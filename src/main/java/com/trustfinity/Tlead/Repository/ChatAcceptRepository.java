package com.trustfinity.Tlead.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trustfinity.Tlead.models.ChatAccept;

@Repository
public interface ChatAcceptRepository extends JpaRepository<ChatAccept, Long>{
	
	public List<ChatAccept> findByUsersEmail(String email);
	public ChatAccept findByAcceptEmailAndUsersEmail(String acceptEmail,String email);
	
	

}
