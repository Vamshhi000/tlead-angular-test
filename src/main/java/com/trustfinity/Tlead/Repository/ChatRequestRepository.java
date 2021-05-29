package com.trustfinity.Tlead.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trustfinity.Tlead.models.ChatRequest;
@Repository
public interface ChatRequestRepository extends JpaRepository<ChatRequest, Long>{
	
	public List<ChatRequest> findByUsersEmail(String email);
	
	public ChatRequest findByRequestEmailAndUsersEmail(String requestEmail,String email);
//	public ChatRequest findByRequestEmailAndUsersPhoneNumber(String requestEmail,String phoneNumber);
 

}
