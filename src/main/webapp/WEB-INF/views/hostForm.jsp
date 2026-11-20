<c:set var="pageTitle" value="${mode == 'edit' ? 'Edit Host' : 'Add Host'}" />
<c:set var="activeNav" value="${mode == 'edit' ? 'hosts' : 'addHost'}" />
<%@ include file="header.jspf" %>

<main class="container" style="max-width: 680px;">
    <div class="page-header">
        <div>
            <h1>${mode == 'edit' ? 'Edit Host' : 'Add New Host'}</h1>
            <p class="page-subtitle">Record network device specifications and risk classification.</p>
        </div>
    </div>

    <div class="card">
        <form method="post" action="${ctx}${mode == 'edit' ? '/hosts/edit' : '/hosts/add'}" data-validate novalidate>
            <c:if test="${mode == 'edit'}">
                <input type="hidden" name="id" value="<c:out value='${form.id}'/>">
            </c:if>

            <div class="form-row">
                <div class="form-group">
                    <label for="ipAddress">IPv4 Address *</label>
                    <input type="text" id="ipAddress" name="ipAddress" data-rule="ipAddress"
                           placeholder="e.g. 192.168.1.10" value="<c:out value='${form.ipAddress}'/>">
                    <c:if test="${not empty errors.ipAddress}">
                        <span class="field-error"><c:out value="${errors.ipAddress}"/></span>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="hostname">Hostname</label>
                    <input type="text" id="hostname" name="hostname" data-rule="hostname"
                           placeholder="e.g. web-server-01" value="<c:out value='${form.hostname}'/>">
                    <c:if test="${not empty errors.hostname}">
                        <span class="field-error"><c:out value="${errors.hostname}"/></span>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label for="operatingSystem">Operating System</label>
                <input type="text" id="operatingSystem" name="operatingSystem"
                       placeholder="e.g. Ubuntu 22.04 LTS" value="<c:out value='${form.operatingSystem}'/>">
                <c:if test="${not empty errors.operatingSystem}">
                    <span class="field-error"><c:out value="${errors.operatingSystem}"/></span>
                </c:if>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="status">Status</label>
                    <select id="status" name="status">
                        <option value="Active" ${empty form.status or form.status == 'Active' ? 'selected' : ''}>Active</option>
                        <option value="Inactive" ${form.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="riskLevel">Risk Level</label>
                    <select id="riskLevel" name="riskLevel">
                        <option value="Low" ${empty form.riskLevel or form.riskLevel == 'Low' ? 'selected' : ''}>Low</option>
                        <option value="Medium" ${form.riskLevel == 'Medium' ? 'selected' : ''}>Medium</option>
                        <option value="High" ${form.riskLevel == 'High' ? 'selected' : ''}>High</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="notes">Audit Notes</label>
                <textarea id="notes" name="notes" rows="4" placeholder="Owner, VLAN, or security observations..."><c:out value="${form.notes}"/></textarea>
                <c:if test="${not empty errors.notes}">
                    <span class="field-error"><c:out value="${errors.notes}"/></span>
                </c:if>
            </div>

            <div style="display: flex; gap: 0.75rem; margin-top: 1.5rem;">
                <button type="submit" class="btn btn-primary">${mode == 'edit' ? 'Save Changes' : 'Add Host'}</button>
                <a href="${ctx}/hosts" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</main>

<%@ include file="footer.jspf" %>
