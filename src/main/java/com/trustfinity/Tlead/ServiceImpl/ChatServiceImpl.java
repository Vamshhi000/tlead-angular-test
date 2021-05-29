package com.trustfinity.Tlead.ServiceImpl;

import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trustfinity.Tlead.Repository.ChatRepository;
import com.trustfinity.Tlead.Repository.UserRepository;
import com.trustfinity.Tlead.Service.ChatAcceptService;
import com.trustfinity.Tlead.Service.ChatService;
import com.trustfinity.Tlead.models.ChatAccept;
import com.trustfinity.Tlead.models.ChatModel;
import com.trustfinity.Tlead.models.Users;
@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private ChatAcceptService chatAcceptService;

	@Override
	public void saveFriends(ChatAccept chatAccept,String email) {

		Users myModel=userRepository.findByEmail(email);
		
		Users frndModel=userRepository.findByEmail(chatAccept.getAcceptEmail());
        String chatId =String.format("%s_%s", myModel.getMyRefId(), frndModel.getMyRefId());
        Date date= new Date();
   	 	long time = date.getTime();
   	 	Timestamp tss = new Timestamp(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String ts= formatter.format(tss);

		saveMyModel(myModel,frndModel,chatAccept,chatId,ts);
		savefrndModel(myModel, frndModel, chatAccept,email,chatId,ts);
		chatAcceptService.deleteAcceptRequest(chatAccept.getAcceptEmail(), email);
	}
	
	public void saveMyModel(Users myModel,Users frndModel,ChatAccept chatAccept,String chatId,String ts) {
		ChatModel chatModel= new ChatModel();
		chatModel.setChatId(chatId);
		chatModel.setTimestamp(ts);
		chatModel.setChatEmail(chatAccept.getAcceptEmail());
		chatModel.setRecipientId(frndModel.getMyRefId());
		chatModel.setRecipientName(frndModel.getFirstName()+" "+frndModel.getLastName());
		chatModel.setSenderId(myModel.getMyRefId());
		chatModel.setSenderName(myModel.getFirstName()+" "+myModel.getLastName());
		chatModel.setUsers(myModel);
		
		chatRepository.save(chatModel);
		
	}
	
	public void savefrndModel(Users myModel,Users frndModel,ChatAccept chatAccept,String email,String chatId,String ts) {
		
		ChatModel chatModel= new ChatModel();
		chatModel.setTimestamp(ts);
		chatModel.setChatId(chatId);
		chatModel.setChatEmail(email);
		chatModel.setRecipientId(myModel.getMyRefId());
		chatModel.setRecipientName(myModel.getFirstName()+" "+myModel.getLastName());
		chatModel.setSenderId(frndModel.getMyRefId());
		chatModel.setSenderName(frndModel.getFirstName()+" "+frndModel.getLastName());
		chatModel.setUsers(frndModel);
		chatRepository.save(chatModel);
	}

	@Override
	public List<ChatModel> getAllFrnds(String email) {
		
		return chatRepository.findByUsersEmail(email);
	}

}
