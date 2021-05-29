package com.trustfinity.Tlead.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trustfinity.Tlead.Repository.MsgRepository;
import com.trustfinity.Tlead.Service.MsgService;
import com.trustfinity.Tlead.models.ChatModel;
import com.trustfinity.Tlead.models.MsgModel;

@Controller
@RequestMapping(value="/msg")
@CrossOrigin(origins = "*")
public class MsgController {
	
	 @Autowired private SimpMessagingTemplate messagingTemplate;

	 @Autowired private MsgService msgService;
	 
	 
	    @GetMapping("getChats/{chatId}")
	    public ResponseEntity<List<MsgModel>> getChatsbyId(@PathVariable String chatId){
	    	List<MsgModel> chats=msgService.getChatsById(chatId);
	      	return new ResponseEntity<List<MsgModel>>(chats,HttpStatus.OK);
	    }
	 
	    @MessageMapping("/hello")
	    public void greeting(@Payload ChatModel chatModel) throws Exception {
	        Thread.sleep(1000); // simulated delay

	        try {
		        if(chatModel.getMsg()!=null) {
			        
			        ChatModel greeting = new ChatModel();
			        		greeting.setMsg(chatModel.getMsg());
			        		greeting.setChatEmail(chatModel.getChatEmail());
			        		greeting.setRecipientName(chatModel.getRecipientName());
			        		greeting.setSenderName(chatModel.getSenderName());
			        		msgService.saveMsgModel(chatModel);
			        		messagingTemplate.convertAndSendToUser(
			        		chatModel.getRecipientId(),"/queue/messages",greeting
			               );
			        		messagingTemplate.convertAndSendToUser(
			    	        		chatModel.getSenderId(),"/queue/messages",greeting
			    	               );
			        		
			        }
			} catch (Exception e) {
				System.out.println(e);
			}
	    }
	    



}
