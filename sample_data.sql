USE network_asset_manager;

-- Demo User (Username: test | Password: password123)
INSERT INTO users (full_name, username, email, password_hash) VALUES
('test', 'test', 'test@testmail.com', '120000:MDEyMzQ1Njc4OWFiY2RlZg==:KhxMqoWFMvOD+j+N9C3bgfAXWQ6gd1TWfou8CumGpPc=');

-- Sample Hosts
INSERT INTO hosts (id, ip_address, hostname, operating_system, status, risk_level, notes) VALUES
(1, '192.168.1.10', 'web-server-01', 'Ubuntu 22.04 LTS', 'Active', 'High', 'Public-facing Nginx reverse proxy. Needs OpenSSL patch.'),
(2, '192.168.1.20', 'db-server-01', 'Debian 12', 'Active', 'Medium', 'Primary MySQL 8.0 database instance. Restricted to internal VLAN.'),
(3, '192.168.1.35', 'hr-desktop-07', 'Windows 11 Pro', 'Active', 'Low', 'Standard HR workstation with endpoint protection enabled.'),
(4, '192.168.1.50', 'file-server-01', 'Windows Server 2022', 'Active', 'High', 'SMBv1 legacy share detected during internal audit.'),
(5, '192.168.1.77', 'old-printer', 'HP JetDirect', 'Inactive', 'Low', 'Decommissioned floor 2 network printer.'),
(6, '192.168.1.1', 'host-server', 'ubuntu server', 'Active', 'Low', 'Core gateway router and firewall.');

-- Sample Open Ports
INSERT INTO ports (host_id, port_number, service_name, vulnerability_note) VALUES
(1, 22, 'SSH', 'OpenSSH 8.9p1 - Key-based authentication enforced'),
(1, 80, 'HTTP', 'Redirects to HTTPS'),
(1, 443, 'HTTPS', 'TLS 1.2/1.3 enabled'),
(2, 3306, 'MySQL', 'Bound to internal interface only'),
(4, 445, 'SMB', 'High risk: Anonymous enumeration enabled'),
(4, 3389, 'RDP', 'Network Level Authentication (NLA) required'),
(6, 53, 'DNS', 'Internal DNS resolver');
