<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src = "/resources/js/jquery-3.4.1.min.js"></script>
<script>
	
	var loginChk = '${loginMsg}';
	
	console.log(loginChk);
	
	if(loginChk == 'idFail'){
		alert("아이디를 확인하세요");
		$("#logId").focus();
	}else if(loginChk == 'passFail'){
		alert("비밀번호를 제발 좀 확인하세요");
		$("#logPass").focus();
	}
	
	$(function(){
		
		
		
		$("#btn").click(function(){
			 	
			if($("#logId").val() == ''){
				alert('아이디를 입력하세요');
				$("#logId").focus();
			}else if($("#logPass").val() == ''){
				alert('비밀번호를 입력하세요');
				$("#logPass").focus();
			}else{
				$("#frm").attr("action","login").attr("method","post").submit();
			}
		})
	})
	
	
</script>
</head>
<body>
	
   	<form name = "frm" id = "frm">
   		<div>새로 등록한 로그인jsp</div>
		<div>
			아이디 : <input type = "text" name = "logId" id = "logId" placeholder="아이디를 입력하세요"><br>
			비밀번호 : <input type = "password" name = "logPass" id = "logPass" placeholder="비밀번호를 입력하세요"><br>
			<br>
		</div>
	</form>
	<div>
		<input type = "button" name = "btn" id = "btn" value = "로그인테스트">
	</div>                                                
	
</body>
</html>