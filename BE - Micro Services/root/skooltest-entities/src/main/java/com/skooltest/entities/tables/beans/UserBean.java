package com.skooltest.entities.tables.beans;

import java.io.Serializable;
import java.util.Objects;

public class UserBean implements Serializable {

	private static final long serialVersionUID = -8625791704965693344L;

	private String userName;

	private String firstName;

	private String lastName;

	private Integer studentId;

	private Integer teacherId;

	private String userType;

	private String imageUrl;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "UserBean [userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", studentId="
				+ studentId + ", teacherId=" + teacherId + ", userType=" + userType + ", imageUrl=" + imageUrl + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, imageUrl, lastName, studentId, teacherId, userName, userType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBean other = (UserBean) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(imageUrl, other.imageUrl)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(studentId, other.studentId)
				&& Objects.equals(teacherId, other.teacherId) && Objects.equals(userName, other.userName)
				&& Objects.equals(userType, other.userType);
	}
}
