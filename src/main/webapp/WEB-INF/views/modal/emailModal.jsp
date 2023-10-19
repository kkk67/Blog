<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
        <div class="modal-header">
	          <h5 class="modal-title" id="exampleModalLabel">이메일 수정</h5>
	          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
	            <span aria-hidden="true">x</span>
	          </button>
	        </div>
	        
	        <div class="modal-body">
				<form action="#">
				 	<!-- <div class="form-group">
					    <input type="password" class="form-control" placeholder="현재 비밀번호" id="emailPassword" >
					  </div> -->
					  <div class="form-group">
					  <input type="hidden" id="userid" value="${principal.user.id}">
					    <input type="email" class="form-control" placeholder="현재 이메일" id="inputEmail" >
					    <button id="validEmail"  role="button" type="button" class="form-control btn btn-secondary"  onclick="checkMail('${principal.user.email}') ">확인</button>
					  </div>
					  <div class="form-group">
					    <input type="email" class="form-control" placeholder="새로운 이메일" id="newEmail">
					    <button id="sendNumber"  type="button" class="form-control btn btn-secondary"  onclick="index.sendMail()">인증번호 전송</button>
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
						<span id="resultValid"></span>
						<input type="hidden"  id="numberCheck" value="0">
					  </div>
				</form>
	        </div>
	        
	        <div class="modal-footer">
	        <button class="btn btn-primary" type="button"  id="Email-btn">수정</button>
	          <button class="btn btn-danger" type="button" data-dismiss="modal">취소</button>
	        </div>
</body>
<script type="text/javascript">
function checkMail(email){
	var inputEmail = $("#inputEmail").val();
	
	if(inputEmail == email){
		$("#newEmail").attr("disabled",false);
		$("#sendNumber").attr("disabled",false);
		$("#inputEmail").attr("disabled",true);
		$("#validEmail").attr("disabled",true);
		$("#validEmail").text("확인 완료");
		$("#resultValid").text("");
		$("#resultValid").text("이메일이 확인되었습니다.");
		$("#resultValid").css("color","green");
	}
	else{
		$("#resultValid").text("");
		$("#resultValid").text("이메일을 다시 확인해주세요.");
		$("#resultValid").css("color","red");
	}
}
</script>
</html>