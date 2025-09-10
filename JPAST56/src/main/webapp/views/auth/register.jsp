<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đăng ký tài khoản</title>
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
  <style>
    body{min-height:100vh;display:flex;align-items:center;justify-content:center;background:#f6f7fb}
    .card-form{max-width:760px;border:0;border-radius:18px;box-shadow:0 16px 48px rgba(0,0,0,.08)}
  </style>
</head>
<body>
<div class="card card-form w-100">
  <div class="card-body p-4 p-md-5">
    <div class="d-flex justify-content-between align-items-center mb-2">
      <h3 class="m-0">Tạo tài khoản</h3>
      <a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/login">
        <i class="bi bi-box-arrow-in-right me-1"></i> Đăng nhập
      </a>
    </div>
    <p class="text-muted mb-3">Điền đầy đủ thông tin bên dưới để đăng ký.</p>

    <!-- Lỗi tổng quát (nếu có) -->
    <c:if test="${not empty errors.global}">
      <div class="alert alert-danger d-flex align-items-center">
        <i class="bi bi-exclamation-triangle me-2"></i><div>${errors.global}</div>
      </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/register" novalidate>
      <div class="row g-3">
        <!-- USERNAME -->
        <div class="col-md-6">
          <label class="form-label">Tài khoản <span class="text-danger">*</span></label>
          <input name="username"
                 class="form-control ${not empty errors.username ? 'is-invalid' : ''}"
                 value="${prefill_username}" placeholder="Ví dụ: user03" required>
          <c:if test="${not empty errors.username}">
            <div class="invalid-feedback">${errors.username}</div>
          </c:if>
          <div class="form-text">3–30 ký tự gồm chữ/số/._-</div>
        </div>

        <!-- FULLNAME -->
        <div class="col-md-6">
          <label class="form-label">Họ tên</label>
          <input name="fullName" class="form-control ${not empty errors.fullName ? 'is-invalid' : ''}"
                 value="${prefill_fullName}" placeholder="Tên hiển thị">
          <c:if test="${not empty errors.fullName}">
            <div class="invalid-feedback">${errors.fullName}</div>
          </c:if>
        </div>

        <!-- PASSWORD -->
        <div class="col-md-6">
          <label class="form-label">Mật khẩu <span class="text-danger">*</span></label>
          <div class="input-group">
            <input id="pw" type="password" name="password"
                   class="form-control ${not empty errors.password ? 'is-invalid' : ''}"
                   required placeholder="Tối thiểu 6 ký tự">
            <button class="btn btn-outline-secondary" type="button" onclick="togglePw('pw', this)">
              <i class="bi bi-eye"></i>
            </button>
            <c:if test="${not empty errors.password}">
              <div class="invalid-feedback d-block">${errors.password}</div>
            </c:if>
          </div>
        </div>

        <!-- CONFIRM -->
        <div class="col-md-6">
          <label class="form-label">Xác nhận mật khẩu <span class="text-danger">*</span></label>
          <div class="input-group">
            <input id="cf" type="password" name="confirm"
                   class="form-control ${not empty errors.confirm ? 'is-invalid' : ''}"
                   required placeholder="Nhập lại mật khẩu">
            <button class="btn btn-outline-secondary" type="button" onclick="togglePw('cf', this)">
              <i class="bi bi-eye"></i>
            </button>
            <c:if test="${not empty errors.confirm}">
              <div class="invalid-feedback d-block">${errors.confirm}</div>
            </c:if>
          </div>
        </div>

        <!-- EMAIL -->
        <div class="col-12">
          <label class="form-label">Email <span class="text-danger">*</span></label>
          <input name="email" type="email"
                 class="form-control ${not empty errors.email ? 'is-invalid' : ''}"
                 value="${prefill_email}" placeholder="you@example.com" required>
          <c:if test="${not empty errors.email}">
            <div class="invalid-feedback">${errors.email}</div>
          </c:if>
        </div>
      </div>

      <div class="d-grid mt-4">
        <button class="btn btn-primary" type="submit">
          <i class="bi bi-person-plus me-1"></i> Đăng ký
        </button>
      </div>
      <div class="form-text mt-2">Sau khi đăng ký thành công, bạn sẽ được chuyển về trang đăng nhập.</div>
    </form>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
function togglePw(id, btn){
  const inp = document.getElementById(id);
  const eye = btn.querySelector('i');
  const toText = inp.type === 'password';
  inp.type = toText ? 'text' : 'password';
  eye.className = toText ? 'bi bi-eye-slash' : 'bi bi-eye';
}
</script>
</body>
</html>
