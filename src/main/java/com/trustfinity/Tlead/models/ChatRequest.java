package com.trustfinity.Tlead.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "frdRequest")
public class ChatRequest {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 private String requestEmail;
	 private String userName;
	 private boolean requestStatus;
	 private boolean cancelStatus;
	 @ManyToOne(fetch=FetchType.EAGER)
	 @JoinColumn(name="email")
	 private Users users;
	 
	public ChatRequest() {
		
		
	}

	
	public String getRequestEmail() {
		return requestEmail;
	}


	public void setRequestEmail(String requestEmail) {
		this.requestEmail = requestEmail;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public boolean isRequestStatus() {
		return requestStatus;
	}


	public void setRequestStatus(boolean requestStatus) {
		this.requestStatus = requestStatus;
	}


	public boolean isCancelStatus() {
		return cancelStatus;
	}


	public void setCancelStatus(boolean cancelStatus) {
		this.cancelStatus = cancelStatus;
	}


	public Users getUsers() {
		return users;
	}

	@JsonIgnore
	public void setUsers(Users users) {
		this.users = users;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (cancelStatus ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((requestEmail == null) ? 0 : requestEmail.hashCode());
		result = prime * result + (requestStatus ? 1231 : 1237);
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatRequest other = (ChatRequest) obj;
		if (cancelStatus != other.cancelStatus)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (requestEmail == null) {
			if (other.requestEmail != null)
				return false;
		} else if (!requestEmail.equals(other.requestEmail))
			return false;
		if (requestStatus != other.requestStatus)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
	
	 
}
