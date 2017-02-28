package com.contact.exercise.service;

import com.contact.exercise.domain.CandidateResume;
import com.contact.exercise.exceptions.ServiceException;

public interface CandidateInformationService {

	public boolean save(CandidateResume candidateResume) throws ServiceException;
}
