package com.trustfinity.Tlead.Service;

import java.util.List;

import com.trustfinity.Tlead.models.ChatModel;
import com.trustfinity.Tlead.models.MsgModel;

public interface MsgService {
	
	public List<MsgModel> getChatsById(String chatId);
	public void saveMsgModel(ChatModel chatModel);
}
