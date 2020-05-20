const SignUpApp = (() => {

    const SignUpController = function () {
        const signUpService = new SignUpService();

        const signUp = () => {
            const signUpBtn = document.getElementById("signup-btn");
            signUpBtn.addEventListener('click', signUpService.signUp);
        };

        const init = () => {
            signUp();
        };

        return {
            init: init,
        };
    };

    const SignUpService = function () {
        const signUpApi = new SignUpApi();

        if (AppStorage.check('sign-up-running')) return;
        AppStorage.set('sign-up-running', true);

        const signUp = (event) => {
            event.preventDefault();

            const email = document.getElementById("login-email-input");
            const password = document.getElementById("login-password-input");
            const userName = document.getElementById("login-user-name-input");
            const nickName = document.getElementById("login-user-nickname-input");

            const data = {
                email: email.value,
                userName: userName.value,
                nickName: nickName.value,
                password: password.value,
            };

            signUpApi.signUp(data)
                .then(response => {
                    return response.json();
                }).then(json => {
                if (json.hasOwnProperty('errorMessage')) {
                    alert(json.errorMessage);
                } else {
                    //TODO: Redirect login page
                    alert('가입 완료! 로그인 하세요.');
                }
                AppStorage.set('sign-up-running', false);
            })
        }

        return {
            signUp: signUp,
        }
    }

    const SignUpApi = function () {
        const signUp = (data) => {
            return Api.post('/api/users/signup', data);
        }

        return {
            signUp: signUp,
        }
    }

    const init = () => {
        const signUpController = new SignUpController();
        signUpController.init();
    };

    return {
        init: init,
    };
})();

SignUpApp.init();