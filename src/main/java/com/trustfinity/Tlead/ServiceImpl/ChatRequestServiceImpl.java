package com.trustfinity.Tlead.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
public class ChatRequestServiceImpl implements ChatRequestService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ChatRequestRepository chatRequestRepository;
	
	@Autowired
	private ChatAcceptRepository chatAcceptRepository;
	
	@Autowired
	private ChatAcceptService chatAcceptService;
	@Override
	public List<ChatRequest> searchResult(String name,String email) {
		
		
		Users user=userRepository.findByEmail(name);
		if(user!=null) {
			List<ChatRequest> reqList= new ArrayList<ChatRequest>();
			ChatRequest chatRequestt =chatRequestRepository.findByRequestEmailAndUsersEmail(name,email);
			
			if(chatRequestt==null) {
				ChatRequest chatRequest= new ChatRequest();
				chatRequest.setRequestEmail(user.getEmail());
				chatRequest.setUserName(user.getFirstName()+" "+user.getLastName());
				chatRequest.setRequestStatus(false);
				chatRequest.setCancelStatus(true);
			
				reqList.add(chatRequest);
				return reqList;
			}else {
				reqList.add(chatRequestt);
				return reqList;
				
			}
			
		}
			

		Users userr=userRepository.findByPhoneNumberr(name);
		if(userr!=null) {
		
			ChatRequest chatRequestt =chatRequestRepository.findByRequestEmailAndUsersEmail(userr.getEmail(),email);	
			List<ChatRequest> reqList= new ArrayList<ChatRequest>();
			if(chatRequestt==null) {
				
				ChatRequest chatRequest= new ChatRequest();
				chatRequest.setRequestEmail(userr.getEmail());
				chatRequest.setUserName(userr.getFirstName()+" "+userr.getLastName());
				chatRequest.setRequestStatus(false);
				chatRequest.setCancelStatus(true);
		
				reqList.add(chatRequest);

				return reqList;
				
			}else {
				reqList.add(chatRequestt);
				return reqList;
				
				
			
				
			}
			
		}
		
			
		if(user==null&userr==null) {
			
				String searchName="%"+name+"%";
				return searchUpdateDto(searchName,email);
			
		}else {
			
			return null;
		}
			
		
		


	}

	@Override
	public List<ChatRequest> saveRequests(ChatRequest chatrequest,String email,String sKey) {
		ChatRequest chatRequest =chatRequestRepository.findByRequestEmailAndUsersEmail(chatrequest.getRequestEmail(),email);
		if(chatRequest==null) {
			Users user=userRepository.findByEmail(email);
			chatrequest.setUsers(user);
			chatrequest.setRequestStatus(true);
			chatrequest.setCancelStatus(false);
			chatRequestRepository.save(chatrequest);
			chatAcceptService.saveAcceptRequsts(chatrequest);
		}
		String searchName="%"+sKey+"%";	
		return searchUpdateDto(searchName,email);

	}

	public List<ChatRequest> SearchResponceDto(String searchName,String email){
		
	List<ChatRequest> chatResponceList= new ArrayList<ChatRequest>();
	List<Users> users = userRepository.findByFirstNameLike(searchName);	
	List<Users> userss =users.stream().filter((a)->!a.getEmail().equals(email)).collect(Collectors.toList());
		for(Users user:userss) {
			ChatRequest chatRequest= new ChatRequest();
			chatRequest.setRequestEmail(user.getEmail());
			chatRequest.setUserName(user.getFirstName()+" "+user.getLastName());
			chatRequest.setRequestStatus(false);
			chatRequest.setCancelStatus(true);
		
			chatResponceList.add(chatRequest);
		}
		return chatResponceList;
		
		
	}
	
	public List<ChatRequest> searchUpdateDto(String sKey,String email){
		
		List<ChatRequest> chatRequestList= chatRequestRepository.findByUsersEmail(email);
		
		List<ChatRequest> chatResponceList=SearchResponceDto(sKey,email);
		
		
		for(ChatRequest chResChmdl:chatResponceList) {
			
			for(ChatRequest chReqUsr:chatRequestList) {
				
				if(chResChmdl.getRequestEmail().equals(chReqUsr.getRequestEmail())) {
					chResChmdl.setRequestStatus(chReqUsr.isRequestStatus());
					chResChmdl.setCancelStatus(chReqUsr.isCancelStatus());
					
				}
			}
			
		}
		
		return chatResponceList;
	}

	@Override
	public List<ChatRequest> cancelChatRequest(String requestEmail,String sKey,String email) {
		ChatRequest chatRequest =chatRequestRepository.findByRequestEmailAndUsersEmail(requestEmail,email);	
		chatRequestRepository.delete(chatRequest);
			chatAcceptService.deleteAcceptRequest(email, requestEmail);
		String searchName="%"+sKey+"%";	
		return searchUpdateDto(searchName,email);
	}

}
