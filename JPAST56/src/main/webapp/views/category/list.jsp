<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Danh sách Category</title>
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .container-narrow{max-width:980px}
    .img-thumb{width:96px;height:72px;object-fit:cover;border-radius:8px}
    .navbar-brand{font-weight:700}
    .table>:not(caption)>*>*{vertical-align:middle}
  </style>
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<nav class="navbar navbar-expand-lg bg-body-tertiary border-bottom">
  <div class="container">
    <a class="navbar-brand" href="${ctx}/user/home">JPA Demo</a>
    <div class="collapse navbar-collapse show">
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link" href="${ctx}/user/home">User Home</a></li>
        <li class="nav-item"><a class="nav-link" href="${ctx}/manager/home">Manager Home</a></li>
        <li class="nav-item"><a class="nav-link" href="${ctx}/admin/home">Admin Home</a></li>
      </ul>
      <div class="d-flex">
        <c:if test="${not empty sessionScope.currentUser}">
          <span class="me-3">👤 <b>${sessionScope.currentUser.username}</b> (role ${sessionScope.currentUser.roleId})</span>
          <a class="btn btn-outline-danger btn-sm" href="${ctx}/logout">Đăng xuất</a>
        </c:if>
      </div>
    </div>
  </div>
</nav>

<div class="container container-narrow py-4">
  <c:if test="${not empty sessionScope.flash}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      ${sessionScope.flash}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <c:remove var="flash" scope="session"/>
  </c:if>
  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="m-0">Danh sách Category</h3>
    <a href="${ctx}/category/add" class="btn btn-success">+ Thêm Category</a>
  </div>

  <table class="table table-hover table-bordered bg-white">
    <thead class="table-light">
      <tr>
        <th style="width:70px">ID</th>
        <th>Tên</th>
        <th style="width:140px">Ảnh</th>
        <th>Owner</th>
        <th style="width:190px">Hành động</th>
      </tr>
    </thead>
    <tbody>
      <c:set var="me" value="${sessionScope.currentUser}"/>
      <c:forEach items="${listcate}" var="c">
        <tr>
          <td>${c.id}</td>
          <td>${c.categoryname}</td>
          <td>
            <c:choose>
              <c:when test="${not empty c.images}">
                <img src="${ctx}/uploads/${c.images}" alt="img" class="img-thumb">
              </c:when>
              <c:otherwise><span class="text-muted">Chưa có</span></c:otherwise>
            </c:choose>
          </td>
          <td><span class="badge text-bg-secondary">${c.owner.username}</span></td>
          <td>
            <c:if test="${me.roleId == 3 || me.id == c.owner.id}">
              <a class="btn btn-sm btn-primary" href="${ctx}/category/edit?id=${c.id}">Sửa</a>
              <a class="btn btn-sm btn-outline-danger"
                 href="${ctx}/category/delete?id=${c.id}"
                 onclick="return confirm('Xóa category này?')">Xóa</a>
            </c:if>
            <c:if test="${me.roleId != 3 && me.id != c.owner.id}">
              <span class="text-muted">Không có quyền</span>
            </c:if>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
