<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đăng nhập</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap & Icons -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
  <style>
    :root { --bg1:#141e30; --bg2:#243b55; }
    body{
      min-height:100vh;
      background: linear-gradient(135deg,var(--bg1),var(--bg2));
      display:flex; align-items:center; justify-content:center;
      color:#1c1d21;
    }
    .login-card{
      width:100%; max-width: 880px;
      background: rgba(255,255,255,.92);
      backdrop-filter: blur(6px);
      border-radius: 18px;
      box-shadow: 0 18px 55px rgba(0,0,0,.25);
      overflow: hidden;
    }
    .left-panel{
      background: url('https://images.unsplash.com/photo-1515879218367-8466d910aaa4?q=80&w=1200&auto=format&fit=crop') center/cover no-repeat;
      min-height: 100%;
    }
    .brand{
      font-weight:800;
      letter-spacing:.3px;
      color:#0d6efd;
    }
    .quick-chip{ border-radius: 999px; }
    .form-label{ font-weight:600; }
    .form-check-label{ user-select: none; }
    .hint{ font-size:.9rem; opacity:.8 }
    .footer-note{ font-size:.85rem; opacity:.75 }
    .btn-primary:disabled .spinner-border{ --bs-spinner-width:1rem; --bs-spinner-height:1rem; }
  </style>
</head>
<body>

<div class="login-card row g-0">
  <!-- Left visual -->
  <div class="col-lg-5 d-none d-lg-block left-panel position-relative">
    <div class="position-absolute bottom-0 start-0 p-4 text-white">
      <div class="fw-bold fs-5">Chào mừng quay lại 👋</div>
      <div class="opacity-75">Đăng nhập để quản lý danh mục & hình ảnh.</div>
    </div>
  </div>

  <!-- Right form -->
  <div class="col-12 col-lg-7 p-4 p-md-5 bg-white">
    <div class="d-flex align-items-center mb-3">
      <i class="bi bi-box-arrow-in-right fs-3 text-primary me-2"></i>
      <div class="brand fs-4">JPA Demo</div>
    </div>

    <h3 class="mb-2">Đăng nhập</h3>
    <div class="hint mb-3">Dùng tài khoản mẫu hoặc tài khoản của bạn.</div>

    <!-- Thông báo lỗi từ server -->
    <c:if test="${not empty error}">
      <div class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle me-2"></i>
        <div>${error}</div>
      </div>
    </c:if>
<!-- Flash thành công sau khi đăng ký -->
<c:if test="${not empty sessionScope.flash}">
  <div class="alert alert-success d-flex align-items-center" role="alert">
    <i class="bi bi-check-circle me-2"></i>
    <div>${sessionScope.flash}</div>
  </div>
  <c:remove var="flash" scope="session"/>
</c:if>

    <!-- Nút điền nhanh demo accounts -->
    <div class="mb-3 d-flex flex-wrap gap-2">
      <button type="button" class="btn btn-outline-secondary btn-sm quick-chip"
              data-username="admin" data-password="admin123">
        <i class="bi bi-person-gear me-1"></i> Admin
      </button>
      <button type="button" class="btn btn-outline-secondary btn-sm quick-chip"
              data-username="manager01" data-password="manager123">
        <i class="bi bi-people-fill me-1"></i> Manager
      </button>
      <button type="button" class="btn btn-outline-secondary btn-sm quick-chip"
              data-username="user01" data-password="user123">
        <i class="bi bi-person me-1"></i> user01
      </button>
      <button type="button" class="btn btn-outline-secondary btn-sm quick-chip"
              data-username="user02" data-password="user123">
        <i class="bi bi-person me-1"></i> user02
      </button>
    </div>

    <form id="loginForm" method="post" action="${pageContext.request.contextPath}/login" novalidate>
      <div class="mb-3">
        <label class="form-label" for="username">Tài khoản</label>
        <input id="username" name="username" class="form-control" placeholder="VD: admin"
               autocomplete="username" required>
        <div class="invalid-feedback">Vui lòng nhập tài khoản.</div>
      </div>

      <div class="mb-3">
        <label class="form-label" for="password">Mật khẩu</label>
        <div class="input-group">
          <input id="password" name="password" type="password" class="form-control"
                 placeholder="••••••••" autocomplete="current-password" required>
          <button class="btn btn-outline-secondary" type="button" id="togglePw" tabindex="-1">
            <i class="bi bi-eye"></i>
          </button>
          <div class="invalid-feedback">Vui lòng nhập mật khẩu.</div>
        </div>
      </div>

      <div class="d-flex justify-content-between align-items-center mb-3">
        <div class="form-check">
          <input class="form-check-input" type="checkbox" value="1" id="rememberMe">
          <label class="form-check-label" for="rememberMe">Ghi nhớ tài khoản</label>
        </div>
        <a class="link-secondary small text-decoration-none" href="#" onclick="event.preventDefault(); alert('Tính năng quên mật khẩu sẽ bổ sung sau.');">Quên mật khẩu?</a>
      </div>

      <button id="submitBtn" class="btn btn-primary w-100" type="submit">
        <span class="btn-text">Đăng nhập</span>
        <span class="spinner-border spinner-border-sm ms-2 d-none" role="status" aria-hidden="true"></span>
      </button>
<div class="text-center mt-3">
  Chưa có tài khoản?
  <a class="link-primary text-decoration-none" href="${pageContext.request.contextPath}/register">
    Đăng ký ngay
  </a>
</div>

      <div class="footer-note text-center mt-3">
        Demo accounts: <code>admin/admin123</code>, <code>manager01/manager123</code>,
        <code>user01/user123</code>, <code>user02/user123</code>
      </div>
    </form>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // --- Show/Hide password
  const pw = document.getElementById('password');
  const togglePw = document.getElementById('togglePw');
  togglePw.addEventListener('click', () => {
    const isPwd = pw.type === 'password';
    pw.type = isPwd ? 'text' : 'password';
    togglePw.querySelector('i').className = isPwd ? 'bi bi-eye-slash' : 'bi bi-eye';
    pw.focus();
  });

  // --- Quick fill demo accounts
  document.querySelectorAll('.quick-chip').forEach(btn => {
    btn.addEventListener('click', () => {
      document.getElementById('username').value = btn.dataset.username;
      document.getElementById('password').value = btn.dataset.password;
      document.getElementById('username').focus();
    });
  });

  // --- Remember username (localStorage)
  const REMEMBER_KEY = 'login_username';
  const usernameEl = document.getElementById('username');
  const rememberEl = document.getElementById('rememberMe');

  // Load remembered username
  const remembered = localStorage.getItem(REMEMBER_KEY);
  if (remembered) {
    usernameEl.value = remembered;
    rememberEl.checked = true;
  }

  // --- Form validation + spinner
  const form = document.getElementById('loginForm');
  const submitBtn = document.getElementById('submitBtn');
  form.addEventListener('submit', (e) => {
    if (!form.checkValidity()) {
      e.preventDefault(); e.stopPropagation();
      form.classList.add('was-validated');
      return;
    }
    // Save/remove remembered username
    if (rememberEl.checked) localStorage.setItem(REMEMBER_KEY, usernameEl.value.trim());
    else localStorage.removeItem(REMEMBER_KEY);

    // Show spinner, disable button
    submitBtn.disabled = true;
    submitBtn.querySelector('.spinner-border').classList.remove('d-none');
    submitBtn.querySelector('.btn-text').textContent = 'Đang xử lý...';
  });

  // Enter -> submit (mặc định), nhưng đảm bảo validation chạy đẹp
  ['username','password'].forEach(id=>{
    document.getElementById(id).addEventListener('keydown', e=>{
      if(e.key==='Enter'){ form.requestSubmit(); }
    })
  });
</script>
</body>
</html>
