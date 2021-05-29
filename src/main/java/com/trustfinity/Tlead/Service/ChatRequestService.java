package com.trustfinity.Tlead.Service;

import java.util.List;

import com.trustfinity.Tlead.models.ChatRequest;


public interface ChatRequestService {
	public List<ChatRequest> searchResult(String name,String email);
	
	public List<ChatRequest> saveRequests(ChatRequest chatrequest,String email,String sKey);
	
	public List<ChatRequest> cancelChatRequest(String requestEmail,String sKey,String email);
}
