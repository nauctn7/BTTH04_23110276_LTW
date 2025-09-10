<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Category</title>
</head>
<body>

<h2>Danh sách Category</h2>

<c:forEach items="${listcate}" var="list" >
  ${list.id}
  <br>
  ${list.categoryname}
</c:forEach>

</body>
</html>
