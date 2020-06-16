const UserApp = (() => {
    const userTemplate = Handlebars.compile(template.user);

    const UserController = function () {
        const userService = new UserService();

        const read = () => {
            userService.read();
        }

        const remove = () => {
            const withdrawOkBtn = document.getElementById('withdraw-ok-btn');
            withdrawOkBtn.addEventListener('click', userService.remove)
        }

        const init = () => {
            read();
            remove();
        }

        return {
            init: init
        }
    };

    const UserService = function () {
        const userApi = new UserApi();

        const read = () => {
            const userList = document.getElementById("user-list");
            if (userList == null) return;
            userList.innerHTML = "";

            userApi.render()
                .then(response => response.json())
                .then(data => {
                    data.forEach(user => {
                        userList.insertAdjacentHTML('afterbegin', userTemplate({
                            username: user.name,
                            authority: user.userRole.toLowerCase(),
                            nickname: user.nickName.nickName,
                            email: user.email.email,
                            status: user.userStatus.toLowerCase(),
                        }));
                    });
                })
                .catch(error => console.log("error: " + error));
        }

        const remove = () => {
            const modalContainer = document.getElementById('modal-container');
            const userId = modalContainer.getAttribute('data-userid')
            const password = document.getElementById('withdraw-password-input');

            const data = {
                id: userId,
                password: password.value,
            };

            userApi.checkPassword(data)
                .then(response => response.json())
                .then(success => {
                    if (!success) {
                        password.value = "";
                        alert("Invalid Password");
                    }
                    return success;
                })
                .then(success => {
                    if (success) {
                        userApi.remove(userId)
                            .catch(error => console.log("error: " + error));
                    }
                });
        }

        return {
            read: read,
            remove: remove,
        }
    };

    const UserApi = function () {
        const render = () => {
            return Api.get(`/api/users`);
        }

        const remove = (userId) => {
            return Api.delete(`/api/users/${userId}`);
        }

        const checkPassword = (data) => {
            return Api.post(`/api/users/validate`, data)
        }

        return {
            render: render,
            remove: remove,
            checkPassword: checkPassword,
        }
    }

    const init = () => {
        const userController = new UserController();
        userController.init();
    };

    return {
        init: init,
    };

})();

UserApp.init();