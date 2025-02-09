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
                    <h4>Quên mật khẩu</h4>
                </div>
                <div class="card-body">

                    <form action="/quen-mat-khau/kiem-tra-opt" method="post">
                        <!-- Email -->
                        <input type="hidden" id="email" name="email" value="${email}">
                        <div class="mb-3">

                            <label for="otp" >Otp</label>
                            <input type="number" class="form-control" id="otp" name="otp" placeholder="123456"" required>
                            <span id="otp" class="error" style="color: red"> ${erro3}</span>
                        </div>
                        <div class="text-center">
                            <button  type="submit"  class="btn btn-primary">Đăng Ký</button>
                        </div>  </form>


                </div>
            </div>
        </div>
    </div>

</body>
</html>



