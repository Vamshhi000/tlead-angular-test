package com.trustfinity.Tlead.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.trustfinity.Tlead.Repository.PaymentRepository;
import com.trustfinity.Tlead.Repository.UserRepository;
import com.trustfinity.Tlead.Service.AdminService;
import com.trustfinity.Tlead.models.EditUsers;
import com.trustfinity.Tlead.models.Payments;
import com.trustfinity.Tlead.models.ResponceDto;
import com.trustfinity.Tlead.models.Users;
@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public List<ResponceDto> getAllUsers(Integer pageNo, Integer pageSize) {
		
		List<ResponceDto> usrList=new ArrayList<ResponceDto>();
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Users> users=userRepository.findAll(paging);
		   if(users.hasContent()) {
			   
			   for(Users usr:users) {
				   Payments payments = paymentRepository.findByUserEmail(usr.getEmail());
				   ResponceDto responcedto= new ResponceDto();
				   responcedto.setEmail(usr.getEmail());
				   responcedto.setPhoneNumber(usr.getPhoneNumber());
				   responcedto.setRefId(usr.getReferalId());
				   responcedto.setMyRefId(usr.getMyRefId());
				   responcedto.setUserName(usr.getFirstName()+" "+usr.getLastName());
				 if(payments!=null) {
					 
					 responcedto.setPaidstatus(payments.getStatus());
					 responcedto.setOrderId(payments.getOrderId());
					 responcedto.setPaymentId(payments.getPaymentId());
				 }else {
					 responcedto.setPaidstatus("unpaid");
					 responcedto.setOrderId("no Data");
					 responcedto.setPaymentId("no Data"); 
					 
				 }
				 usrList.add(responcedto);
			   }
			   
			   
	            return usrList;
	        } else {
	            return new ArrayList<ResponceDto>();
	        }
	}

	@Override
	public void editUsers(EditUsers editUsers) {
		Users users=userRepository.findByEmail(editUsers.getEmail());
		
		if(users!=null) {
			users.setEmail(editUsers.getEmail());
			users.setFirstName(editUsers.getFirstName());
			users.setLastName(editUsers.getLastName());
			users.setPhoneNumber(editUsers.getPhoneNumber());
			users.setReferalId(editUsers.getReferalId());
			userRepository.save(users);
		}
		Payments payments = paymentRepository.findByUserEmail(editUsers.getEmail());
		
		if(payments!=null) {
			payments.setOrderId(editUsers.getOrderId());	
			payments.setStatus(editUsers.getStatus());
			
			payments.setPaymentId(editUsers.getPaymentId());
			paymentRepository.save(payments);
		}
	
	}

	@Override
	public void deleteUsers(EditUsers editUsers) {
		
		Users users=userRepository.findByEmail(editUsers.getEmail());
		Payments payments = paymentRepository.findByUserEmail(editUsers.getEmail());
		
		if(payments!=null) {
			paymentRepository.delete(payments);
		}
		
		if(users!=null) {
			userRepository.delete(users);
		}
	}
	
	

}
