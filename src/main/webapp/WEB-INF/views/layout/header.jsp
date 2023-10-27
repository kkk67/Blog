<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<title>블로그</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
	 <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<style>
 	.navbar{
		position: sticky;
		top: 0;
		left: 0;
		right: 0;
			z-index: 100;
			height: 60px;
	} 
</style>
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark"  style="height: auto;;">
		<a class="navbar-brand" href="/"> 홈 </a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<c:choose>
				<c:when test="${empty principal}">
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link"
							href="/auth/loginForm">로그인</a></li>
						<li class="nav-item"><a class="nav-link"
							href="/auth/joinForm">회원가입</a></li>
					</ul>
				</c:when>
				<c:when test="${principal.member.role eq 'ADMIN'}">
						<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link"
							href="/board/saveForm">글쓰기</a></li>
						<li class="nav-item"><a class="nav-link"
							href="/auth/users">회원관리</a></li>
						<li class="nav-item"><a class="nav-link"
							href="/user/logout">로그아웃</a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${empty principal.member.oauth }">
							<ul class="navbar-nav">
								<li class="nav-item"><a class="nav-link"
									href="/board/saveForm">글쓰기</a></li>
								<li class="nav-item"><a class="nav-link"
									href="/auth/ConfirmForm">회원정보</a></li>
								<li class="nav-item"><a class="nav-link"
									href="/user/logout">로그아웃</a></li>
							</ul>						
						</c:when>
						<c:otherwise>
							<ul class="navbar-nav">
								<li class="nav-item"><a class="nav-link"
									href="/board/saveForm">글쓰기</a></li>
								<li class="nav-item"><a class="nav-link"
									href="/auth/updateForm">회원정보</a></li>
								<li class="nav-item"><a class="nav-link"
									href="/user/logout">로그아웃</a></li>
							</ul>		
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</div>
				  <form action="/search" method="GET" class="form-inline justify-content-end " role="search" style="width:auto;">
	        			<input type="search" name="keyword" class="form-control" id="keyword" placeholder="search">
	       				 <button class="btn btn-outline-success">Search</button>
						<!-- <button class="btn btn-success bi bi-search"></button> --> <!-- 부트스트랩 아이콘을 이용한 검색버튼 -->
					</form>
	</nav>
	<br/>
<%-- 	<%
		String strReferer = request.getHeader("referer");

	if(strReferer == null){
	%>
	<script>
		alert("URL을 직접 입력해서 접근하셨습니다. \n정상적인 경로를 통해 다시 접근해주세요.");
		document.location.href="/";
	</script>
	<%
	return;
	}
	%> --%>
</body>