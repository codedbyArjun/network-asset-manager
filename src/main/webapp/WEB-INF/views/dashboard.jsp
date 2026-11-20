<c:set var="pageTitle" value="Dashboard" />
<c:set var="activeNav" value="dashboard" />
<%@ include file="header.jspf" %>

<main class="container">
    <div class="page-header">
        <div>
            <h1>Dashboard</h1>
            <p class="page-subtitle">Welcome back, <c:out value="${sessionScope.user.fullName}"/>. Here is your network at a glance.</p>
        </div>
        <a href="${ctx}/hosts/add" class="btn btn-primary">+ Add host</a>
    </div>

    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-label">Total Hosts</div>
            <div class="stat-value">${stats.total}</div>
        </div>
        <div class="stat-card">
            <div class="stat-label">Active</div>
            <div class="stat-value">${stats.active}</div>
            <div class="stat-sub">${stats.inactive} Inactive</div>
        </div>
        <div class="stat-card">
            <div class="stat-label">High Risk</div>
            <div class="stat-value danger">${stats.high}</div>
        </div>
        <div class="stat-card">
            <div class="stat-label">Ports Logged</div>
            <div class="stat-value">${stats.ports}</div>
        </div>
    </div>

    <div class="dashboard-split">
        <div class="card">
            <h2 class="card-title">Risk distribution</h2>
            <c:set var="denom" value="${stats.total > 0 ? stats.total : 1}" />

            <div class="risk-row">
                <span class="risk-label">High</span>
                <div class="risk-bar-bg">
                    <div class="risk-bar-fill high" style="width: ${(stats.high * 100) / denom}%"></div>
                </div>
                <span class="risk-count">${stats.high}</span>
            </div>

            <div class="risk-row">
                <span class="risk-label">Medium</span>
                <div class="risk-bar-bg">
                    <div class="risk-bar-fill medium" style="width: ${(stats.medium * 100) / denom}%"></div>
                </div>
                <span class="risk-count">${stats.medium}</span>
            </div>

            <div class="risk-row">
                <span class="risk-label">Low</span>
                <div class="risk-bar-bg">
                    <div class="risk-bar-fill low" style="width: ${(stats.low * 100) / denom}%"></div>
                </div>
                <span class="risk-count">${stats.low}</span>
            </div>
        </div>

        <div class="card">
            <h2 class="card-title">Recently added</h2>
            <c:choose>
                <c:when test="${empty recentHosts}">
                    <p class="page-subtitle">No hosts added yet.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="h" items="${recentHosts}">
                        <div class="recent-item">
                            <div class="recent-info">
                                <a href="${ctx}/host?id=${h.id}" class="mono"><c:out value="${h.ipAddress}"/></a>
                                <span class="recent-hostname"><c:out value="${h.hostname}" default="-"/></span>
                            </div>
                            <span class="badge risk-${fn:toLowerCase(h.riskLevel)}"><c:out value="${h.riskLevel}"/></span>
                        </div>
                    </c:forEach>
                    <div style="margin-top: 1rem;">
                        <a href="${ctx}/hosts" style="font-size: 0.88rem;">View all hosts &rarr;</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>

<%@ include file="footer.jspf" %>
