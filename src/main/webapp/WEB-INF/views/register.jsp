<c:set var="pageTitle" value="Create account" />
<%@ include file="header.jspf" %>

<div class="auth-wrapper">
    <div class="auth-card">
        <h1>Create account</h1>
        <p class="auth-subtitle">Register to start tracking devices on your network.</p>

        <form method="post" action="${ctx}/register" data-validate novalidate>
            <div class="form-group">
                <label for="fullName">Full name</label>
                <input type="text" id="fullName" name="fullName" data-rule="fullName"
                       value="<c:out value='${fullName}'/>">
                <c:if test="${not empty errors.fullName}">
                    <span class="field-error"><c:out value="${errors.fullName}"/></span>
                </c:if>
            </div>

            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" data-rule="username"
                       value="<c:out value='${username}'/>">
                <c:if test="${not empty errors.username}">
                    <span class="field-error"><c:out value="${errors.username}"/></span>
                </c:if>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" data-rule="email"
                       value="<c:out value='${email}'/>">
                <c:if test="${not empty errors.email}">
                    <span class="field-error"><c:out value="${errors.email}"/></span>
                </c:if>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" data-rule="password">
                <span class="field-hint">At least 8 characters, with a letter and a digit.</span>
                <c:if test="${not empty errors.password}">
                    <span class="field-error"><c:out value="${errors.password}"/></span>
                </c:if>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" data-rule="confirmPassword">
                <c:if test="${not empty errors.confirmPassword}">
                    <span class="field-error"><c:out value="${errors.confirmPassword}"/></span>
                </c:if>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Create account</button>
        </form>

        <div class="auth-footer">
            Already registered? <a href="${ctx}/login">Sign in</a>
        </div>
    </div>
</div>

<%@ include file="footer.jspf" %>
