package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="board")
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 100,nullable = false)
	private String title;
	
	@Lob //대용량 데이터
	private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨.
	
	private int count; //조회수
	
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER) // mappedBy 연관관계의 주인이 아니다 (난 FK가 아니에요) DB에 컬럼을 만들지 마세요 
	@JsonIgnoreProperties("board")
	@OrderBy("id desc")
	//값은 연관관계 주인의 변수명 Board board
	//EAGER은 홈페이지 로딩과 함께 바로 값을 가져오는 전략 , LAZY는 바로는 안 가져오지만 다른 버튼을 눌러서 댓글이 나오게하면 LAZY전략
	private List <Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	@ManyToOne(fetch = FetchType.EAGER) // Many = board,  One= User 한명의 유저는 여러개의 게시글을 작성 가능
	@JoinColumn(name="memberid")
	private Member member;  //DB는 오브젝트를 지정할 수 없다. FK , 자바는 오브젝트 저장 가능
}
