package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

//서비스는 하나의 트랜잭션(작업 단위)면 상관이 없지만 두 개 이상의 트랜잭션을 한번에 수행하기 위하여 사용한다.
//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class BoardService {

		@Autowired
		private BoardRepository boardRepository;
		@Autowired
		private ReplyRepository replyRepository;
		@Autowired
		private UserRepository userRepository;
		
		@Transactional
		public void 글쓰기(Board board,User user) {
				board.setCount(0);
				board.setUser(user);
				boardRepository.save(board);
		}
		@Transactional(readOnly = true)
		public Page<Board> 글목록(Pageable pageable){
			return boardRepository.findAll(pageable);
		};
		@Transactional(readOnly = true)
		public Board 글상세보기(int id) {
			return boardRepository.findById(id)
					.orElseThrow(()->{
						return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
					});
		}
		
		@Transactional
		public void 삭제하기(int id) {
			System.out.println("글 삭제하기 : " + id);
				boardRepository.deleteById(id);
		}
		@Transactional
		public void 글수정하기(int id,Board requestBoard) {
			Board board = boardRepository.findById(id)
					.orElseThrow(()->{
						return new IllegalArgumentException("글 수정 실패: 아이디를 찾을 수 없습니다.");
					}); // 영속화 완료
			board.setTitle(requestBoard.getTitle());
			board.setContent(requestBoard.getContent());
			// 해당 함수로 종료시 ( service 종료될 때) 알아서 업데이트 : 더티채킹 -> 자동 업데이트가 됨 flush
		}
		
		@Transactional
		public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
			
			User user = userRepository.findById(replySaveRequestDto.getUserid()).orElseThrow(()->{
				return new IllegalArgumentException("댓글 쓰기 실패: 작성자를 찾을 수 없습니다.");
			});
			Board board = boardRepository.findById(replySaveRequestDto.getBoardid()).orElseThrow(()->{
				return new IllegalArgumentException("댓글 쓰기 실패: 게시글 id를 찾을 수 없습니다.");
			});
			
			Reply reply = Reply.builder()
					.user(user)
					.board(board)
					.content(replySaveRequestDto.getContent())
					.build();
			replyRepository.save(reply);
		}
		
		@Transactional
		public void 댓글삭제하기(int id) {
			replyRepository.deleteById(id);
		}
		
}
