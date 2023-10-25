<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container justify-content-center"  style="width: 900px; margin-top: 100px;">
	<form class="form-horizontal"    >
		<div class="form-group"  >
			<div style=>
			<label  for="username">Username</label>
			 <input type="text" class="form-control " placeholder="Enter Username" id="username" style="margin-top: -10px; ">
			<button id="check-username"  type="button" class=" form-control btn btn-secondary"  >중복확인</button>
			<span id="userNameResult"></span>
			</div>
		</div>
		<div class="form-group">
			<label for="password">Password</label> 			
			<input type="password" class="form-control " placeholder="Enter password" id="password" style="margin-top: -10px">

		</div>


		<div class="form-group">
				<label for="email">Email address</label>
			 <input type="email" class="form-control" placeholder="Enter email" id="newEmail" name="email" style="margin-top: -10px; ">
			<!-- <button id="check-email" type="button" class="btn btn-secondary">중복확인</button>
			<span id="emailResult"></span> -->  <!-- position: absolute; bottom: 445px ;left: 1210px; -->
			<button id="sendNumber"  type="button" class="btn btn-secondary form-control"  onclick="index.sendMail(1)" style="">인증번호 전송</button>
			<input type="hidden" id="hidden-text" value="">
			<input type="text" id="Confirm" name="Confirm" style="display: none" value="false">
			<div class="form-group last mb-4 check_input" id="mail_number"   style="display: none" >
			<br/>
				<label for="number" id="confirmTxt">인증번호를 입력해주세요</label>
				<input type="hidden"  id="numberCheck" value="0">
				<div>
					<input type="text" name="number" id="number" style="width:250px; margin-top: -10px">
					<button id="confirmBtn" type="button" class="btn btn-primary" onclick="index.confirmNumber()">이메일 인증</button>			
				</div>
			</div>
		</div>
	</form>
	<br />
	<button id="btn-save" class="btn btn-primary form-control">회원가입</button>
</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>