package com.contact.exercise.domain;

import java.io.InputStream;
import java.util.List;

public class CandidateResume {

	private String id;
	private String firstName;
	private String lastName;
	private String clientId;
	private String email;
	private String fileExtension;
	private InputStream inputStream;
	private List<Skill> skills;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public String toString() {
		return "CandidateResume [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", clientId=" + clientId
				+ ", email=" + email + ", fileExtension=" + fileExtension
				+ ", skills=" + skills + "]";
	}

}
