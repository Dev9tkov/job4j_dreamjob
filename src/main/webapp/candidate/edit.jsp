<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="java.util.Optional" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <script>
        $(document).ready(function () {
            $.ajax({
                url: "http://localhost:8080/dreamjob/city",
                method: "get",
                complete: function (data) {
                    var result = "<option></option>";
                    var countries = JSON.parse(data.responseText);
                    for (var i = 0; i < countries.length; i++) {
                        result += "<option value=\"" + countries[i] + "\">" + countries[i] + "</option>";
                    }
                    document.getElementById("country").innerHTML = result;
                }
            });
        });
        function getCity() {
            $.ajax({
                url: "http://localhost:8080/dreamjob/city",
                method: "post",
                data: {"country" : $("#country").val()},
                complete: function (data) {
                    var result = "<option></option>";
                    var cities = JSON.parse(data.responseText);
                    for (var i = 0; i < cities.length; i++) {
                        result += "<option value=\"" + cities[i] + "\">" + cities[i] + "</option>";
                    }
                    document.getElementById("city").innerHTML = result;
                }
            });
        }
    </script>

    <title>Работа мечты</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "");
    if (id != null) {
        Optional<Candidate> rsl = PsqlStore.instOf().findByIdCan(Integer.valueOf(id));
        candidate = rsl.get();
    }
%>
<div class="container pt-3">
    <div class="row">
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
        </li>
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                <h3>Новый кандидат</h3>
                <% } else { %>
                Редактирование кандидата.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidates.do?id=<%=candidate.getId()%>" method="post" enctype="multipart/form-data">
                    <div class="form-group row">
                        <label class="control-label col-sm-1" >Имя:</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="name" value="<%=candidate.getName()%>">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="control-label col-sm-1" for="country">Страна:</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="country" name="country" onchange="getCity()" required></select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="control-label col-sm-1" for="city">Город:</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="city" name="city" required></select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Фото</label><br>
                        <input type="file" class="btn btn-default" name="photoId" value="<%=candidate.getPhotoId()%>">
                    </div>
                    <div class="form-group">
                    </div>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
