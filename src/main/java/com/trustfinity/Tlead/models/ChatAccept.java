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
@Table(name = "chatAccept")
public class ChatAccept {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 private String acceptEmail;
	 private String userName;
	 private boolean acceptStatus;
	 private boolean cancelStatus;
	 @ManyToOne(fetch=FetchType.LAZY)
	 @JoinColumn(name="email")
	 private Users users;
	 
	 
	 public ChatAccept() {
		 
	 }


	public String getAcceptEmail() {
		return acceptEmail;
	}


	public void setAcceptEmail(String acceptEmail) {
		this.acceptEmail = acceptEmail;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public boolean isAcceptStatus() {
		return acceptStatus;
	}


	public void setAcceptStatus(boolean acceptStatus) {
		this.acceptStatus = acceptStatus;
	}


	public boolean isCancelStatus() {
		return cancelStatus;
	}


	public void setCancelStatus(boolean cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	@JsonIgnore 
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
		result = prime * result + ((acceptEmail == null) ? 0 : acceptEmail.hashCode());
		result = prime * result + (acceptStatus ? 1231 : 1237);
		result = prime * result + (cancelStatus ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ChatAccept other = (ChatAccept) obj;
		if (acceptEmail == null) {
			if (other.acceptEmail != null)
				return false;
		} else if (!acceptEmail.equals(other.acceptEmail))
			return false;
		if (acceptStatus != other.acceptStatus)
			return false;
		if (cancelStatus != other.cancelStatus)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
