package com.cos.blog.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

	//리플리 객체가 아니라 하나씩 쪼개서 해야함 객체로 받아서 하는게 있는지는 확인해 봐야함
	//아래는 사용하지 않음
	@Transactional
	@Query(value="INSERT INTO REPLY(content, userId, boardId) VALUES(?1, ?2,?3", nativeQuery = true)
	int insertReply(Reply reply);
}
