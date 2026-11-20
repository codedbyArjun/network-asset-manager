<c:set var="pageTitle" value="Error" />
<%@ include file="header.jspf" %>

<main class="container" style="text-align: center; padding: 4rem 1rem;">
    <h1 style="font-size: 2.5rem; margin-bottom: 0.5rem;">Something went wrong</h1>
    <p class="page-subtitle" style="margin-bottom: 1.5rem;">The requested resource could not be found or an unexpected server error occurred.</p>
    <a href="${ctx}/dashboard" class="btn btn-primary">Return to Dashboard</a>
</main>

<%@ include file="footer.jspf" %>
