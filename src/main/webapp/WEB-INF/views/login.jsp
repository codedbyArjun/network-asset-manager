<c:set var="pageTitle" value="Login" />
<%@ include file="header.jspf" %>

<div class="auth-wrapper">
    <div class="auth-card">
        <h1>Sign in</h1>
        <p class="auth-subtitle">Log in to manage your network assets.</p>

        <c:if test="${not empty flashMessage}">
            <div class="alert alert-success"><c:out value="${flashMessage}"/></div>
        </c:if>

        <c:if test="${not empty loginError}">
            <div class="alert alert-danger"><c:out value="${loginError}"/></div>
        </c:if>

        <form method="post" action="${ctx}/login" data-validate novalidate>
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" data-rule="loginUsername"
                       value="<c:out value='${username}'/>" autocomplete="username">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" data-rule="loginPassword"
                       autocomplete="current-password">
            </div>

            <button type="submit" class="btn btn-primary btn-block">Sign in</button>
        </form>

        <div class="auth-footer">
            New here? <a href="${ctx}/register">Create an account</a>
        </div>
    </div>
</div>

<%@ include file="footer.jspf" %>
