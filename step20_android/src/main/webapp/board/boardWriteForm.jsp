<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="../script/boardScript.js"></script>
<body>
<form action="boardWrite.do" method="post" name="boardWriteForm">
	<table border="1">
		<tr>
			<th width="50">제목</th>
			<td><input type="text" name="subject" size="60"></td>
		</tr>
		<tr>
			<th width="50">내용</th>
			<td><textarea rows="15" cols="45" name="content"></textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" value="글쓰기" onclick="checkBoardWrite()">
				<input type="reset" value="다시작성">
			</td>
		</tr>
	</table>	
</form>
</body>
</html>