<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>

<div class="container">

	<c:forEach var="board" items="${boards.content }">
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4>
				<a href="/board/${board.id}" class="btn btn-primary">상세 보기</a>
			</div>
		</div>
	</c:forEach>
	<ul class="pagination  justify-content-center">
		<c:choose>
			<c:when test="${boards.first }">
					<li class="page-item disabled"><a class="page-link"
			href="">Previous</a></li>
			</c:when>
			<c:otherwise>
		<li class="page-item"><a class="page-link"
			href="?page=${boards.number}">Previous</a></li>
			</c:otherwise>
		</c:choose>
		<c:forEach var="page" begin="1" end="${Total}">
				<c:choose>
				<c:when test="${boards.number eq page-1}">
					<li class="page-item active"><a class="page-link" href="?page=${page}">${page}</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="?page=${page}">${page}</a></li>
				</c:otherwise>
				</c:choose>
		</c:forEach>
		
		<c:choose>
			<c:when test="${boards.last }">
				<li class="page-item disabled"><a class="page-link"
			href="">Next</a></li>
			</c:when>
			<c:otherwise>
			<li class="page-item"><a class="page-link"
			href="?page=${boards.number+2}">Next</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>
<%@ include file="layout/footer.jsp"%>