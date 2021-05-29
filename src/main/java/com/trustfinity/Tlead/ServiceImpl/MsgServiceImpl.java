package com.trustfinity.Tlead.ServiceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trustfinity.Tlead.Repository.MsgRepository;
import com.trustfinity.Tlead.Repository.UserRepository;
import com.trustfinity.Tlead.Service.MsgService;
import com.trustfinity.Tlead.models.ChatModel;
import com.trustfinity.Tlead.models.MessageStatus;
import com.trustfinity.Tlead.models.MsgModel;
import com.trustfinity.Tlead.models.Users;
@Service
public class MsgServiceImpl implements MsgService{
	
	@Autowired
	private MsgRepository msgRepository;
	
	@Autowired UserRepository userRepository;

	@Override
	public List<MsgModel> getChatsById(String chatId) {
	
		return msgRepository.findAllByOrder(chatId);
	}

	@Override
	public void saveMsgModel(ChatModel chatModel) {
		
		Users users=userRepository.findByMyRefId(chatModel.getSenderId());
   	 	Date date= new Date();
   	 	long time = date.getTime();
   	 	Timestamp ts = new Timestamp(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String formattert= formatter.format(ts);
		MsgModel msgModel = new MsgModel();
		msgModel.setChatEmail(chatModel.getChatEmail());
		msgModel.setChatId(chatModel.getChatId());
		msgModel.setMsg(chatModel.getMsg());
		msgModel.setRecipientId(chatModel.getRecipientId());
		msgModel.setRecipientName(chatModel.getRecipientName());
		msgModel.setSenderId(chatModel.getSenderId());
		msgModel.setSenderName(chatModel.getSenderName());
		msgModel.setTimestamp(formattert);
		msgModel.setStatus(MessageStatus.DELIVERED);
		
		msgModel.setEmail(users.getEmail());
		
		msgRepository.save(msgModel);
		
	}

}
