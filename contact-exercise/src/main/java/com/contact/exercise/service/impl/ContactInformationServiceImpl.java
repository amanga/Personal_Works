package com.contact.exercise.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.contact.exercise.domain.Address;
import com.contact.exercise.domain.ContactInformation;
import com.contact.exercise.service.ContactInformationService;

@Service
public class ContactInformationServiceImpl implements ContactInformationService {

	private static Logger logger = LoggerFactory
			.getLogger(ContactInformationServiceImpl.class);
	
	private List<ContactInformation> mockData;

	@PostConstruct
	public void init(){
		mockData = mockContacts();
	}
	public List<ContactInformation> get() throws Exception {
		logger.debug("get contact information");
		return mockData;
	}

	public boolean save(ContactInformation contactInfo) throws Exception {
		logger.debug("save contact information");
		logger.debug(contactInfo.toString());
		int size = this.mockData.size();
		contactInfo.setId((long)size+1);
		this.mockData.add(contactInfo);
		return true;
	}

	public boolean delete(ContactInformation contactInfo) throws Exception {
		logger.debug("delete contact information");
		return false;
	}

	public boolean update(ContactInformation contactInfo) throws Exception {
		logger.debug("update contact information");
		Iterator<ContactInformation> itr = this.mockData.iterator();
		while(itr.hasNext()){
			ContactInformation contactInformation = itr.next();
			if(contactInfo.getId()==(contactInformation.getId())){
				logger.debug(">>>>>inside id is equals id");
				logger.debug(">>>>>Remove initiated");
				this.mockData.remove(contactInformation);
				logger.debug(">>>>>Remove done");
				this.mockData.add(contactInfo);
				break;
			}
		}
		return true;
	}

	private List<ContactInformation> mockContacts() {
		List<ContactInformation> contactInfos = new ArrayList<ContactInformation>();

		for (int i = 0; i < 9; i++) {
			ContactInformation contactInfo = new ContactInformation();
			contactInfo.setId((long) i);
			contactInfo.setFirstName("First_" + i);
			contactInfo.setLastName("Last_" + i);
			List<Address> addrs = new ArrayList<Address>();

			if (i % 2 == 0)
				addrs.add(getAddress(i));

			contactInfo.setAdress(addrs);
			contactInfos.add(contactInfo);
		}

		return contactInfos;

	}

	private Address getAddress(int i) {
		Address addr = new Address();
		addr.setId((long) i);
		addr.setStreet("Street " + i);
		addr.setCity("City " + i);
		addr.setCountry("Country " + i);

		return addr;
	}

}
