var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-z-]+){1,2}$/;
let index = {
	init: function(){
		$("#btn-save").on("click",()=>{ // function(){} 대신 ()=>{} this를 바인딩하기 위해서 
			this.save();
		});
		$("#btn-update").on("click",()=>{ // function(){} 대신 ()=>{} this를 바인딩하기 위해서 
			this.update();
		});
		$("#check-username").on("click",()=>{
			this.validUsername();
		})
	},
	
	save: function() {
	/*	alert("user의 save함수 호출됨");*/
	
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
		
		/*console.log(data);*/
		
		//ajax 호출시 default가 비동기 호출
		//ajax가 통신을 성공하고 json을 리턴해주면 서버가 자동으로 자바 오브젝트로 변환해줌 
		
		if(!data.username){
			alert("아이디를 입력하세요")
			document.getElementById('username').focus();
			return;
		}
		if(!data.password){
			alert("비밀번호를 입력하세요")
			document.getElementById('password').focus();
			return;
		}
		
		if(!data.email){
			alert("이메일을 입력하세요")
			document.getElementById('email').focus();
			return;
		}
			if(data.username.length<3 || data.username.length > 16){
					alert("아이디는 최소 3자리 이상 15자리 이하로 작성해야 합니다.");
					document.getElementById('username').value='';
					document.getElementById('username').focus();
					return;
			}
			
			if(data.password.length<4 || data.password.length > 20){
					alert("비밀번호는 최소 4자리 이상 20자리 이하로 작성해야 합니다.");
					document.getElementById('password').value='';
					document.getElementById('password').focus();
					return;
			}
			if(!regExp.test(data.email)){
				alert("이메일 형식이 아닙니다.");
				document.getElementById('email').focus();
				return;
			}
		
		this.validUsername();
		this.validEmail();
		
		$.ajax({
			//회원가입 수행 요청 
			type: "POST",
			url: "/auth/joinProc", 
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){ // 성공
			alert("회원가입이 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){ //실패
			alert(JSON.stringify(error.responseText));
		});  // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		
	},
	
	update: function() {
	/*	alert("user의 save함수 호출됨");*/
	
		let data = {
			id:$("#id").val(),
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val(),
			originEmail: $("#origin-email").val(),
		};
		
		console.log(data);
		
		
		if(!data.password){
			alert("비밀번호를 입력하세요")
			document.getElementById('password').focus();
			return;
		}
		
		if(!data.email){
			alert("이메일을 입력하세요")
			document.getElementById('email').focus();
			return;
		}
		if(data.password.length<3 || data.password.length > 20){
					alert("비밀번호는 최소 4자리 이상 20자리 이하로 작성해야 합니다.");
					document.getElementById('password').value='';
					document.getElementById('password').focus();
					return;
			}
		if(!regExp.test(data.email)){
				alert("이메일 형식이 아닙니다.");
				document.getElementById('email').focus();
				return;
			}
			
		this.validEmail();
		
		if(confirm("수정하시겠습니까?")){
			$.ajax({
				type: "PUT",
				url: "/user", 
				data: JSON.stringify(data), // http body 데이터
				contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
				dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
			}).done(function(resp){ // 성공
				alert("회원수정이 완료되었습니다. 다시 로그인 해주세요");
				console.log(resp);
				location.href="/user/logout";
			}).fail(function(error){ //실패
				alert(JSON.stringify(error.responseText));
			}); 			
		}
	},
	delete: function(userid) {
		let data={
			userid: userid,
			password: $("#password").val()
		}
		if(!data.password){
			alert("비밀번호를 입력해주세요");
			document.getElementById("password").focus();
			return;
		}
		
			if(confirm("탈퇴하시겠습니까?")){
				$.ajax({
				type: "DELETE",
				url: `/user/${userid}`,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
				dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
			}).done(function(resp){ // 성공
				alert("회원탈퇴가 완료되었습니다.");
				console.log(resp);
				location.href="/user/logout";
			}).fail(function(error){ //실패
				alert(JSON.stringify(error.responseText));
			}); 
		}			
		
	},
	validPwd: function(userid){
		let data={
			userid: ""+userid,
			password: $("#password").val()
		}
		
		console.log(data);	
		
		if(!data.password){
			alert("비밀번호를 입력해주세요");
			document.getElementById('password').focus();
			return;
		}
		
		$.ajax({
			type: "POST",
			url: "/user/validPwd", 
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
		}).done(function(resp){
			console.log(resp);
			if(resp == null){
				alert("비밀번호를 입력해주세요");
				document.getElementById('password').focus();
				return false;
			}
			else if(resp == "Y"){
				/*alert("비밀번호가 일치합니다.");*/
				$("#validPwd").attr("disabled",true);
				$("#password").attr("disabled",true);
				location.href="/auth/updateForm";
				return true;
			}
			else{
				alert("비밀번호가 일치하지 않습니다.");
				document.getElementById("password").value="";
				return false;
			}
		
		}).fail(function(error){ //실패
			/*alert(JSON.stringify(error.responseText));*/
			 alert("code:"+error.status+"\n"+"message:"+error.responseText+"\n"+"error:"+error);
		}); 
		
	},
	
	validUsername:function(){
		let data={
			username: $("#username").val()
		};
		console.log(data);
		$.ajax({
			type: "GET",
			url: `/auth/joinProc/${data.username}/existsUsername`, 
		}).done(function(resp){
			if(resp == "N"){
				$("#userNameResult").text("사용 가능한 아이디입니다.");
				$("#userNameResult").css("color","green");
				
			}else{
				$("#userNameResult").text("이미 사용중인 아이디입니다.");
				$("#userNameResult").css("color","red");
				return;
			}
			console.log(resp);
			return resp;
		}).fail(function(error){
			alert(JSON.stringify(error.responseText));
			return error;
		})
	},
	
	validEmail:function(){
		let data={
			email: $("#email").val()
		};
		var returnValue;
		console.log(data);
		$.ajax({
			type: "GET",
			url: `/auth/joinProc/${data.email}/existsEmail`, 
			async:false,
		}).done(function(resp){
			if(resp == "N"){
/*				$("#emailResult").text("사용 가능한 이메일입니다..");
				$("#emailResult").css("color","green");*/ 
				document.getElementById("hidden-text").value=true;
				console.log($("#hidden-text").val());
				returnValue = $("#hidden-text").val();
			}else{
/*				$("#emailResult").text("이미 사용중인 이메일입니다..");
				$("#emailResult").css("color","red");*/
				document.getElementById("hidden-text").value=false;
				console.log($("#hidden-text").val());
				returnValue = $("#hidden-text").val();
			}
		}).fail(function(error){
			alert(JSON.stringify(error.responseText));
		})
		
		return returnValue;
	},
	
	
	sendMail: function(checknum){ // 이메일 중복 확인 및 이메일 인증번호 전송 js
		
		let data={
			mail: $("#email").val(),
		}
	checknum = checknum + 0;
	if(!data.mail){
			alert("이메일을 입력하세요")
			document.getElementById('email').focus();
			return;
		}
			if(!regExp.test(data.mail)){
				alert("이메일 형식이 아닙니다.");
				document.getElementById('email').focus();
				return;
			}
			var result = this.validEmail();
		/*	var result = document.getElementById("hidden-text").value;*/
			console.log(result);
			
			if(checknum == 1){ // 회원가입
					if(result == "false"){
					alert("이메일이 중복됩니다.");
					document.getElementById('email').value="";
				}else{
					$("#sendNumber").text("인증번호 전송");
					$("#mail_number").css("display","block");
						$.ajax({
							url:"/mail",
							type:"post",
							dataType:"json",
							data:JSON.stringify($("#email").val()),
							contentType: "application/json; charset=utf-8",
						}).done(function(resp){
							alert("인증번호 발송");
							console.log(resp);
							$("#Confirm").val(resp);
							index.confirmOnkey();
						}).fail(function(error){
							alert(JSON.stringify(error.responseText));
					});
				}
			}
			else{
					$("#sendNumber").text("인증번호 전송");
				$("#mail_number").css("display","block");
					$.ajax({
						url:"/mail",
						type:"post",
						dataType:"json",
						data:JSON.stringify($("#email").val()),
						contentType: "application/json; charset=utf-8",
					}).done(function(resp){
						alert("인증번호 발송");
						console.log(resp);
						$("#Confirm").val(resp);
						index.confirmOnkey();
					}).fail(function(error){
						alert(JSON.stringify(error.responseText));
				});
			}
	},
	confirmNumber: function(){ // 이메일 인증코드 확인 js
				var number1 = $("#number").val();
				var number2 = $("#Confirm").val();
		
				if(number1 != number2){
					alert("인증번호가 잘못되었습니다.");
				}
				else{
					alert("인증번호 확인 완료");
					$("#mail_number").css("display","none");
					$("#email").attr("disabled",true);
					$("#sendNumber").attr("disabled",true);
					$("#sendNumber").text("인증 완료");
					$("#btn-update").css("display","inline"); // 수정버튼
				}
		},
		
	confirmOnkey:function(){ // 키를 입력 시 마다 검사하는 js
			var number1 = $("#number").val();
				var number2 = $("#Confirm").val();
		
			$("#number").on("keyup",function(){
				number1 = $("#number").val();
				number2 = $("#Confirm").val();
				if(number1 != number2){
					$("#confirmTxt").remove;
					$("#confirmTxt").html("<span id='emconfirmchk'>인증번호가 잘못되었습니다.</span>")
					$("#emconfirmchk").css("color" ,"red");
					$("#emconfirmchk").css("font-weight" ,"bold");
					$("#emconfirmchk").css("font-size" ,"20px");
				}else{
					$("#confirmTxt").remove;
					$("#confirmTxt").html("<span id='emconfirmchk'>인증번호 확인 완료</span>")
					$("#emconfirmchk").css("color" ,"blue");
						$("#emconfirmchk").css("font-weight" ,"bold");
					$("#emconfirmchk").css("font-size" ,"20px");
				}
			})
	}
	}
index.init();