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
            const passwordInput = form.querySelector('#password');

            if (cpfInput && !isValidCPF(cpfInput.value)) {
                cpfInput.classList.add('is-invalid');
                valid = false;
            }

            if (dateInput && !isValidDate(dateInput.value)) {
                dateInput.classList.add('is-invalid');
                valid = false;
            }

            if (passwordInput && passwordInput.value.length > 0 && passwordInput.value.length < 6) {
                passwordInput.classList.add('is-invalid');
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
            } else if (input.id === 'password' && input.value.length > 0) {
                if (input.value.length < 6) {
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

        // Validador de CPF real
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

        // Validador de Data
        function isValidDate(dateString) {
            if (!dateString) return false;
            const today = new Date();
            const inputDate = new Date(dateString);
            return inputDate <= today;
        }
    })();