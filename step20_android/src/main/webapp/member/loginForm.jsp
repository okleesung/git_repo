<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
<form action="login.do" method="post"> 
	<table border="1">
		<tr>
			<td width="70" align="center">아이디</td>
			<td><input type="text" name="id" size="30"></td>
		</tr>
		<tr>	
			<td width="70" align="center">비밀번호</td>
			<td><input type="password" name="pwd" size="30"></td>
		</tr>
		<tr>	
			<td colspan="2" align="center">
				<input type="submit" value="로그인">
				<input type="button" value="회원가입" onclick="">
			</td>
		</tr>

	</table>
</form>
<a href="boardList.do">글 목록</a>
</body>
</html>