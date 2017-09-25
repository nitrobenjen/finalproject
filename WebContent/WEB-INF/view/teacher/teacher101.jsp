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
		var open_course_id = "";
		var course_name = "";
		var course_day = "";
		
		//과정에 속한 수강생 목록 출력액션
		$(document).on("click",".listbtn", function(){	
			var txt="";
			var pagenum="";
			var pagecount=1;
			
			open_course_id = $(this).val();
			course_name = $(this).parents("tbody tr").children().eq(3).text();
			course_day = $(this).parents("tbody tr").children().eq(2).text();
			var txt2 = ""+course_name+"("+course_day+")";
			$(".modal-body1 h4").html(txt2);
			
			
			$.ajax({
			
					
				url:"teachschedulestulist.do",
				data:{"open_course_id":open_course_id},
				success: function(data){
					var myObj = JSON.parse(data);	
					for(var i=0; i<Object.keys(myObj).length; i++){
						
						txt += "<tr><td>"+myObj[i].student_id+"</td><td>"+myObj[i].student_name+"</td><td>"+myObj[i].student_phone+"</td><td>"+myObj[i].student_regdate+"</td><td>"+myObj[i].finish_day+"</td></tr>";
						
					}
					
					//페이징 번호
					for(var i=1; i<myObj[0].totalpage+1; i++){
						
						if(pagecount == 1){
							pagenum+= "<li class=\"active\"><a href=\"#\" class=\"pagebtn\">"+pagecount+"</a></li>";
						}else{
							pagenum+= "<li><a href=\"#\" class=\"pagebtn\">"+pagecount+"</a></li>";
						}						
						pagecount++;
						
					}
					
					
					$(".pagenum").html(pagenum);
					$(".stulist").html(txt);
					$("#pre-list-Modal").modal("show");
				}
				
			});
			
		});
		
		//수강생 목록 페이징 처리 버튼
		$(document).on("click",".pagebtn", function(){	
			
			var currentpage = $(this).text();
			var txt="";
			var pagenum="";
			var pagecount=1;
			
			var txt2 = ""+course_name+"("+course_day+")";
			$(".modal-body1 h4").html(txt2);
			
			
			$.ajax({			
					
				url:"teachschedulestulist.do",
				data:{"open_course_id":open_course_id, "currentpage":currentpage},
				success: function(data){
					var myObj = JSON.parse(data);	
					console.log(myObj);
					for(var i=0; i<Object.keys(myObj).length; i++){
						
						txt += "<tr><td>"+myObj[i].student_id+"</td><td>"+myObj[i].student_name+"</td><td>"+myObj[i].student_phone+"</td><td>"+myObj[i].student_regdate+"</td><td>"+myObj[i].finish_day+"</td></tr>";
						
					
					}
					
					
					//페이징 번호
					for(var i=1; i<myObj[0].totalpage+1; i++){
						
						if(pagecount == currentpage){
							pagenum+= "<li class=\"active\"><a href=\"#\" class=\"pagebtn\">"+pagecount+"</a></li>";
						}else{
							pagenum+= "<li><a href=\"#\" class=\"pagebtn\">"+pagecount+"</a></li>";
						}						
						pagecount++;
						
					}
					
					$(".pagenum").html(pagenum);
					$(".stulist").html(txt);
					$("#pre-list-Modal").modal("show");
				}
				
			});
			
			
		});
		
		$(".searchbtn").on("click", function(){
			
			var txt, txt2 ="";			
			var key = $("#key").val();
			var value =$("#value").val();
			
			
			$.ajax({
				url :"teachschedulesearch.do",
				data : {"key":key, "value":value},
				success : function(data){
					var myObj = JSON.parse(data);
					
					if(Object.keys(myObj).length != 0){
						
						
						for(var i=0; i<Object.keys(myObj).length; i++){
							
							txt += "<tr><td>"+myObj[i].open_sub_id+"</td>";
							txt += "<td>"+myObj[i].subject_name+"</td>";
							txt += "<td>"+myObj[i].sub_start_day+"~"+myObj[i].sub_end_day+"</td>";
							txt += "<td>"+myObj[i].course_name+"</td>";
							txt += "<td>"+myObj[i].course_start_day+"~"+myObj[i].course_end_day+"</td>";
							txt += "<td>"+myObj[i].class_name+"</td>";
							txt += "<td>"+myObj[i].book_name+"</td>";
							txt += "<td>"+myObj[i].student_count+"/"+myObj[i].jungwon+"</td>";
							if(myObj[i].student_count == 0){
								txt += "<td><button type=\"button\" class=\"btn btn-default listbtn\" disabled value=\""+myObj[i].open_course_id+"\">명단</button></td></tr>";
							
							}else{
								txt += "<td><button type=\"button\" class=\"btn btn-default listbtn\" value=\""+myObj[i].open_course_id+"\">명단</button></td></tr>";
							}
							}
						
						
						
					}else{
						txt += "<tr><td colspan='9' style='text-align:center;'>검색된 결과가 없습니다.</td></tr>"
					}
					
					
				
					
					$(".teacherlist").html(txt);
					
				
				}});
			
		});
		
		

	});
</script>
</head>
<body>


	<div id="main">
		<div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png" width="300px">
			<div class="login">
			<%-- 	<a href="${pageContext.request.contextPath}/teacher/info.jsp">[강사]김정은</a> 님 │ 로그아웃 --%>
				<a href="${pageContext.request.contextPath}/teachinfo.do">${sessionScope.logininfo.id_}[강사]${teachinfo.teacher_name}</a> 님 │ <a href="${pageContext.request.contextPath}/logout.do">로그아웃</a>
			</div>
		</div>
		<div id="menu">
			<div class="menu">
				<ul class="nav nav-pills nav-justified">
					<li class="active"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">강의스케쥴</a>
						<ul class="dropdown-menu">
							<li><a href="${pageContext.request.contextPath}/teachschedulelist.do">강의
									예정</a></li>
							<li><a href="${pageContext.request.contextPath}/teachinsub.do">강의 중</a></li>
							<li><a href="${pageContext.request.contextPath}/teachendsub.do">강의
									종료</a></li>
						</ul></li>
					<li><a href="${pageContext.request.contextPath}/teacher201.do">배점관리</a></li>
					<li class=""><a
						href="${pageContext.request.contextPath}/teacher301.do">성적관리</a></li>
				</ul>
			</div>
		</div>

		<div class="content">


			<h3>강의스케쥴 > 강의 예정</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>과목ID</th>
						<th>과목명</th>
						<th style="width: 100px;">과목기간</th>
						<th>과정명</th>
						<th style="width: 100px;">과정기간</th>
						<th>강의실</th>
						<th>교재명</th>
						<th>수강생인원</th>
						<th>수강생</th>
					</tr>
				</thead>
				<tbody class="teacherlist">
<!-- 					<tr>
						<td>OUB001</td>
						<td>자바 네트워트 프로그래밍</td>
						<td>17/07/01 ~ 17/07/31</td>
						<td>Framework을 활용한 빅데이터 개발자 과정</td>
						<td>17/03/01 ~ 17/10/01</td>
						<td>제2강의실</td>
						<td>html 웹 프로그래밍 기초</td>
						<td>24/40</td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#pre-list-Modal">명단</button></td>
					</tr> -->
					
					<c:forEach var="a" items="${teachlist}">
					
						<tr>
						<td>${a.open_sub_id}</td>
						<td>${a.subject_name}</td>
						<td>${a.sub_start_day} ~ ${a.sub_end_day}</td>
						<td>${a.course_name}</td>
						<td>${a.course_start_day} ~ ${a.course_end_day}</td>
						<td>${a.class_name}</td>
						<td>${a.book_name}</td>
						<td>${a.student_count}/${a.jungwon}</td>
						<td><button type="button" class="btn btn-default listbtn" ${a.student_count == 0?"disabled":""} value="${a.open_course_id}">명단</button></td>
						</tr>
					
					</c:forEach>
					
					
					
				</tbody>
			</table>


			<form class="form-inline" style="text-align: center;">
				<div class="form-group">
					<select class="form-control" id="key" name="key">
						<option value="subject_name">과목명</option>
						<option value="course_name">과정명</option>
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="value" name="value"
						required>
				</div>
				<button type="button" class="btn btn-default searchbtn">Search</button>
			</form>


		</div>
	</div>




	<!-- 수강생 명단 Modal -->
	<div class="modal fade" id="pre-list-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">강의스케쥴 > 강의 예정 > 수강생 정보</h5>
				</div>
				<form class="form-horizontal">
					<div class="modal-body modal-body1">

						<h4>자바 네트워트 프로그래밍(17/07/01 ~ 17/07/31)</h4>

						<hr>
						<table class="table table-striped table-bordered ocu">
							<thead>
								<tr>
									<th>수강생 ID</th>
									<th>이름</th>
									<th>전화번호</th>
									<th>등록일</th>
									<th>수료여부</th>
								</tr>
							</thead>
							<tbody class="stulist">
								<tr >
									<td>STU001</td>
									<td>홍길동</td>
									<td>010-1234-1234</td>
									<td>17/03/21</td>
									<td>중도탈락(17-06-29)</td>
								</tr>
							</tbody>
						</table>
	<!-- 					<div style="float: left; position: absolute">
							<button type="button" class="btn btn-default btn-sm">
								TotalCount <span class="badge" id="totalCount">25</span>
							</button>
						</div> -->

						<div style="text-align: center;">

							<ul class="pagination pagenum" style="margin: 0px 0px 20px 0px;">
								<li class="active"><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">5</a></li>
							</ul>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					</div>
				</form>
			</div>

		</div>
	</div>


</body>
</html>