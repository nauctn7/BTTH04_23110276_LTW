<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Sửa Category</title>
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>.card-form{max-width:720px;margin:24px auto}.img-preview{max-width:220px;border-radius:8px}.img-thumb{width:120px;height:90px;object-fit:cover;border-radius:8px}</style>
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
          <span class="me-3">👤 <b>${sessionScope.currentUser.username}</b></span>
          <a class="btn btn-outline-danger btn-sm" href="${ctx}/logout">Đăng xuất</a>
        </c:if>
      </div>
    </div>
  </div>
</nav>

<div class="container">
  <div class="card card-form shadow-sm">
    <div class="card-body">
      <h3 class="mb-3">Sửa Category #${category.id}</h3>
      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>
      <form method="post" action="${ctx}/category/edit" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${category.id}">
        <div class="mb-3">
          <label class="form-label">Tên category</label>
          <input name="name" class="form-control" value="${category.categoryname}" required>
        </div>
        <div class="mb-3">
          <label class="form-label d-block">Ảnh hiện tại</label>
          <c:choose>
            <c:when test="${not empty category.images}">
              <img src="${ctx}/uploads/${category.images}" class="img-thumb mb-2" alt="current">
            </c:when>
            <c:otherwise><span class="text-muted">Chưa có</span></c:otherwise>
          </c:choose>
        </div>
        <div class="mb-3">
          <label class="form-label">Tải ảnh mới (nếu muốn thay)</label>
          <input type="file" name="imageFile" class="form-control" accept="image/*" onchange="preview(this)">
          <div class="mt-2"><img id="previewImg" class="img-preview" style="display:none"></div>
        </div>
        <button class="btn btn-primary">Cập nhật</button>
        <a class="btn btn-link" href="javascript:history.back()">Hủy</a>
      </form>
    </div>
  </div>
</div>

<script>
function preview(input){
  const img = document.getElementById('previewImg');
  if (input.files && input.files[0]) {
    img.src = URL.createObjectURL(input.files[0]);
    img.style.display = 'block';
  } else {
    img.src = ''; img.style.display = 'none';
  }
}
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
