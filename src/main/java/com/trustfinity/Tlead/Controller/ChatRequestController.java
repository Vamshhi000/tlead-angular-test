package com.trustfinity.Tlead.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trustfinity.Tlead.Repository.ChatRepository;
import com.trustfinity.Tlead.Repository.ChatRequestRepository;
import com.trustfinity.Tlead.Repository.UserRepository;
import com.trustfinity.Tlead.Service.ChatRequestService;
import com.trustfinity.Tlead.models.ChatModel;
import com.trustfinity.Tlead.models.ChatRequest;
import com.trustfinity.Tlead.models.MsgModel;
import com.trustfinity.Tlead.models.ResponceDto;
import com.trustfinity.Tlead.models.Users;

@Controller
@RequestMapping(value="/chatRequest")
@CrossOrigin(origins = "*")
public class ChatRequestController {
	
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ChatRequestRepository chatRequestRepository;
	@Autowired
	private ChatRequestService chatRequestService;
	
	@Autowired
	private ChatRepository chatRepository;
	
    @GetMapping("searchResult/{name}")
    public ResponseEntity<List<ChatRequest>> searchResult(@PathVariable String name,@RequestParam("email") String email){
		List<ChatRequest> reqList= new ArrayList<ChatRequest>();
    	Users usersEm=userRepository.findByEmail(name);
    	Users usersPh=userRepository.findByPhoneNumberr(name);


    		
    		if(usersEm!=null) {
        		if(usersEm.getEmail().equals(email)) {

        					reqList.add(null);
        				 	return new ResponseEntity<List<ChatRequest>>(reqList,HttpStatus.OK);
        		}else {
        	    	List<ChatRequest> userss=chatRequestService.searchResult(name,email);
        	    	List<ChatRequest> searchfilterDto=searchfilter(userss,name,email);
        	    	return new ResponseEntity<List<ChatRequest>>(searchfilterDto,HttpStatus.OK);
        		}
        	}
     	
        	if(usersPh!=null) {
        		
        		if(usersPh.getEmail().equals(email)) {
        			
    				reqList.add(null);
    			 	return new ResponseEntity<List<ChatRequest>>(reqList,HttpStatus.OK);
        		}else {
        	    	List<ChatRequest> userss=chatRequestService.searchResult(name,email);
          	    	List<ChatRequest> searchfilterDto=searchfilter(userss,name,email);
        	    	return new ResponseEntity<List<ChatRequest>>(searchfilterDto,HttpStatus.OK);
        		}
        	}
        	
        	else {
        		
    	    	List<ChatRequest> userss=chatRequestService.searchResult(name,email);
      	    	List<ChatRequest> searchfilterDto=searchfilter(userss,name,email);
    	    	return new ResponseEntity<List<ChatRequest>>(searchfilterDto,HttpStatus.OK);
        	}

        	
        	
//    	}
    
    	
    }
    
    @PostMapping("saveRequests/{sKey}")
    public ResponseEntity<?> saveSendRequest(@RequestBody ChatRequest chatRequest,@RequestParam("email") String email,@PathVariable String sKey) {
    	ChatRequest chatRequestt =chatRequestRepository.findByRequestEmailAndUsersEmail(chatRequest.getRequestEmail(),email);
    	
    	
    	if(chatRequestt==null) {
        	List<ChatRequest> svaedResponce=chatRequestService.saveRequests(chatRequest,email,sKey);
        	return new ResponseEntity<List<ChatRequest>>(svaedResponce,HttpStatus.OK);
    	}else {
    		
    		ResponceDto responceDto = new ResponceDto();
    		responceDto.setMsg("Already Send Request");
    	  	return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
    	}
    }
    
    @PutMapping("cancelRequest/{sKey}")
    public ResponseEntity<?> cancelChatRequest(@RequestBody ChatRequest chatRequest,@RequestParam(name="email") String email,@PathVariable String sKey) {
    	ChatRequest chatRequesttt =chatRequestRepository.findByRequestEmailAndUsersEmail(chatRequest.getRequestEmail(),email);	
    	if(chatRequesttt==null) {
    		ResponceDto responceDto = new ResponceDto();
    		responceDto.setMsg("Already Canceled Request");
    	  	return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);

    	}else {
    	  	List<ChatRequest> cancelResponce=chatRequestService.cancelChatRequest(chatRequest.getRequestEmail(),sKey,email);
        	return new ResponseEntity<List<ChatRequest>>(cancelResponce,HttpStatus.OK);
    	}
    }

 public List<ChatRequest> searchfilter(List<ChatRequest> userss,String name,String email){

	 ChatRequest chatRequest = new ChatRequest();
	 userss.add(null);
	 
	 
	 
	 
	 List<ChatModel> modelss=chatRepository.findByUsersEmail(email);
if(modelss!=null) {
	 for(ChatModel chatmodel:modelss) {
		for(ChatRequest x :userss) {
			
		if(x!=null) {
			if(x.getRequestEmail().equals(chatmodel.getChatEmail())) {
				userss.remove(x);
		}

			}
		}
	 }
}

		Users usersPh=userRepository.findByPhoneNumberr(name); 
			

	if(usersPh!=null) {
		ChatModel chatModel2=chatRepository.findByChatEmailAndUsersEmail(usersPh.getEmail(), email);
		if(chatModel2!=null) {
		
					for(ChatRequest x :userss) {
						
				if(x!=null) {
					if(x.getRequestEmail().equals(chatModel2.getChatEmail())) {
						userss.remove(x);
				}

						}
					}
			
		}
	
}

		if(userss.size()>1) {
			userss.remove(null);
		}
		 return userss;
	 
	 
 }
}
