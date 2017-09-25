<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
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
		
var txt="";
		
		$(".infomodifybtn").on("click", function(){
			var student_phone = $(".student_phone").val();
			var pw = $(this).parent().prev().find(".pw").val();
			
			$.ajax({
				url:"${pageContext.request.contextPath}/studentinfomodify.do",
				data:{"student_phone":student_phone, "pw":pw},
				success:function(data){
					if(data=="false"){
						$("#modify-Modal-false").modal("show");
					}else{
						$("#modify-Modal-success").modal("show");
					}
					
					
				}});			
		});
		
		$(".pwmodifybtn").on("click", function(){
			var pw = $(this).prev().val();
			
			$.ajax({
				url:"${pageContext.request.contextPath}/studentpwmodify.do",
				data:{"pw":pw},
				success:function(data){
					if(data=="false"){
						$("#pw-Modal-false").modal("show");
					}else{
						$("#pw-mod-Modal").modal("show");
					}
					
					
				}});	
			
		});
		
		
		$(".pwmodify2").on("click", function(){
			var currentpw = $("#currentpw").val();
			var newpw = $("#newpw").val();
			var newpwcheck = $("#newpwcheck").val();
			
			$.ajax({
				url:"${pageContext.request.contextPath}/studentpwmodify2.do",
				data:{"currentpw":currentpw, "newpw":newpw , "newpwcheck":newpwcheck},
				success:function(data){
					if(data=="false"){
						$("#pw-Modal-false").modal("show");
					}else{
						$("#pw-mod-Modal").modal("show");
					}
					
					if(data=="false1") {
						$("#pw-Modal-false").modal("show");
						//modifyphone = "현재 암호가 틀렸습니다.";
					}else if(data=="false2") {
						$("#pw-Modal-false2").modal("show");
						//modifyphone = "현재 암호와 같은 암호입니다.";
					}else if(data=="false3") {
						$("#pw-Modal-false3").modal("show");
						//modifyphone = "변경할 암호를 같게 입력해야합니다. 다시 확인해주세요.";
					}else {
						$("#pw-Modal-success").modal("show");
						//modifyphone = "비밀번호를 변경했습니다.";
					}
					
				}});	
			
		});
		
		
		$('#pw-Modal-success').on('hidden.bs.modal', function () {
			location.href='${pageContext.request.contextPath}/studentinfo.do';
		})

	});
</script>
</head>
<body>


	<div id="main">
		<div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png" width="300px">
			<div class="login"><a href="${pageContext.request.contextPath}/studentinfo.do">[수강생]${studentinfo.student_name}</a> 님 │ 로그아웃</div>
		</div>
		<div id="menu">
			<div class="menu">
				<ul class="nav nav-pills nav-justified">
					<li><a href="${pageContext.request.contextPath}/studentmain.do">성적조회</a></li>
				</ul>
			</div>
		</div>
		
		<form class="form-horizontal">
		<div class="content">
		
		<div style="max-width:500px;margin:auto;">

				<h3 style="text-align:center;margin-bottom:30px;">개인 정보</h3>


				<div class="form-group">
					<label class="control-label col-sm-3">아이디</label>
					<div class="col-sm-9">
						<input class="form-control" id="focusedInput" type="text" value="${sessionScope.logininfo.id_}" readonly>
					</div>
					<label class="control-label col-sm-3 m10">비밀번호</label>
					<div class="col-sm-9 m10">
						<input class="form-control pw" id="pw" type="password" name="pw" style="max-width:309px; display: inline; value="124" required">
						<button type="button" class="btn btn-default pwmodifybtn">변경</button>
					</div>
					<label class="control-label col-sm-3 m10">이름</label>
					<div class="col-sm-9 m10">
						<input class="form-control" id="focusedInput" type="text" value="${stuinfo.student_name}" readonly>
					</div>
					<label class="control-label col-sm-3 m10">주민번호 뒷자리</label>
					<div class="col-sm-9 m10">
						<input class="form-control" id="focusedInput" type="text" value="${stuinfo.student_ssn}" readonly>
					</div>
					<label class="control-label col-sm-3 m10">전화번호</label>
					<div class="col-sm-9 m10">
						<input class="form-control student_phone" id="student_phone" type="text" value="${stuinfo.student_phone}">
					</div>
					</div>
					<label class="control-label col-sm-3 m10">과정 수강 횟수</label>
					<div class="col-sm-9 m10">
						<input class="form-control" id="focusedInput" type="text" value="${stuinfo.course_count }" readonly>
					</div>
				</div>

			</div>
			<div class="modal-footer" style="border-top:none;">
				<button type="button" class="btn btn-default infomodifybtn">수정</button>
			</div>
			
		
		
		</div>	
		</form>

		
	</div>




	<!-- 비밀 번호 변경 Modal -->
	<div class="modal fade" id="pw-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">비밀 번호 변경</h5>
				</div>
				<form class="form-horizontal">
				<div class="modal-body">
						<div class="form-group">
							<label class="control-label col-sm-3">현재 비밀 번호</label>
							<div class="col-sm-9">
								<input class="form-control" id="currentpw" name="currentpw" type="password">
							</div>
							<label class="control-label col-sm-3 m10">새 비밀 번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="newpw" name="newpw" type="password">
							</div>
							<label class="control-label col-sm-3 m10">새 비밀 번호 확인</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="newpwcheck" name="newpwcheck" type="password">
							</div>
						</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pwmodify2">변경</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
				</form>
			</div>

		</div>
	</div>
	
	
	
		<!-- 수정 실패 Modal >전화번호 -->
	<div class="modal fade" id="modify-Modal-false" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">수정 실패</h3>
				</div>
				<input type='hidden' name='book_id' class="book_id" value="">
				<div class="modal-body">

					<h4 style="text-align: center; font-weight: bold;">비밀번호가 틀렸습니다.</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>
	
	
	
		<!-- 수정 성공 Modal > 전화번호 -->
	<div class="modal fade" id="modify-Modal-success" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">수정 성공</h3>
				</div>
				<input type='hidden' name='book_id' class="book_id" value="">
				<div class="modal-body">

					<h4 style="text-align: center; font-weight: bold;">정보가 수정되었습니다.</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>



	<!-- 비밀번호 확인 Modal -->
	<div class="modal fade" id="pw-Modal-false" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">비밀번호 확인 요망</h3>
				</div>
				<input type='hidden' name='book_id' class="book_id" value="">
				<div class="modal-body">

					<h4 style="text-align: center; font-weight: bold;">비밀번호가 틀렸습니다.</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>
	
	
	
	
	
	<!-- 비밀번호 변경 오류2 Modal -->
	<div class="modal fade" id="pw-Modal-false2" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">비밀번호 확인 요망</h3>
				</div>
				<input type='hidden' name='book_id' class="book_id" value="">
				<div class="modal-body">

					<h4 style="text-align: center; font-weight: bold;">현재 비밀번호와 새 비밀번호가 같습니다.</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>
	


<!-- 비밀번호 변경 오류3 Modal -->
	<div class="modal fade" id="pw-Modal-false3" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">비밀번호 확인 요망</h3>
				</div>
				<input type='hidden' name='book_id' class="book_id" value="">
				<div class="modal-body">

					<h4 style="text-align: center; font-weight: bold;">변경할 비밀번호가 일치하지 않습니다.</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>
	
	
	<!-- 비밀번호 변경 완료 Modal -->
	<div class="modal fade" id="pw-Modal-success" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">성공</h3>
				</div>
				<input type='hidden' name='book_id' class="book_id" value="">
				<div class="modal-body">

					<h4 style="text-align: center; font-weight: bold;">비밀번호를 변경하였습니다.</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>



</body>
</html>