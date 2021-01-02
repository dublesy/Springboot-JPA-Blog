/**
 * 
 */

let index = {
		init:function(){
			$("#btn-save").on("click",()=>{ //function(){} 대신 ()=>{}을 사용한 이유는 this를 바인딩 하기 위해서 사용함, function으로 사용하면 this는 window객체를 가리킨다.
				this.save();
			});
			
			$("#btn-update").on("click",()=>{ //function(){} 대신 ()=>{}을 사용한 이유는 this를 바인딩 하기 위해서 사용함, function으로 사용하면 this는 window객체를 가리킨다.
				this.update();
			});
			
			/*$("#btn-login").on("click",()=>{
				this.login();
			});*/
					
		},
	save:function(){
		//alert('user의 save함수 호출됨');
		let data = {
				username:$("#username").val(),
				password:$("#password").val(),
				email:$("#email").val()
		}
		//console.log(data);
		//ajax 호출시 디폴트가 비동기 호출임
		$.ajax({
			//회원가입 수행 요청
			type:"POST",
			url:base+"/auth/joinProc",
			data : JSON.stringify(data), //http body데이터
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤타입인지(mime)
			dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 버퍼(스트링)로 오는데 json이면 => 자바스크립트 오브젝트로 변경해줌
		}).done(function(resp){
			if(resp.status == 200)
			{
				alert("회원가입이 완료되었습니다.");
				location.href = base;	
			}else{
				alert("회원가입에 실패하였습니다. : "+resp.data);
			}
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	},

	update:function(){
		let data = {		
				id:$("#id").val(),
				username:$("#username").val(),
				password:$("#password").val(),
				email:$("#email").val()
		}
		//console.log(data);
		//ajax 호출시 디폴트가 비동기 호출임
		$.ajax({
			//회원가입 수행 요청
			type:"PUT",
			url:base+"/user",
			data : JSON.stringify(data), //http body데이터
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤타입인지(mime)
			dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 버퍼(스트링)로 오는데 json이면 => 자바스크립트 오브젝트로 변경해줌
		}).done(function(resp){
			console.log(resp);
			
			if(resp.status == 200)
			{
				alert("회원수정이 완료되었습니다.");
				location.href = base;	
			}else{
				alert("회원수정에 실패하였습니다. : "+resp.data);
			}
			
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	},
		
	/*login:function(){
		//alert('user의 save함수 호출됨');
		let data = {
				username:$("#username").val(),
				password:$("#password").val()
		}
		//console.log(data);
		//ajax 호출시 디폴트가 비동기 호출임
		$.ajax({
			//회원가입 수행 요청
			type:"POST",
			url:base+"/api/user/login",
			data : JSON.stringify(data), //http body데이터
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤타입인지(mime)
			dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 버퍼(스트링)로 오는데 json이면 => 자바스크립트 오브젝트로 변경해줌
		}).done(function(resp){
			console.log(resp);
			alert("로그인이 완료되었습니다.");
			
			location.href = base;
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	}
	*/
}

index.init();