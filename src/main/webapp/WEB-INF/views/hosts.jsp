<c:set var="pageTitle" value="Hosts" />
<c:set var="activeNav" value="hosts" />
<%@ include file="header.jspf" %>

<main class="container">
    <div class="page-header">
        <div>
            <h1>Network Hosts</h1>
            <p class="page-subtitle">Search, filter, and audit tracked devices.</p>
        </div>
        <a href="${ctx}/hosts/add" class="btn btn-primary">+ Add host</a>
    </div>

    <c:if test="${not empty flashMessage}">
        <div class="alert alert-success"><c:out value="${flashMessage}"/></div>
    </c:if>

    <form method="get" action="${ctx}/hosts" class="filter-bar">
        <input type="text" name="q" placeholder="Search IP, hostname, OS, or notes..." value="<c:out value='${q}'/>">
        <select name="risk">
            <option value="All" ${risk == 'All' ? 'selected' : ''}>All Risk Levels</option>
            <option value="High" ${risk == 'High' ? 'selected' : ''}>High Risk</option>
            <option value="Medium" ${risk == 'Medium' ? 'selected' : ''}>Medium Risk</option>
            <option value="Low" ${risk == 'Low' ? 'selected' : ''}>Low Risk</option>
        </select>
        <button type="submit" class="btn btn-secondary">Filter</button>
        <c:if test="${not empty q or risk != 'All'}">
            <a href="${ctx}/hosts" class="btn btn-secondary">Reset</a>
        </c:if>
    </form>

    <div class="card" style="padding: 0;">
        <div class="table-wrap">
            <table>
                <thead>
                    <tr>
                        <th>IP Address</th>
                        <th>Hostname</th>
                        <th>Operating System</th>
                        <th>Status</th>
                        <th>Risk Level</th>
                        <th>Ports</th>
                        <th>Added On</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty hosts}">
                            <tr>
                                <td colspan="8" style="text-align: center; color: var(--text-secondary); padding: 2rem;">
                                    No hosts found.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="h" items="${hosts}">
                                <tr>
                                    <td><a href="${ctx}/host?id=${h.id}" class="mono"><c:out value="${h.ipAddress}"/></a></td>
                                    <td><c:out value="${h.hostname}" default="-"/></td>
                                    <td><c:out value="${h.operatingSystem}" default="-"/></td>
                                    <td><span class="badge status-${fn:toLowerCase(h.status)}"><c:out value="${h.status}"/></span></td>
                                    <td><span class="badge risk-${fn:toLowerCase(h.riskLevel)}"><c:out value="${h.riskLevel}"/></span></td>
                                    <td>${h.portCount}</td>
                                    <td><fmt:formatDate value="${h.createdAt}" pattern="dd MMM yyyy"/></td>
                                    <td class="col-actions">
                                        <a href="${ctx}/host?id=${h.id}" class="btn btn-secondary btn-sm">View</a>
                                        <a href="${ctx}/hosts/edit?id=${h.id}" class="btn btn-secondary btn-sm">Edit</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</main>

<%@ include file="footer.jspf" %>
