let index = {
	init: function(){
		$("#btn-save").on("click",()=>{ 
			this.save();
		});
		$("#btn-delete").on("click",()=>{ 
			this.deleteById();
		});
		$("#btn-update").on("click",()=>{ 
			this.update();
		});
		$("#btn-reply-save").on("click",()=>{ 
			this.replysave();
		});
	},
	
	save: function() {
		let data = {
			title:$("#title").val(),
			content:$("#content").val(),
		};
		
		$.ajax({
			type: "POST",
			url: "/api/board", 
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){ // 성공
			alert("글 작성이 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){ //실패
			alert(JSON.stringify(error));
		});  // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		
	},
	
	deleteById: function() {
		let id = $("#id").text();
		
		$.ajax({
			type: "DELETE",
			url: "/api/board/"+id, 
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){ // 성공
			alert("삭제가 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){ //실패
			alert(JSON.stringify(error));
		}); 
		
	},
	
	update: function() {
		let id = $("#id").val();
		
		let data = {
			title:$("#title").val(),
			content:$("#content").val(),
		};
		$.ajax({
			type: "PUT",
			url: "/api/board/" +id, 
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){ // 성공
			alert("글 수정이 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){ //실패
			alert(JSON.stringify(error));
		});  
		
	},
	replysave: function() {
		let data = {
			userid:$("#userid").val(),
			content:$("#reply-content").val(),
			boardid:$("#boardid").val()
		};
		
	
		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardid}/reply`, 
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){ // 성공
			alert("댓글 작성이 완료되었습니다.");
			console.log(resp);
			location.href= `/board/${data.boardid}`;
		}).fail(function(error){ //실패
			alert(JSON.stringify(error));
		});  // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	},
	
	replyDelete: function(boardId,replyId) {
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){ // 성공
			alert("삭제가 완료되었습니다.");
			console.log(resp);
			location.href=`/board/${boardId}`;
		}).fail(function(error){ //실패
			alert(JSON.stringify(error));
		}); 
		
	},
	
}
index.init();