<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="<c:url value="/static/css/style.css" />">
    <link rel="stylesheet" href="<c:url value="/static/css/mdl-modal.css" />">
    <link rel="stylesheet" href="<c:url value="/static/css/preloader.css" />">
    <style>
        #view-source {
            position: fixed;
            display: block;
            right: 0;
            bottom: 0;
            margin-right: 40px;
            margin-bottom: 40px;
            z-index: 900;
        }
    </style>

    <title>Library</title>
</head>
<body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="mdl-layout__header mdl-layout__header--scroll mdl-color--primary">
        <div class="mdl-layout--large-screen-only mdl-layout__header-row">
        </div>
        <div class="mdl-layout--large-screen-only mdl-layout__header-row">
            <h3>Библиотека</h3>
        </div>
        <div class="mdl-layout--large-screen-only mdl-layout__header-row">
        </div>
        <div class="mdl-layout__tab-bar mdl-js-ripple-effect mdl-color--primary-dark">
            <a href="<c:url value="/" />" class="mdl-layout__tab">Книги</a>
            <a href="<c:url value="/users" />" class="mdl-layout__tab is-active">Пользователи</a>
            <a href="<c:url value="/logout" />" class="mdl-layout__tab logout-tab">Выйти</a>
            <button class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored mdl-shadow--4dp mdl-color--accent"
                    id="add" onclick="showModal()">
                <i class="material-icons" role="presentation">add</i>
                <span class="visuallyhidden">Add</span>
            </button>
        </div>
    </header>
    <main class="mdl-layout__content">
        <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
            <thead>
            <tr>
                <th class="mdl-data-table__cell--non-numeric">Имя пользователя</th>
                <th>Удалить</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td class="mdl-data-table__cell--non-numeric cursor-pointer"
                        onclick="prepareEditModal('${user.getId()}')">${user.getName()}</td>
                    <td>
                        <button class="mdl-js-ripple-effect mdl-button mdl-js-button mdl-button--raised mdl-button--colored"
                                onclick="deleteUser(${user.getId()});">
                            Удалить
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="cssload-thecube">
            <div class="cssload-cube cssload-c1"></div>
            <div class="cssload-cube cssload-c2"></div>
            <div class="cssload-cube cssload-c4"></div>
            <div class="cssload-cube cssload-c3"></div>
        </div>
        <%--<div>--%>
        <%--<button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored show-more-button"--%>
        <%--onclick="showMore(1, '${currentUser}')">--%>
        <%--Показать ещё--%>
        <%--</button>--%>
        <%--</div>--%>
        <footer class="mdl-mega-footer">
            <div class="mdl-mega-footer--bottom-section">
                <div class="mdl-logo">
                    by Evgeniy Evzerov
                </div>
            </div>
        </footer>
    </main>
</div>

<script src="<c:url value="/static/js/script_users.js" />"></script>
<script src="<c:url value="/static/js/mdl-modal.js" />"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>

</body>
</html>