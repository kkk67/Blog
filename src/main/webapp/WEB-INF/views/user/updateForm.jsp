<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
<button id="btn-update"  class="btn btn-primary">수정</button>
	<a href="/auth/deleteForm" class="btn btn-danger">회원탈퇴</a>
	<form>
		<input type="hidden" id ="id" value="${principal.user.id }"/>
		<div class="form-group">
			<label for="username">Username</label> <input type="text"
				class="form-control"  value=" ${principal.user.username }" placeholder="Enter Username" id="username" readonly>
		</div>
		<c:if test="${empty principal.user.oauth }">
		<div class="form-group">
			<label for="password">Password:</label> <input type="password"
				class="form-control"   placeholder="Enter password" id="password">
		</div>		
		<div class="form-group">
			<label for="email">Email address:</label> <input type="email"
				class="form-control"  value=" ${principal.user.email }"  placeholder="Enter email" id="email" >
			<button id="sendNumber"  type="button" class="btn btn-secondary"  onclick="index.sendMail()">인증번호 전송</button>
			<input type="hidden" id="hidden-text">
			<input type="text" id="Confirm" name="Confirm" style="display: none" value="">
			<div class="form-group last mb-4 check_input" id="mail_number"   style="display: none" >
			<br/>
				<label for="number" id="confirmTxt">인증번호를 입력해주세요</label>
				<div>
					<input type="text" name="number" id="number" style="width:250px; margin-top: -10px">
					<button id="confirmBtn" type="button" class="btn btn-primary" onclick="index.confirmNumber(0)">이메일 인증</button>			
				</div>
			</div>
		</div>
		</c:if>

		<c:if test="${not empty principal.user.oauth }">
		<div class="form-group">
			<label for="password">Password:</label> <input type="password"
				class="form-control"   placeholder="" id="password" readonly>
		</div>		
		<div class="form-group">
			<label for="email">Email address:</label> <input type="email"
				class="form-control"  value=" ${principal.user.email }"  placeholder="Enter email" id="email" readonly>
		</div>

		</c:if>
	</form>
			
<!-- 			<span id="emailResult"></span> 	 -->
</div>
<script src ="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>