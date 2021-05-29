package com.trustfinity.Tlead.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trustfinity.Tlead.models.MsgModel;
import com.trustfinity.Tlead.models.Users;
@Repository
public interface MsgRepository extends JpaRepository<MsgModel, Long>{
	
	
	public List<MsgModel> findByChatId(String chatId);
	


	@Query(value="select * from msg_model where chat_id=:chatId order by timestamp asc;", nativeQuery = true)
	public List<MsgModel> findAllByOrder(String chatId);
}
