<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String contextRoot = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- jQuery UI 사용 환경 설정 -->
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


<style>
</style>

<script>
	$(document).ready(function() {

	});
</script>
</head>
<body>


	<div id="main">
		<div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png" width="300px">
			<div class="login">
				<a href="${pageContext.request.contextPath}/studentinfo.do">[수강생]${studentinfo.student_name}</a> 님 │ 로그아웃
			</div>
		</div>
		<div id="menu">
			<div class="menu">
				<ul class="nav nav-pills nav-justified">
					<li><a href="${pageContext.request.contextPath}/studentmain.do">성적조회</a></li>
				</ul>
			</div>
		</div>

		<div class="content">

			<h3>성적조회</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>과정ID</th>
						<th>과정명</th>
						<th>과정기간</th>
						<th>강의실</th>
						<th>과목수</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="st" items="${stugrade}">
					<form action="${pageContext.request.contextPath}/student001.do" method="post">
					<input type="hidden" name="open_course_id" value="${st.open_course_id }">
					<input type="hidden" name="course_name" value="${st.course_name }">
					<input type="hidden" name="course_start_day" value="${st.course_start_day}">
					<input type="hidden" name="course_end_day" value="${st.course_end_day}">
					<input type="hidden" name="class_name" value="${st.class_name }">
					
					<tr>
					<td>${st.open_course_id }</td>
					<td>${st.course_name }</td>
					<td>${st.course_start_day}~${st.course_end_day}</td>
					<td>${st.class_name }</td>
					<td><button type="submit" class="btn btn-default btn-sm" ${st.subject_count ==0?"disabled":""}>
								<span class="badge" id="Count">${st.subject_count}</span> 보기
							</button><input type="hidden" name="open_course_id" value ="${st.open_course_id }"></td>
					</tr>
					</form>
					</c:forEach>
					
				</tbody>
			</table>

		</div>
	</div>

</body>
</html>