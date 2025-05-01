const togglePassword = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('password');
    const togglePasswordIcon = document.getElementById('togglePasswordIcon');
    const passwordStrengthBar = document.getElementById('passwordStrengthBar');
    const passwordStrengthText = document.getElementById('passwordStrengthText');

    togglePassword.addEventListener('click', function () {
        const isPassword = passwordInput.type === 'password';
        passwordInput.type = isPassword ? 'text' : 'password';
        togglePasswordIcon.classList.toggle('bi-eye-fill');
        togglePasswordIcon.classList.toggle('bi-eye-slash-fill');
    });

    function checkPasswordStrength() {
        const val = passwordInput.value;
        let strength = 0;

        if (val.length >= 8) strength++;
        if (val.match(/[A-Z]/)) strength++;
        if (val.match(/[a-z]/)) strength++;
        if (val.match(/[0-9]/)) strength++;
        if (val.match(/[^A-Za-z0-9]/)) strength++;

        if (val.length === 0) {
            passwordStrengthBar.style.width = '0%';
            passwordStrengthBar.className = 'progress-bar';
            passwordStrengthText.textContent = '';
            return;
        }

        if (strength <= 2) {
            passwordStrengthBar.style.width = '30%';
            passwordStrengthBar.className = 'progress-bar bg-danger';
            passwordStrengthText.textContent = 'Senha fraca';
            passwordStrengthText.className = 'form-text text-danger';
        } else if (strength === 3 || strength === 4) {
            passwordStrengthBar.style.width = '60%';
            passwordStrengthBar.className = 'progress-bar bg-warning';
            passwordStrengthText.textContent = 'Senha média';
            passwordStrengthText.className = 'form-text text-warning';
        } else {
            passwordStrengthBar.style.width = '100%';
            passwordStrengthBar.className = 'progress-bar bg-success';
            passwordStrengthText.textContent = 'Senha forte';
            passwordStrengthText.className = 'form-text text-success';
        }
    }

    (function () {
        'use strict';

        var forms = document.querySelectorAll('.needs-validation');

        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!validateForm(form)) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);

                form.querySelectorAll('input').forEach(input => {
                    input.addEventListener('blur', () => validateField(input));
                });
            });

        function validateForm(form) {
            let valid = form.checkValidity();

            const cpfInput = form.querySelector('#cpf');
            const dateInput = form.querySelector('#dataNascimento');

            if (!isValidCPF(cpfInput.value)) {
                cpfInput.classList.add('is-invalid');
                valid = false;
            }

            if (!isValidDate(dateInput.value)) {
                dateInput.classList.add('is-invalid');
                valid = false;
            }

            return valid;
        }

        function validateField(input) {
            if (input.id === 'cpf' && input.value !== '') {
                if (!isValidCPF(input.value)) {
                    input.classList.add('is-invalid');
                    input.classList.remove('is-valid');
                } else {
                    input.classList.add('is-valid');
                    input.classList.remove('is-invalid');
                }
            } else if (input.id === 'dataNascimento' && input.value !== '') {
                if (!isValidDate(input.value)) {
                    input.classList.add('is-invalid');
                    input.classList.remove('is-valid');
                } else {
                    input.classList.add('is-valid');
                    input.classList.remove('is-invalid');
                }
            } else {
                if (input.checkValidity()) {
                    input.classList.add('is-valid');
                    input.classList.remove('is-invalid');
                } else {
                    input.classList.add('is-invalid');
                    input.classList.remove('is-valid');
                }
            }
        }

        // Validador de CPF real (mantido)
        function isValidCPF(cpf) {
            cpf = cpf.replace(/[^\d]+/g,'');
            if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;

            let soma = 0;
            for (let i = 0; i < 9; i++) {
                soma += parseInt(cpf.charAt(i)) * (10 - i);
            }
            let resto = (soma * 10) % 11;
            if (resto === 10 || resto === 11) resto = 0;
            if (resto !== parseInt(cpf.charAt(9))) return false;

            soma = 0;
            for (let i = 0; i < 10; i++) {
                soma += parseInt(cpf.charAt(i)) * (11 - i);
            }
            resto = (soma * 10) % 11;
            if (resto === 10 || resto === 11) resto = 0;
            return resto === parseInt(cpf.charAt(10));
        }

        // Validador de Data (mantido)
        function isValidDate(dateString) {
            if (!dateString) return false;
            const today = new Date();
            const inputDate = new Date(dateString);
            return inputDate <= today;
        }
    })();