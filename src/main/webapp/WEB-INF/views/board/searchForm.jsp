<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<c:forEach var="search" items="${search.content }">
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">${search.title}</h4>
				<a href="/board/${search.id}" class="btn btn-primary">상세 보기</a>
			</div>
		</div>
	</c:forEach>
	<!-- 검색 결과가 없으면 -->
	<c:choose>
		<c:when test="${isEmpty }">
			<br/>
			<h2>입력하신 "${keyword }"  의 대한 검색 결과가 없습니다. </h2>
			<button class="btn btn-secondary"  onclick="history.back()">돌아가기</button>
			<br/><br/>
		</c:when>
		<c:otherwise>
			<ul class="pagination justify-content-center">
	<c:choose>
		<c:when test="${hasPrev eq false }"> <!-- 이전 페이지가 없으면 -->
		  <li class="page-item disabled" ><a class="page-link" href="#" >Previous</a></li>		
		</c:when>
		<c:otherwise>
			  <li class="page-item" ><a class="page-link" href="/search?keyword=${keyword }&page=${previous+1}" >Previous</a></li>		
		</c:otherwise>
	</c:choose>
	
	<c:forEach  var="page"  begin="1"  end="${Total }">
		<li class="page-item"><a class="page-link" href="/search?keyword=${keyword }&page=${page}">${page}</a></li>
	</c:forEach>
	
	<c:choose>
				<c:when test="${hasNext eq false }"> <!-- 다음 페이지가 없으면 -->
		  <li class="page-item disabled" ><a class="page-link" href="#" >Next</a></li>		
		</c:when>
		<c:otherwise>
			  <li class="page-item" ><a class="page-link" href="/search?keyword=${keyword }&page=${next+1}" >Next</a></li>		
		</c:otherwise>
	</c:choose>
	</ul>
		</c:otherwise>
	</c:choose>
</div>
<%@ include file="../layout/footer.jsp"%>