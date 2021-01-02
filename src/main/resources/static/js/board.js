/**
 * 
 */
let index = {
		init:function(){
			$("#btn-save").on("click",()=>{ //function(){} 대신 ()=>{}을 사용한 이유는 this를 바인딩 하기 위해서 사용함, function으로 사용하면 this는 window객체를 가리킨다.
				this.save();
			});
			
			$("#btn-delete").on("click",()=>{ //function(){} 대신 ()=>{}을 사용한 이유는 this를 바인딩 하기 위해서 사용함, function으로 사용하면 this는 window객체를 가리킨다.
				this.deleteById();
			});
			
			$("#btn-update").on("click",()=>{ //function(){} 대신 ()=>{}을 사용한 이유는 this를 바인딩 하기 위해서 사용함, function으로 사용하면 this는 window객체를 가리킨다.
				this.update();
			});
			
			$("#btn-reply-save").on("click",()=>{ //function(){} 대신 ()=>{}을 사용한 이유는 this를 바인딩 하기 위해서 사용함, function으로 사용하면 this는 window객체를 가리킨다.
				this.replySave();
			});
			
		},
	save:function(){
		//alert('user의 save함수 호출됨');
		let data = {
				title:$("#title").val(),
				content:$("#content").val(),
		}
		//console.log(data);
		//ajax 호출시 디폴트가 비동기 호출임
		$.ajax({
			//글쓰기 수행 요청
			type:"POST",
			url:base+"/api/board",
			data : JSON.stringify(data), //http body데이터
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤타입인지(mime)
			dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 버퍼(스트링)로 오는데 json이면 => 자바스크립트 오브젝트로 변경해줌
		}).done(function(resp){
			console.log(resp);
			if(resp.status == 200)
			{
				alert("글쓰기가 완료되었습니다.");
				location.href = base;	
			}else{
				alert("글쓰기가 실패하였습니다."+resp.data);
			}
			
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	},
	deleteById:function(){
		 let id = $("#id").text();
		$.ajax({
			//글쓰기 수행 요청
			type:"DELETE",
			url:base+"/api/board/"+id,
			//data : JSON.stringify(data), //http body데이터
			//contentType:"application/json; charset=utf-8", //body 데이터가 어떤타입인지(mime)
			dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 버퍼(스트링)로 오는데 json이면 => 자바스크립트 오브젝트로 변경해줌
		}).done(function(resp){
			console.log(resp);
			if(resp.status == 200)
			{
				alert("삭제가 완료되었습니다.");
				location.href = base;	
			}else{
				alert("글쓰기가 실패하였습니다. : "+resp.data);
			}
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	},
	update:function(){
		let id = $("#id").val();

		let data = {
				title:$("#title").val(),
				content:$("#content").val(),
		}
		//ajax 호출시 디폴트가 비동기 호출임
		$.ajax({
			//글쓰기 수행 요청
			type:"PUT",
			url:base+"/api/board/"+id,
			data : JSON.stringify(data), //http body데이터
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤타입인지(mime)
			dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 버퍼(스트링)로 오는데 json이면 => 자바스크립트 오브젝트로 변경해줌
		}).done(function(resp){
			console.log(resp);
			if(resp.status == 200)
			{
				alert("글수정이 완료되었습니다.");
				location.href = base;	
			}else{
				alert("글수정이 실패하였습니다. : "+resp.data);
			}
			
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	},
	
	replySave:function(){
		//alert('user의 save함수 호출됨');
		let data = {
				boardId : $("#boardId").val(),
				content:$("#reply-content").val(),
		}
		console.log(data);
		//ajax 호출시 디폴트가 비동기 호출임
		$.ajax({
			//글쓰기 수행 요청
			type:"POST",
			url:base+`/api/board/${data.boardId}/reply`,
			data : JSON.stringify(data), //http body데이터
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤타입인지(mime)
			dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 버퍼(스트링)로 오는데 json이면 => 자바스크립트 오브젝트로 변경해줌
		}).done(function(resp){
			console.log(resp);
			if(resp.status == 200)
			{
				alert("댓글 작성이 완료되었습니다.");
				location.href = base+`/board/${data.boardId}`;	
			}else{
				alert("댓글 작성이 실패하였습니다."+resp.data);
			}
			
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	replyDelete:function(boardId, replyId){
		//ajax 호출시 디폴트가 비동기 호출임
		$.ajax({
			//
			type:"DELETE",
			url:base+`/api/board/${boardId}/reply/${replyId}`,
			
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤타입인지(mime)
			dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 버퍼(스트링)로 오는데 json이면 => 자바스크립트 오브젝트로 변경해줌
		}).done(function(resp){
			console.log(resp);
			if(resp.status == 200)
			{
				alert("댓글 삭제가 완료되었습니다.");
				location.href = base+`/board/${boardId}`;	
			}else{
				alert("댓글 삭제에 실패하였습니다."+resp.data);
			}
			
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	}
}

index.init();