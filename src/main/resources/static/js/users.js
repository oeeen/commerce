const UserApp = (() => {
    const userTemplate = Handlebars.compile(template.user);

    const UserController = function () {
        const userService = new UserService();

        const read = () => {
            userService.read();
        }

        const init = () => {
            read();
        }

        return {
            init: init
        }
    };

    const UserService = function () {
        const userApi = new UserApi();

        const read = () => {
            const userList = document.getElementById("user-list");
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
                        }));
                    });
                })
                .catch(error => console.log("error: " + error));
        }

        return {
            read: read,
        }
    };

    const UserApi = function () {
        const render = () => {
            return Api.get(`/api/users`);
        }

        return {
            render: render,
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