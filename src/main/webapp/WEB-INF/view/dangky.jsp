<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>

</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Ký Người Dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-header text-center bg-primary text-white">
                    <h4>Đăng Ký Tài Khoản</h4>
                </div>
                <div class="card-body">


<form action="/dang-ky/success" method="post" >

<%--    <div class="mb-3">--%>

<%--        <label for="email" >Email</label>--%>
        <input type="text" class="form-control" id="email" name="email" placeholder="user@example.com" value="${email}" required>
<%--        <div class="invalid-feedback">Email không được để trống và phải đúng định dạng.</div>--%>
<%--        <span id="email" class="error" style="color: red"> ${erro}</span>--%>
<%--    </div>--%>

    <!-- Tài khoản -->
    <div class="mb-3">
    <label for="taiKhoan" >Tên người dùng</label>
    <input type="text" class="form-control" id="tenNguoiDung" name="tenNguoiDung" value="${user.tenNguoiDung}"required>
<%--        <div class="invalid-feedback">Tài khoản không được để trống.</div>--%>
        <span style="color: red"> ${erro4}</span>
    </div>
    <div class="mb-3">
        <label for="taiKhoan" >Số điện thoại</label>
        <input type="text" class="form-control" id="SDT" name="SDT" value="${user.SDT}"required>
<%--        <div class="invalid-feedback">Tài khoản không được để trống.</div>--%>
        <span style="color: red"> ${erro3}</span>
    </div>
    <div class="mb-3">
        <label for="taiKhoan" >Tài Khoản</label>
        <input type="text" class="form-control" id="taiKhoan" name="taiKhoan" placeholder="username123" value="${user.taiKhoan}"required>
        <div class="invalid-feedback">Tài khoản không được để trống.</div>
        <span id="email" class="error" style="color: red"> ${erro1}</span>
    </div>

    <!-- Mật khẩu -->
    <div class="mb-3">
        <label for="matKhau" >Mật khẩu</label>
        <input type="password" class="form-control" id="matKhau" name="matKhau" placeholder="Ít nhất 6 ký tự" minlength="6" required>
        <div class="invalid-feedback">Mật khẩu phải có ít nhất 6 ký tự.</div>
    </div>
    <div class="mb-3">
        <label for="matKhau" >Nhập lại mật khẩu</label>
        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Ít nhất 6 ký tự" minlength="6" required>
        <div class="error-message" id="passwordError"></div>
        <span id="email" class="error" style="color: red"> ${erro2}</span>

    </div>


    <!-- Submit -->
    <div class="text-center">
        <button  type="submit"  class="btn btn-primary">Đăng Ký</button>
    </div>
</form>

            </div>
        </div>
    </div>
</div>

</body>
</html>



