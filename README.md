# BTTH04 – Lập trình Web (JPA + JSP/Servlet)

**Sinh viên**: Cáp Thanh Nhàn  
**MSSV**: 23110276  

---

## 🎯 Mục tiêu
Xây dựng ứng dụng web quản lý **Category** sử dụng **JPA/Hibernate** trên nền **JSP/Servlet**, hỗ trợ các chức năng:
- Đăng nhập / Đăng ký người dùng
- Phân quyền (RBAC: User, Manager, Admin)
- CRUD Categoryh
- Bảo mật với BCrypt

---

## ✅ Các chức năng đã hoàn thành

### 1. CSDL
- Bảng `Users (1)` – `Category (N)`
- `Category.user_id` → `Users.id` (FK)

### 2. Authentication
- Đăng ký (username, password, confirm, email)  
  - Kiểm tra trùng username  
  - Báo lỗi theo field  
- Đăng nhập / Đăng xuất  

### 3. Authorization (RBAC)
- Role:
  - `1 = User`
  - `2 = Manager`
  - `3 = Admin`
- Sau khi đăng nhập:
  - User → `/user/home`
  - Manager → `/manager/home`
  - Admin → `/admin/home`
- Filter:
  - `/admin/*` → chỉ Admin
  - `/manager/*` → Manager + Admin
  - Whitelist: `/login`, `/register`, `/uploads`, `/css`, `/js`, `/assets`, `/favicon`

### 4. Trang Home
- User, Admin → xem tất cả Category  
- Manager → chỉ xem Category của mình  

### 5. Quản lý Category
- Thêm / Sửa / Xem / Xóa (chỉ Admin hoặc chính chủ)  
- Upload ảnh → lưu tại thư mục `/uploads`

