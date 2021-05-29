package com.trustfinity.Tlead.Service;

import java.util.List;

import com.trustfinity.Tlead.models.EditUsers;
import com.trustfinity.Tlead.models.ResponceDto;


public interface AdminService {
	
	public List<ResponceDto> getAllUsers(Integer pageNo, Integer pageSize);
	
	public void editUsers(EditUsers editUsers);
	
	public void deleteUsers(EditUsers editUsers);

}
