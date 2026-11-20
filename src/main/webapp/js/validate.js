(function () {
    const IPV4 = /^((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)\.){3}(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)$/;
    const HOSTNAME = /^[A-Za-z0-9]([A-Za-z0-9._-]{0,98}[A-Za-z0-9])?$/;
    const USERNAME = /^[A-Za-z0-9_]{3,20}$/;
    const EMAIL = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    const rules = {
        ipAddress: function (v) {
            v = v.trim();
            if (v === '') return 'IP address is required.';
            return IPV4.test(v) ? '' : 'Enter a valid IPv4 address, for example 192.168.1.10.';
        },
        hostname: function (v) {
            v = v.trim();
            if (v === '') return '';
            return HOSTNAME.test(v) ? '' : 'Hostname may contain letters, numbers, dots, hyphens, and underscores.';
        },
        username: function (v) {
            v = v.trim();
            if (v === '') return 'Username is required.';
            return USERNAME.test(v) ? '' : 'Username must be 3-20 characters (letters, numbers, underscore).';
        },
        loginUsername: function (v) {
            return v.trim() === '' ? 'Username is required.' : '';
        },
        loginPassword: function (v) {
            return v === '' ? 'Password is required.' : '';
        },
        fullName: function (v) {
            v = v.trim();
            if (v.length < 2 || v.length > 60) return 'Full name must be 2 to 60 characters.';
            return '';
        },
        email: function (v) {
            v = v.trim();
            if (v === '') return 'Email is required.';
            return EMAIL.test(v) ? '' : 'Enter a valid email address.';
        },
        password: function (v) {
            if (v.length < 8 || !/[A-Za-z]/.test(v) || !/\d/.test(v)) {
                return 'At least 8 characters, with a letter and a digit.';
            }
            return '';
        },
        confirmPassword: function (v, form) {
            const pw = form.querySelector('[name="password"]');
            if (pw && v !== pw.value) return 'Passwords do not match.';
            return '';
        },
        portNumber: function (v) {
            const n = Number(v);
            if (!Number.isInteger(n) || n < 1 || n > 65535) {
                return 'Enter a valid port between 1 and 65535.';
            }
            return '';
        },
        serviceName: function (v) {
            return v.trim() === '' ? 'Service name is required.' : '';
        }
    };

    function showError(input, message) {
        let errEl = input.parentElement.querySelector('.field-error');
        if (!errEl) {
            errEl = document.createElement('span');
            errEl.className = 'field-error';
            input.parentElement.appendChild(errEl);
        }
        errEl.textContent = message;
        if (message) {
            input.classList.add('input-error');
        } else {
            input.classList.remove('input-error');
        }
    }

    document.querySelectorAll('form[data-validate]').forEach(function (form) {
        const inputs = form.querySelectorAll('[data-rule]');

        inputs.forEach(function (input) {
            const ruleName = input.getAttribute('data-rule');
            const validator = rules[ruleName];
            if (!validator) return;

            input.addEventListener('blur', function () {
                showError(input, validator(input.value, form));
            });

            input.addEventListener('input', function () {
                if (input.classList.contains('input-error')) {
                    showError(input, validator(input.value, form));
                }
            });
        });

        form.addEventListener('submit', function (e) {
            let hasErrors = false;
            inputs.forEach(function (input) {
                const ruleName = input.getAttribute('data-rule');
                const validator = rules[ruleName];
                if (!validator) return;
                const msg = validator(input.value, form);
                showError(input, msg);
                if (msg) hasErrors = true;
            });
            if (hasErrors) {
                e.preventDefault();
            }
        });
    });
})();
