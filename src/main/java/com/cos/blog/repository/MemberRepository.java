package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cos.blog.model.Member;

// DAO
// 자동으로 bean등록이 된다.
// @Repository 생략 가능
public interface MemberRepository extends JpaRepository<Member, Integer> {
	Optional<Member> findByUsername(String username);
	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
	
	@Query(value="SELECT * FROM members WHERE id LIKE %?%",nativeQuery = true)
	Page<Member> findByIdContaining(String keyword,Pageable pageable); // @Query 사용해야 함.
	
	Page<Member> findByUsernameContaining(String keyword,Pageable pageable);
	Page<Member> findByEmailContaining(String keyword,Pageable pageable);
	
	@Query(value="SELECT * FROM members m WHERE m.id LIKE %?1% OR m.username LIKE %?2% OR m.email LIKE %?3% ",nativeQuery = true)
	Page<Member> findByIdContainingOrUsernameContainingOrEmailContaining(String keyword1,String keyword2,String keyword3,Pageable pageable);
}

// JPA Naming 전략
//SELECT * FROM user WHERE username = ? AND password = ?;
//User findByUsernameAndPassword(String username,String password);

/*
 * @Query(value =
 * "SELECT * FROM user WHERE username = ? AND password = ?",nativeQuery = true)
 * User login(String username,String password);
 */
