<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form>
		<input value="${board.id}" type="hidden" class="form-control" id="id" >
		<div class="form-group">
			<input value="${board.title}" type="text" class="form-control" id="title" placeholder="Enter title">
		</div>
		<div class="form-group">
			<textarea class="form-control  summernote" rows="5" id="content">${board.content}</textarea>
		</div>
	</form>
	<button id="btn-update" class="btn btn-primary">글수정완료</button>
</div>
<script>
	$('.summernote').summernote({
		tabsize : 2,
		height : 300
	});
</script>
<script src="${contextPath}/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>


