<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">


<!-- jQuery UI 사용 환경 설정 -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">


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
		//페이지 이동
		$(document).on("click",".page",function() {		
			$(location).attr('href',"${pageContext.request.contextPath}/admin3_3_StudentList.do?open_course_id=${open_course_id}&curPage="+$(this).attr("title")+"&key=${key}&value=${value}");
				});
	});
</script>
</head>
<body>
	<div id="main">
		 <div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png" width="300px">
			<div class="login">
				관리자 님 │ 로그아웃
			</div>
		</div>
		<div id="menu">
			<div class="menu">
				<ul class="nav nav-pills nav-justified">
					<li class=""><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">기초 정보 관리</a>
						<ul class="dropdown-menu">
							<li><a
								href="${pageContext.request.contextPath}/adminmain.do">과정명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin102.do">과목명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin103.do">교재명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin104.do">강의실명</a></li>
						</ul></li>
						<li class=""><a href="${pageContext.request.contextPath}/admin201.do">강사 계정 관리</a></li>
					<li class="active"><a href="${pageContext.request.contextPath}/adminOpenCourseList.do">개설 과정/과목
							관리</a></li>
					<li class=""><a
						href="${pageContext.request.contextPath}/admin401.do">수강생 관리</a></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#">성적
							관리</a>
						<ul class="dropdown-menu">
							<li><a href="${pageContext.request.contextPath}/admin501.do">과목별</a></li>
							<li><a href="${pageContext.request.contextPath}/admin502.do">수강생별</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
		<%@ include file = "/WEB-INF/view/header.jsp" %>
		<div class="content">
			<h3>수강생 명단</h3>
			<h4 style="text-align: center; font-weight: bold;">OCU001 /
				Framework을 활용한 빅데이터 개발자 과정</h4>
			<h5 style="text-align: center;">2016/09/01 ~ 2017/02/01</h5>
			<hr>
			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>수강생 ID</th>
						<th>이름</th>
						<th>주민번호 뒷자리</th>
						<th>전화번호</th>
						<th>등록일</th>
						<th>수료여부</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="student" items="${studentList}">
						<tr>
							<td>${student.id}</td>
							<td>${student.name}</td>
							<td>${student.ssn}</td>
							<td>${student.phone}</td>
							<td>${student.regDate}</td>
							<td>${empty student.finish_day ?(dateCompare<0?'수료':'수료예정'):student.finish_day} </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<div style="float:left;position:absolute">
			<button type="button" class="btn btn-default btn-sm">
						TotalCount <span class="badge" id="totalCount">${totalRow}</span></button>
			</div>

			<div style="text-align: center;">

				<ul class="pagination" style="margin: 0px 0px 20px 0px;">
					<c:forEach begin="1" end="${totalPage}" varStatus="c">
						<li class="${curPage==c.count?'active ':'' } page" title="${c.count}" ><a>${c.count}</a></li>
					</c:forEach>

				</ul>
			</div>

			<form class="form-inline" method="post" style="text-align: center;" action="admin3_3_StudentList.do">
				<div class="form-group">
					<select class="form-control" id="key" name="key">
						<option value="id">ID</option>
						<option value="name">Name</option>
						<option value="phone">Phone</option>
						<option value="regDate">RegDate</option>
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="value" name="value"
						required>
				</div>
				<input type="hidden" name="open_course_id"  value="${open_course_id}">
				<input type="hidden" name="curPage"  value="${curPage}">
				<button type="submit" class="btn btn-default">Search</button>
			</form>
		</div>
	</div>
</body>
</html>