package com.trustfinity.Tlead.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trustfinity.Tlead.Repository.ChatAcceptRepository;
import com.trustfinity.Tlead.Repository.ChatRequestRepository;
import com.trustfinity.Tlead.Repository.UserRepository;
import com.trustfinity.Tlead.Service.ChatAcceptService;
import com.trustfinity.Tlead.Service.ChatRequestService;
import com.trustfinity.Tlead.models.ChatAccept;
import com.trustfinity.Tlead.models.ChatRequest;
import com.trustfinity.Tlead.models.Users;
@Service
public class ChatAcceptServiceImpl implements ChatAcceptService{
	@Autowired
	private ChatRequestRepository chatRequestRepository;
	@Autowired
	private ChatAcceptRepository chatAcceptRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChatRequestService chatRequestService;

	@Override
	public List<ChatAccept> getAllRequests(String email) {
		List<ChatAccept> getlist=chatAcceptRepository.findByUsersEmail(email);
		return getlist;
	}



	@Override
	public void saveAcceptRequsts(ChatRequest chatRequest) {
		ChatAccept chatAcceptt= chatAcceptRepository.findByAcceptEmailAndUsersEmail(chatRequest.getUsers().getEmail(), chatRequest.getRequestEmail());
		if(chatAcceptt==null) {
			Users user=userRepository.findByEmail(chatRequest.getRequestEmail());
			ChatAccept chatAccept= new ChatAccept();
			chatAccept.setAcceptEmail(chatRequest.getUsers().getEmail());
			chatAccept.setUsers(user);
			chatAccept.setAcceptStatus(false);
			chatAccept.setCancelStatus(false);
			chatAccept.setUserName(chatRequest.getUsers().getFirstName()+chatRequest.getUsers().getLastName());
			chatAcceptRepository.save(chatAccept);
		}
		
	}



	@Override
	public List<ChatAccept> deleteAcceptRequest(String acceptEmail, String email) {
		ChatAccept chatAcceptt= chatAcceptRepository.findByAcceptEmailAndUsersEmail(acceptEmail,email);
		if(chatAcceptt!=null) {
			chatAcceptRepository.delete(chatAcceptt);
		}
		ChatRequest chatRequest =chatRequestRepository.findByRequestEmailAndUsersEmail(email,acceptEmail);	
		if(chatRequest!=null) {
			chatRequestRepository.delete(chatRequest);
		}
		return getAllRequests(email);
	}

}
