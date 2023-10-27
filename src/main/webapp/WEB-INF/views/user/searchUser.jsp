<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="/css/user.css">
<script>
	if(${principal.member.role eq 'ADMIN'}){
		
	}
	else{
			alert("잘못된 접근입니다.");
			location.href="/";		
	}
</script>
<div class="container">
	  <h2>회원 검색결과</h2>
	  <p>하단에 있는 회원 리스트에서 삭제 수정이 가능합니다.</p> 
	<div class="wrapper" style="width:auto;">
	  				<form action="/searchUser" method="GET" class="form-inline justify-content-end " role="search"  >
	  						<select id="selectBox"  name="type" class="selectpicker" style="margin: 0; float: left;"  >
							    <option value="ALL">전체 검색</option>
							    <option value="ID">회원번호</option>
							    <option value="USERNAME">아이디</option>
							    <option value="EMAIL">이메일</option>
			 				 </select>
	  					<div style=" float: right;">
				        		<input type="search" name="keyword" class="form-control" id="keyword" placeholder="search"  style="margin: 0; float: left;" >
		       				 	<button class="btn btn-outline-success"  style="margin: 0; float: left;">Search</button>		 		
	  					</div>
							<!-- <button class="btn btn-success bi bi-search"></button> --> <!-- 부트스트랩 아이콘을 이용한 검색버튼 -->
						</form>       
						<br/>     
	</div>
  <table class="table table-hover">
    <thead>
      <tr>
        <th style="text-align: center;">회원번호</th>
        <th style="text-align: center;">아이디</th>
        <th style="text-align: center;">이메일</th>
         <th></th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users.content }">
      <tr>
        <td style="text-align: center;">${user.id }</td>
        <td style="text-align: center;">${user.username }</td>
        <td style="text-align: center;">${user.email }</td>
         <td style="text-align:center;"><a href="/auth/updateForm/${user.id }" class="btn btn-primary">수정</a>
         <Button class="btn btn-danger" onclick="index.delete(${user.id})">삭제</Button></td>
      </tr>    
    </c:forEach>
    </tbody>
  </table>
  	<ul class="pagination justify-content-center" >
  	<c:choose> 
  		<c:when test="${users.first }">
		  <li class="page-item disabled" ><a class="page-link" href="#">Previous</a></li>
  		</c:when>
  		<c:otherwise>
  			<li class="page-item" ><a class="page-link" href="?type=${type}&keyword=${keyword}&page= ${users.number}">Previous</a></li>
  		</c:otherwise>
  	</c:choose>
  	
  	<c:forEach var="page" begin="1" end="${Total}">
  		<c:choose>
  			<c:when test="${users.number eq  page-1}">
  				<li class="page-item active"><a class="page-link" href="?page=${page}">${page}</a></li>
  			</c:when>
  			<c:otherwise>
  				<li class="page-item"><a class="page-link" href="?type=${type}&keyword=${keyword}&page=${page}">${page}</a></li>
  			</c:otherwise>
  		</c:choose>
  	</c:forEach>
  	
  	<c:choose>
  		<c:when test="${users.last }">
  		  <li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
  		</c:when>
  		<c:otherwise>
  		  <li class="page-item"><a class="page-link" href="?type=${type}&keyword=${keyword}&page=${users.number+2 }">Next</a></li>
  		</c:otherwise>
  	</c:choose>
	</ul>
</div>
<script src ="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>