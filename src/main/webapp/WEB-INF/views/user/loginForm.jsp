<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form action="${contextPath }/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" class="form-control" id="username" placeholder="Enter username" name="username">
		</div>
		<div class="form-group">
			<label for="password">Password</label> 
			<input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
		</div>		
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=e53ece4615b948ce35e9ac43dbb64569&redirect_uri=http://localhost:8000/blog/auth/kakao/callback&response_type=code"><img alt=""  height="38px" src="${contextPath }/image/kakao_login_button.png" /></a>
	</form>	
</div>
<script src="${contextPath}/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


