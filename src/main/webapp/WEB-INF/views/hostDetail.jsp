<c:set var="pageTitle" value="Host details" />
<c:set var="activeNav" value="hosts" />
<%@ include file="header.jspf" %>

<main class="container">
    <c:if test="${not empty flashMessage}">
        <div class="alert alert-success"><c:out value="${flashMessage}"/></div>
    </c:if>

    <div class="page-header">
        <div>
            <div style="font-size: 0.85rem; color: var(--text-secondary); margin-bottom: 0.25rem;">
                <a href="${ctx}/hosts">Hosts</a> / <span class="mono"><c:out value="${host.ipAddress}"/></span>
            </div>
            <div style="display: flex; align-items: center; gap: 0.85rem;">
                <h1 class="mono"><c:out value="${host.ipAddress}"/></h1>
                <span class="badge risk-${fn:toLowerCase(host.riskLevel)}"><c:out value="${host.riskLevel}"/> risk</span>
            </div>
        </div>
        <div class="col-actions">
            <a href="${ctx}/hosts/edit?id=${host.id}" class="btn btn-secondary">Edit</a>
            <form method="post" action="${ctx}/hosts/delete" onsubmit="return confirm('Delete this host and all its logged ports?');">
                <input type="hidden" name="id" value="${host.id}">
                <button type="submit" class="btn btn-danger">Delete</button>
            </form>
        </div>
    </div>

    <div class="card">
        <div class="meta-grid">
            <div>
                <div class="meta-label">Hostname</div>
                <div class="meta-value"><c:out value="${host.hostname}" default="-"/></div>
            </div>
            <div>
                <div class="meta-label">Operating System</div>
                <div class="meta-value"><c:out value="${host.operatingSystem}" default="-"/></div>
            </div>
            <div>
                <div class="meta-label">Status</div>
                <div class="meta-value">
                    <span class="badge status-${fn:toLowerCase(host.status)}"><c:out value="${host.status}"/></span>
                </div>
            </div>
            <div>
                <div class="meta-label">Added On</div>
                <div class="meta-value"><fmt:formatDate value="${host.createdAt}" pattern="dd MMM yyyy, HH:mm"/></div>
            </div>
        </div>

        <div>
            <div class="meta-label">Notes</div>
            <div class="meta-value" style="font-weight: 400; color: var(--text-secondary); white-space: pre-wrap;"><c:out value="${host.notes}" default="No notes recorded."/></div>
        </div>
    </div>

    <div class="card">
        <h2 class="card-title">Open ports &amp; services <span style="color: var(--text-secondary); font-weight: 400; margin-left: 0.35rem;">${fn:length(ports)}</span></h2>

        <div class="table-wrap" style="margin-bottom: 1.75rem;">
            <table>
                <thead>
                    <tr>
                        <th>Port</th>
                        <th>Service</th>
                        <th>Vulnerability / Note</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty ports}">
                            <tr>
                                <td colspan="4" style="text-align: center; color: var(--text-secondary); padding: 1.5rem;">
                                    No ports logged yet. Add the first one below.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="p" items="${ports}">
                                <tr>
                                    <td class="mono">${p.portNumber}</td>
                                    <td><c:out value="${p.serviceName}"/></td>
                                    <td style="color: var(--text-secondary);"><c:out value="${p.vulnerabilityNote}" default="-"/></td>
                                    <td>
                                        <form method="post" action="${ctx}/ports/delete">
                                            <input type="hidden" name="hostId" value="${host.id}">
                                            <input type="hidden" name="portId" value="${p.id}">
                                            <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <h3 style="font-size: 0.98rem; margin-bottom: 1rem;">Log a port</h3>
        <form method="post" action="${ctx}/ports/add" data-validate novalidate>
            <input type="hidden" name="hostId" value="${host.id}">
            <div class="form-row">
                <div class="form-group">
                    <label for="portNumber">Port number *</label>
                    <input type="number" id="portNumber" name="portNumber" data-rule="portNumber" placeholder="1 - 65535" min="1" max="65535">
                </div>
                <div class="form-group">
                    <label for="serviceName">Service *</label>
                    <input type="text" id="serviceName" name="serviceName" data-rule="serviceName" placeholder="e.g. SSH">
                </div>
            </div>
            <div class="form-group">
                <label for="vulnerabilityNote">Vulnerability / Note</label>
                <input type="text" id="vulnerabilityNote" name="vulnerabilityNote" placeholder="e.g. OpenSSH 8.9p1 - banner exposed">
            </div>
            <button type="submit" class="btn btn-primary">Add Port</button>
        </form>
    </div>
</main>

<%@ include file="footer.jspf" %>
