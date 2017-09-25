<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	$("#testdate, #testmoddate").datepicker({
		dateFormat : "yy/mm/dd",
		minDate : new Date('1994/01/01'),
		maxDate : new Date('2018/01/01'),
		changeMonth : true,
		changeYear : true
		});

	$(".bAddBtn").on("click", function() {
		$("#b-insert-Modal #focusedInput").val($(this).val());
		var subject_name = $(this).parents("tr").find("td:eq(1)").text();
		var subject_day = $(this).parents("tr").find("td:eq(2)").text();
		$("#b-insert-Modal h4").html(subject_name)
		$("#b-insert-Modal h4").append("("+subject_day+")");
		$("#b-insert-Modal").modal();
		});

	$(".bjAdd").on("keyup", function() {
		$(this).val( $(this).val().replace(/[^0-9]/g,"") );
		var b_chulsuk = $(".bajum01").val();
		var b_filki = $(".bajum02").val();
		var b_silki = $(".bajum03").val();
		
		var subject_name = $(this).parents("tr").find("td:eq(1)").text();
		var subject_day = $(this).parents("tr").find("td:eq(2)").text();
		$("#b-mod-Modal h4").html(subject_name)
		$("#b-mod-Modal h4").append("("+subject_day+")");
		/* 
		console.log("출석: " + b_chulsuk);
		console.log("필기: " + b_filki);
		console.log("실기: " + b_silki);
 		*/
		$.get("teachBajumChk.do", {b_chulsuk : b_chulsuk, b_filki : b_filki, b_silki : b_silki}, function(data) {
			var result = data;
			console.log(result);
			if (result == 101) {
				$("#alert_").css("display", "inline");
				$("#alert_").css("color", "red");
				$("#alert_").html("배점의 총합은 100입니다.");
			} else if (reslt = 100) {
				$("#alert_").css("display", "none");
			}
				console.log(data);
		});

	});

	$("#b-insert-Modal").on('hidden.bs.modal', function() {
		$(".bajum01").val("");
		$(".bajum02").val("");
		$(".bajum03").val("");
		$("#alert_").css("display", "none");
	});
		//시험 배점 입력 예외처리 -ajax
	$(".bUpBtn").on("click",function() {
			var chulsuk = $(this).parents("tr").find("td:eq(7)").text(); //8
			console.log(chulsuk);
			var filki = $(this).parents("tr").find("td:eq(8)").text(); //9
			var silki = $(this).parents("tr").find("td:eq(9)").text(); //10
			var test_date = $(this).parents("tr").find("td:eq(10)").text();
			var test_munjae = $(this).parents("tr").find("td:eq(11)").text();
			console.log(test_munjae);
			$("#b-mod-Modal #openSub_id").val($(this).val());
			$("#b-mod-Modal .bajum01").val(chulsuk);
			$("#b-mod-Modal .bajum02").val(filki);
			$("#b-mod-Modal .bajum03").val(silki);
			$("#b-mod-Modal #testmoddate").val(test_date);
			$("#b-mod-Modal #test_munjae").val(test_munjae);
			$("#b-mod-Modal").modal();
	});

	$("#munjaeBtn").on("click", function() {
		$("#test_munjae_act").click();
	});

	$("#test_munjae").on({
		/* 
		"focus": function(){
			$("#test_munjae_act").click();
		},
		 */
		"click" : function() {
			$("#test_munjae_act").click();
		}
		/* ,
	    "keyup": function(){
			$("#test_munjae_act").click();
		} */
	});

	$("#test_munjae_act").on("change",function() {
		var file_name = $(this).val().lastIndexOf("\\") + 1;
		var test = $(this).val().substring(file_name);
		$("#b-mod-Modal #test_munjae").val($(this).val().substring($(this).val().lastIndexOf("\\") + 1));
	});
});
</script>
</head>
<body>


	<div id="main">
		<div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png"
				width="300px">
			<div class="login">
				<a href="${pageContext.request.contextPath}/teachinfo.do"">[강사]${teachinfo.teacher_name}</a> 님 │ <a href="${pageContext.request.contextPath}/logout.do">로그아웃</a>
			</div>
		</div>
		<div id="menu">
			<div class="menu">
				<ul class="nav nav-pills nav-justified">
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#">강의스케쥴</a>
						<ul class="dropdown-menu">
							<li><a href="${pageContext.request.contextPath}/teachschedulelist.do">강의
									예정</a></li>
							<li><a href="${pageContext.request.contextPath}/teachinsub.do">강의 중</a></li>
							<li><a href="${pageContext.request.contextPath}/teachendsub.do">강의
									종료</a></li>
						</ul></li>
					<li class="active"><a href="${pageContext.request.contextPath}/teacher201.do">배점관리</a></li>
					<li ><a
						href="${pageContext.request.contextPath}/teacher301.do">성적관리</a></li>
				</ul>
			</div>
		</div>

		<div class="content">


			<h3>배점관리</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>과목ID</th>
						<th>과목명</th>
						<th style="width: 90px;">과목기간</th>
						<th>과정명</th>
						<th style="width: 90px;">과정기간</th>
						<th style="width: 85px;">강의실</th>
						<th>교재명</th>
						<th>출결배점</th>
						<th>필기배점</th>
						<th>실기배점</th>
						<th>시험일</th>
						<th>시험문제</th>
						<th>등록</th>
						<th>수정</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty bajumlist}">
							<tr>
								<td colspan="9" style="text-align: center">- 관리 가능한 과목이
									없습니다. -</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="b" items="${bajumlist}">
								<tr>
									<td>${b.open_sub_id}</td>
									<td>${b.subject_name}</td>
									<td style="text-align: center">${b.sub_start_day}~${b.sub_end_day}</td>
									<td>${b.course_name}</td>
									<td style="text-align: center">${b.course_start_day}~${b.course_end_day }</td>
									<td>${b.class_name}</td>
									<td>${b.book_name}</td>
									<td>${b.b_chulsuk}</td>
									<td>${b.b_filki}</td>
									<td>${b.b_silki}</td>
									<td>${b.test_date}</td>
									<td>${b.test_munje}</td>
									<td><button type="button"
											class="btn btn-default bAddBtn"
											data-toggle="modal" data-target="#Modal"
											value="${b.open_sub_id}" ${(b.b_chulsuk eq '-')?'':'disabled'}>등록</button></td>
									<td><button type="button" class="btn btn-default bUpBtn"
											data-toggle="modal" value="${b.open_sub_id}"
											${(b.b_chulsuk eq '-')?'disabled':''}>수정</button></td>
								</tr>

							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>


			<form class="form-inline" method="post" style="text-align: center;">
				<div class="form-group">
					<select class="form-control" id="key" name="key">
						<option value="name">Name</option>
						<option value="phone">Phone</option>
						<option value="email">Email</option>
						<option value="regDate">RegDate</option>
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="value" name="value"
						required>
				</div>
				<button type="submit" class="btn btn-default">Search</button>
			</form>


		</div>
	</div>




	<!-- 배점 등록 Modal -->
	<div class="modal fade" id="b-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">배점관리 > 등록</h5>
				</div>
				<form class="form-horizontal" enctype="multipart/form-data" method="post" action="${pageContext.request.contextPath}/teachbajumAdd.do">
					<div class="modal-body">

						<h4>JDBC 프로그래밍(17/09/01 ~ 17/09/30)</h4>
						<h5 style="margin-left: 15px;">ㆍ출결, 필기, 실기의 총 배점은 100점입니다.
							100점보다 적거나 많을 수 없습니다.</h5>
						<h5 style="margin-left: 15px;">ㆍ시험일은 과목기간을 벗어날 수 없습니다.</h5>
						<h5 style="margin-left: 15px;">ㆍ시험문제 파일은 .zip 확장자만 등록할 수
							있습니다.</h5>

						<hr>

						<div class="form-group">
							<label class="control-label col-sm-3">과목ID</label>
							<div class="col-sm-9">
								<input class="form-control" name="open_sub_id" id="focusedInput" type="text"
									readonly>
							</div>
							<div class="col-sm-12 m10" style="margin-left: 63px;">
								<label style="padding-right: 15px;">출결배점</label> 
								<input type="text" class="form-control bjAdd bajum01" name="b_chulsuk" maxlength="2" style="width: 70px; display: inline-block; margin-left: 15px; margin-right: 15px;" required> 
								
								<label style="padding-right: 15px;">필기배점</label> 
								<input type="text" class="form-control bjAdd bajum02" name="b_filki" maxlength="2" style="width: 70px; display: inline-block; margin-right: 15px;" required>
								<label style="padding-right: 15px;">실기배점</label> 
								<input type="text" class="form-control bjAdd bajum03" name="b_silki" maxlength="2" style="width: 70px; display: inline-block; margin-right: 15px;" required> <br>
								<label style="display: none; margin-left: 90px; padding-right: 10px;" id="alert_">alert</label>
							</div>
							<!-- 
							<div class="col-sm-12 m10" style="margin-left: 150px;">
								<label style="padding-right: 10px;">alert</label>
							</div>
							 -->
							<label class="control-label col-sm-3 m10">시험일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control" id="testdate"
									name="testdate" placeholder="시험일(YYYY/MM/DD)"
									required="required">
							</div>
							<label class="control-label col-sm-3 m10">시험문제</label>
							<div class="col-sm-9 m10">
								<input type="file" class="form-control" id="test_filename"
									name="test_filename" required="required"> <span
									class="help-block">(only .zip)</span>
							</div>
						</div>

					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-default addSubmitBtn">등록</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					</div>
				</form>
			</div>

		</div>
	</div>



	<!-- 배점 수정 Modal -->
	<div class="modal fade" id="b-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">배점관리 > 수정</h5>
				</div>
				<form class="form-horizontal" enctype="multipart/form-data" method="post" action="${pageContext.request.contextPath}/teachbajumUp.do">
					<div class="modal-body">

						<h4>자바 네트워트 프로그래밍(17/07/01 ~ 17/07/31)</h4>
						<h5 style="margin-left: 15px;">ㆍ출결, 필기, 실기의 총 배점은 100점입니다.
							100점보다 적거나 많을 수 없습니다.</h5>
						<h5 style="margin-left: 15px;">ㆍ시험일은 과목기간을 벗어날 수 없습니다.</h5>
						<h5 style="margin-left: 15px;">ㆍ시험문제 파일은 .zip 확장자만 등록할 수
							있습니다.</h5>

						<hr>

						<div class="form-group">
							<label class="control-label col-sm-3">과목ID</label>
							<div class="col-sm-9">
								<input class="form-control" name="open_sub_id" id="openSub_id" type="text"
									value="" readonly>
							</div>
							<div class="col-sm-12 m10" style="margin-left: 63px;">
								<label style="padding-right: 15px;">출결배점</label><input
									class="form-control bajum01"
									style="width: 70px; display: inline-block; margin-left: 15px; margin-right: 15px;"
									value="20" type="text" name="b_chulsuk"> <label
									style="padding-right: 15px;">필기배점</label><input
									class="form-control bajum02"  name="b_filki"
									style="width: 70px; display: inline-block; margin-right: 15px;"
									value="30" type="text"> <label
									style="padding-right: 15px;">실기배점</label><input
									class="form-control bajum03"  name="b_silki"
									style="width: 70px; display: inline-block; margin-right: 15px;"
									value="50" type="text">
							</div>
							<label class="control-label col-sm-3 m10">시험일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control" id="testmoddate"
									name="testdate" placeholder="시험일(YYYY-MM-DD)"
									required="required" value="2017/07/29">
							</div>
							<label class="control-label col-sm-3 m10">시험문제</label>
							<div class="col-sm-9 m10">
								<button type="button" id="munjaeBtn" class="btn btn-default"
									style="margin: -2px">파일선택</button>
								<input type="text" class="form-control" id="test_munjae"
									name="test_munjae" value=""
									style="width: 80%; display: inline-block; vertical-align: middle;"
									required="required" readonly> <input type="file"
									class="form-control" id="test_munjae_act"
									name="test_munjae_act" style="display: none;"
									> <span>only .zip</span>
							</div>
						</div>

					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-default">수정</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					</div>
				</form>
			</div>

		</div>

	</div>
	
		
</body>
</html>