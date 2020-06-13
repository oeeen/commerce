const LogoutApp = (() => {

    const LogoutController = function () {
        const logoutService = new LogoutService();

        const logout = () => {
            const logoutBtn = document.getElementById("logout-btn");
            logoutBtn.addEventListener('click', logoutService.logout);
        };

        const init = () => {
            logout();
        };

        return {
            init: init,
        };
    };

    const LogoutService = function () {
        const logoutApi = new LogoutApi();

        if (AppStorage.check('logout-running')) return;
        AppStorage.set('logout-running', true);

        const logout = (event) => {
            event.preventDefault();

            logoutApi.logout(null)
                .then(() => {
                    window.location.href = "/";
                    AppStorage.set('logout-running', false);
                })
        }

        return {
            logout: logout,
        }
    }

    const LogoutApi = function () {
        const logout = (data) => {
            return Api.post('/api/users/logout', data);
        }

        return {
            logout: logout,
        }
    }

    const init = () => {
        const logoutController = new LogoutController();
        logoutController.init();
    };

    return {
        init: init,
    };
})();

LogoutApp.init();