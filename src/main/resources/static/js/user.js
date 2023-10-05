var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-z-]+){1,2}$/;
let index = {
	init: function(){
		$("#btn-save").on("click",()=>{ // function(){} 대신 ()=>{} this를 바인딩하기 위해서 
			this.save();
		});
	/*		$("#btn-login").on("click",()=>{ // function(){} 대신 ()=>{} this를 바인딩하기 위해서 
			this.login();
		});*/
		$("#btn-update").on("click",()=>{ // function(){} 대신 ()=>{} this를 바인딩하기 위해서 
			this.update();
		});
		$("#check-username").on("click",()=>{
			this.validUsername();
		}),
		$("#check-email").on("click",()=>{
			this.validEmail();
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
			if(data.username.length<2 || data.username.length > 16){
					alert("아이디는 최소 3자리 이상 15자리 이하로 작성해야 합니다.");
					document.getElementById('username').value='';
					document.getElementById('username').focus();
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
			email:$("#email").val()
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
		$.ajax({
			type: "PUT",
			url: "/user", 
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){ // 성공
			alert("회원수정이 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){ //실패
			alert(JSON.stringify(error.responseText));
		}); 
	},
	delete: function(userid) {
	/*	alert("user의 save함수 호출됨");*/
	
		console.log(userid);
		$.ajax({
			type: "DELETE",
			url: `/user/${userid}`,
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){ // 성공
			alert("회원탈퇴가 완료되었습니다.");
			console.log(resp);
			location.href="/user/logout";
		}).fail(function(error){ //실패
			alert(JSON.stringify(error.responseText));
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
		console.log(data);
		$.ajax({
			type: "GET",
			url: `/auth/joinProc/${data.email}/existsEmail`, 
		}).done(function(resp){
			if(resp == "N"){
				$("#emailResult").text("사용 가능한 이메일입니다..");
				$("#emailResult").css("color","green");
			}else{
				$("#emailResult").text("이미 사용중인 이메일입니다..");
				$("#emailResult").css("color","red");
				return;
			}
		}).fail(function(error){
			alert(JSON.stringify(error.responseText));
		})
	}
	
	/*login: function() {
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
		};
		console.log(data);
		
		//ajax 호출시 default가 비동기 호출
		//ajax가 통신을 성공하고 json을 리턴해주면 서버가 자동으로 자바 오브젝트로 변환해줌 
		$.ajax({
			type: "POST",
			url: "/api/user/login", 
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){ // 성공
			alert("로그인이 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){ //실패
			alert(JSON.stringify(error));
		});  // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		
	}*/
}
index.init();