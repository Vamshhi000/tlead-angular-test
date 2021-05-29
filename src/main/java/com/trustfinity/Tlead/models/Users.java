package com.trustfinity.Tlead.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
	@Id
	private String email;

	private String firstName;

	private String lastName;
	private String password;

	private String phoneNumber;

	private String reenterPassword;

	private String myRefId;

	private String referalId;
	private String passtoken;
	@Column(nullable = true)
	private long linkTime;
	

	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user")
    private Payments payments;
	
	public Users() {
		
		
	}
	


	public long getLinkTime() {
		return linkTime;
	}



	public void setLinkTime(long linkTime) {
		this.linkTime = linkTime;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getReenterPassword() {
		return reenterPassword;
	}

	public void setReenterPassword(String reenterPassword) {
		this.reenterPassword = reenterPassword;
	}

	public String getPasstoken() {
		return passtoken;
	}

	public void setPasstoken(String passtoken) {
		this.passtoken = passtoken;
	}



	public String getMyRefId() {
		return myRefId;
	}



	public void setMyRefId(String myRefId) {
		this.myRefId = myRefId;
	}



	public String getReferalId() {
		return referalId;
	}



	public void setReferalId(String referalId) {
		this.referalId = referalId;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((myRefId == null) ? 0 : myRefId.hashCode());
		result = prime * result + ((passtoken == null) ? 0 : passtoken.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((reenterPassword == null) ? 0 : reenterPassword.hashCode());
		result = prime * result + ((referalId == null) ? 0 : referalId.hashCode());
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
		Users other = (Users) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (myRefId == null) {
			if (other.myRefId != null)
				return false;
		} else if (!myRefId.equals(other.myRefId))
			return false;
		if (passtoken == null) {
			if (other.passtoken != null)
				return false;
		} else if (!passtoken.equals(other.passtoken))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (reenterPassword == null) {
			if (other.reenterPassword != null)
				return false;
		} else if (!reenterPassword.equals(other.reenterPassword))
			return false;
		if (referalId == null) {
			if (other.referalId != null)
				return false;
		} else if (!referalId.equals(other.referalId))
			return false;
		return true;
	}






	
}
