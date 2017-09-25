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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style.css">


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
		
	$("button.btnGrade").on("click", function(){
            
		var ocu_id = $(this).parents("tr").find( "td:eq(0)").text();
		var ocu_name = $(this).parents("tr").find("td:eq(1)").text();
		var ocu_start = $(this).parents("tr").find("td:eq(5)").text();
		var ocu_end = $(this).parents("tr").find("td:eq(6)").text();
		
		$("#grade-Modal").find("span#id").text(ocu_id);
		$("#grade-Modal").find("span#name").text(ocu_name);
		$("#grade-Modal").find("span#start").text(ocu_start);
		$("#grade-Modal").find("span#end").text(ocu_end);
   
        var result;
        var open_course_id = $(this).val();
		var student_id = $("div.content").find("span#id").text();  
             
            $.ajax({            	
            	
               url : "admin502_sub_oubgrade.do",
               data : {open_course_id : open_course_id, student_id : student_id},
               
               success : function(data) {                  
                   result = data;                   
                   console.log(result);                   
                   $("#grade-Modal").find("tbody").html(result);
               },
               async : false
            });    
            return result;
            
             
            //$("#bookImgModal #book_name").html(book_name);
            //$("#bookImgModal").modal();
         });
		
	});
</script>
</head>
<body>


	<div id="main">
		<div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png"
				width="300px">
			<div class="login">관리자 님 │ 로그아웃</div>
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
					<li class="active"><a class="dropdown-toggle " data-toggle="dropdown" href="#">성적
							관리</a>
						<ul class="dropdown-menu">
							<li><a href="${pageContext.request.contextPath}/admin501.do">과목별</a></li>
							<li><a href="${pageContext.request.contextPath}/admin502.do">수강생별</a></li>
						</ul></li>
				</ul>
			</div>
		</div>

		<div class="content">


			<h3>성적 관리 > 수강생별 > 수강 내역</h3>

			<h4 style="text-align: center; font-weight: bold;">
				<span id=id>${student_id}</span> / ${student_name} / ${student_ssn}
				/ ${student_phone}
			</h4>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>개설과정ID</th>
						<th>과정명</th>
						<th>강의실명</th>
						<th>정원</th>
						<th>수강인원</th>
						<th>과정시작일</th>
						<th>과정종료일</th>
						<th>개설과목수</th>
					</tr>
				</thead>
				<tbody>
					<!-- <tr>
						<td>OCU001</td>
						<td>Framework을 활용한 빅데이터 개발자 과정</td>
						<td>제2강의실</td>
						<td>25</td>
						<td>5</td>
						<td>16-09-01</td>
						<td>17-02-01</td>
						<td><button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#grade-Modal">
								<span class="badge" id="Count">3</span> 보기
							</button></td>
					</tr>
					<tr>
						<td>OCU002</td>
						<td>JAVA를 활용한 사물인터넷(IOT) 응용SW 개발자</td>
						<td>제4강의실</td>
						<td>40</td>
						<td>10</td>
						<td>17-01-17</td>
						<td>17-04-17</td>
						<td><button type="button" class="btn btn-default btn-sm" disabled>
								<span class="badge" id="Count">2</span> 보기
							</button></td>
					</tr> -->
					<c:forEach var="g" items="${studentoculist}">
						<tr>
							<td>${g.open_course_id}</td>
							<td>${g.course_name}</td>
							<td>${g.class_name}</td>
							<td>${g.jungwon}</td>
							<td>${g.student_count}</td>
							<td>${g.course_start_day}</td>
							<td>${g.course_end_day}</td>
							<td><button type="submit"
									class="btn btn-default btn-sm btnGrade" data-toggle="modal"
									data-target="#grade-Modal" value="${g.open_course_id}"
									${(g.subject_count==0)?'disabled':''}>
									<span class="badge" id="Count">${g.subject_count}</span> 보기
								</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


		</div>
	</div>


	<!-- 과목별 점수 확인 Modal -->
	<div class="modal fade" id="grade-Modal" role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">개설 과목별 확인</h5>
				</div>
				<div class="modal-body">

					<h4 style="text-align: center; font-weight: bold;">
						<span id="id"></span> / <span id="name"></span> / <span id="start"></span>
						~ <span id="end"></span>
					</h4>
					<h4 style="text-align: center;">${student_id}/${student_name}
						/ ${student_ssn} / ${student_phone}</h4>

					<hr>

					<table class="table table-striped table-bordered ocu">
						<thead>
							<tr>
								<th>개설과목<br>ID
								</th>
								<th>과목명</th>
								<th>출결<br>배점
								</th>
								<th>필기<br>배점
								</th>
								<th>실기<br>배점
								</th>
								<th>출결<br>점수
								</th>
								<th>필기<br>점수
								</th>
								<th>실기<br>점수
								</th>
								<th>실험일</th>
								<th>시험과목파일</th>
							</tr>
						</thead>
						<tbody>
							<!-- <tr>
						<td>OUB001</td>
						<td>자바 네트워트 프로그래밍</td>
						<td>30</td>
						<td>30</td>
						<td>40</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
						<td>17/01/01</td>
						<td>java_simsim_161290.zip</td>
					</tr>
					<tr>
						<td>OUB002</td>
						<td>관계형 데이터베이스</td>
						<td>30</td>
						<td>30</td>
						<td>40</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
						<td>17/01/01</td>
						<td>java_simsim_161290.zip</td>
					</tr>
					<tr>
						<td>OUB003</td>
						<td>JDBC 프로그래밍</td>
						<td>30</td>
						<td>30</td>
						<td>40</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
						<td>17/01/01</td>
						<td>java_simsim_161290.zip</td>
					</tr> -->
						</tbody>
					</table>

				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-default">확인</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>

</body>
</html>