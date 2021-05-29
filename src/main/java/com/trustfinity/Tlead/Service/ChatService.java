package com.trustfinity.Tlead.Service;

import java.util.List;

import com.trustfinity.Tlead.models.ChatAccept;
import com.trustfinity.Tlead.models.ChatModel;
import com.trustfinity.Tlead.models.Users;

public interface ChatService {
	
	public void saveFriends(ChatAccept chatAccept,String email);
	
	public List<ChatModel> getAllFrnds(String email);

}
