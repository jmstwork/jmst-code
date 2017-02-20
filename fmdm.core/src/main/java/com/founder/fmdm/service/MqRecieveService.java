package com.founder.fmdm.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.founder.fmdm.entity.MqMessageLib;

public interface MqRecieveService {

	/**
	 * 插入接收到的消息
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional
	int insertMqMessageLib(MqMessageLib entity);

	/**
	 * 更新接收到消息处理状态
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional
	int updateMqMessageLib(MqMessageLib entity);
	
	/**
	 * 根据发送状态
	 * @param sendFlg
	 * @return
	 */
	List<MqMessageLib> selectBySendFlg(Integer sendFlg);
	
	

}
