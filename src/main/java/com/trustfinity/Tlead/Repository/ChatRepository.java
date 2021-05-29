package com.trustfinity.Tlead.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trustfinity.Tlead.models.ChatModel;
import com.trustfinity.Tlead.models.Users;

public interface ChatRepository extends JpaRepository<ChatModel, Long>{
	
	public List<ChatModel> findByUsersEmail(String email);
	
	public ChatModel findByChatEmailAndUsersEmail(String chatEmail,String email);
	


	
	public ChatModel findByRecipientNameAndUsersEmail(String recipientName,String email);
	
}
