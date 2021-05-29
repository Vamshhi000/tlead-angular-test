package com.trustfinity.Tlead.Service;

import java.util.List;

import com.trustfinity.Tlead.models.ChatAccept;
import com.trustfinity.Tlead.models.ChatRequest;

public interface ChatAcceptService {
	
	public void saveAcceptRequsts(ChatRequest chatRequest);
	
	public List<ChatAccept> getAllRequests(String email);
	
	public List<ChatAccept> deleteAcceptRequest(String acceptEmail,String email);
	
	

}
