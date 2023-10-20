<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
  <h2>회원 관리 페이지</h2>
  <p>하단에 있는 회원 리스트에서 삭제 수정이 가능합니다.</p> 
  					
  				<form action="/search" method="GET" class="form-inline justify-content-end " role="search">
  						<select class="selectpicker">
						    <option value="ALL">전체 검색</option>
						    <option value="ID">회원번호</option>
						    <option value="USERNAME">아이디</option>
						    <option value="EMAIL">이메일</option>
		 				 </select>
			        		<input type="search" name="keyword" class="form-control" id="keyword" placeholder="search">
	       				 <button class="btn btn-outline-success">Search</button>
						<!-- <button class="btn btn-success bi bi-search"></button> --> <!-- 부트스트랩 아이콘을 이용한 검색버튼 -->
					</form>       
  <table class="table table-hover">
    <thead>
      <tr>
        <th>회원번호</th>
        <th>아이디</th>
        <th>이메일</th>
         <th>수정</th>
          <th>삭제</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users.content }">
      <tr>
        <td>${user.id }</td>
        <td>${user.username }</td>
        <td>${user.email }</td>
         <td><a href="/auth/updateForm/${user.id }" class="btn btn-primary">수정</a></td>
        <td><Button class="btn btn-danger" onclick="index.delete(${user.id})">삭제</Button></td>
      </tr>    
    </c:forEach>
      
    </tbody>
  </table>
</div>
<script src ="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>