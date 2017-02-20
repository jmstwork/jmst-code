package com.founder.fmdm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.fmdm.dao.MqMessageLibDao;
import com.founder.fmdm.entity.MqMessageLib;

@Service
public class MqRecieveServiceImpl implements MqRecieveService {

	@Autowired
	MqMessageLibDao mqMessageLibDao;

	@Override
	public int insertMqMessageLib(MqMessageLib entity) {
		return mqMessageLibDao.insertMqMessageLib(entity);
	}

	@Override
	public int updateMqMessageLib(MqMessageLib entity) {
		return mqMessageLibDao.updateMqMessageLib(entity);
	}

	@Override
	public List<MqMessageLib> selectBySendFlg(Integer sendFlg) {
		return mqMessageLibDao.selectBySendFlg(sendFlg);
	}

}
