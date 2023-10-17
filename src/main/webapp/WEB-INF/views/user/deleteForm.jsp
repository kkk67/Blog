<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<div class="form-group">
			<label for="password">회원탈퇴</label> <input type="password"
				class="form-control"   placeholder="Enter password" id="password">
		<%-- 	<button id="validPwd"   type="button" class="btn btn-secondary" onclick="index.validPwd(${principal.user.id })">비밀번호 확인</button> --%>
	</div>	
			<button id="deleteBtn"  type="button" class="btn btn-danger" onclick="index.delete(${principal.user.id})">회원탈퇴</button>
</div>
<script src ="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>