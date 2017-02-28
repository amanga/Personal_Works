package com.contact.exercise.service;

import java.util.List;

import com.contact.exercise.domain.ContactInformation;

public interface ContactInformationService {

	public List<ContactInformation> get() throws Exception;
	public boolean save(ContactInformation contactInfo) throws Exception;
	public boolean delete(ContactInformation contactInfo) throws Exception;
	public boolean update(ContactInformation contactInfo) throws Exception;
}
