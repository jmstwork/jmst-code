package com.founder.core.service;

import java.util.List;

import com.founder.core.service.dto.MessageResource;

public interface MessageResourceService 
{

	List<MessageResource> loadAllMessages();

}
