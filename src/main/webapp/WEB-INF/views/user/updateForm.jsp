<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form>
		<input type="hidden" value="${principal.user.id}" class="form-control" id="id">
		<div class="form-group">
			<label for="username">Username</label> <input type="text" value="${principal.user.username}" class="form-control" id="username" placeholder="Enter username" name="username" readonly="readonly">
		</div>

		<c:choose>
			<c:when test="${empty principal.user.oauth}">
				<div class="form-group">
					<label for="password">Password</label> <input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
				</div>
				<div class="form-group">
					<label for="email">Email</label> <input type="email" value="${principal.user.email}" class="form-control" id="email" placeholder="Enter email" name="email">
				</div>
			</c:when>
			<c:otherwise>
				<div class="form-group">
					<label for="email">Email</label> <input type="email" value="${principal.user.email}" class="form-control" id="email" placeholder="Enter email" name="email" readonly="readonly">
				</div>
			</c:otherwise>
		</c:choose>



	</form>
	<button id="btn-update" class="btn btn-primary">회원수정완료</button>
</div>
<script src="${contextPath}/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


