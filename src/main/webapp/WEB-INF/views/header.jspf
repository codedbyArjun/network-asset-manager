<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${pageTitle}" default="Network Asset Manager"/> | Network Asset Manager</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="nav-container">
        <div class="nav-left">
            <a href="${ctx}/dashboard" class="brand">
                <span class="brand-dot"></span>
                <span>NetAsset Manager</span>
            </a>
            <c:if test="${not empty sessionScope.user}">
                <div class="nav-links">
                    <a href="${ctx}/dashboard" class="${activeNav == 'dashboard' ? 'active' : ''}">Dashboard</a>
                    <a href="${ctx}/hosts" class="${activeNav == 'hosts' ? 'active' : ''}">Hosts</a>
                    <a href="${ctx}/hosts/add" class="${activeNav == 'addHost' ? 'active' : ''}">Add Host</a>
                </div>
            </c:if>
        </div>
        <c:if test="${not empty sessionScope.user}">
            <div class="nav-right">
                <span><c:out value="${sessionScope.user.username}"/></span>
                <a href="${ctx}/logout" class="btn btn-secondary btn-sm">Logout</a>
            </div>
        </c:if>
    </div>
</nav>
