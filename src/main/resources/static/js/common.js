const Api = {
    get: function (url) {
        return fetch(url, {
            method: 'GET',
        });
    },
    post: function (url, data) {
        const token = document.getElementById("_csrf").getAttribute("content");
        const header = document.getElementById("_csrf_header").getAttribute("content");
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append(header, token);

        return fetch(url, {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(data),
        });
    },
    put: function (url, data) {
        const token = document.getElementById("_csrf").getAttribute("content");
        const header = document.getElementById("_csrf_header").getAttribute("content");
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append(header, token);

        return fetch(url, {
            method: 'PUT',
            headers: headers,
            body: JSON.stringify(data),
        });
    },
    delete: function (url) {
        return fetch(url, {
            method: 'DELETE',
        });
    },

    postImage: function (url, data) {
        return fetch(url, {
            method: 'POST',
            body: data,
            headers: {},
        });
    },
};

const AppStorage = (function () {
    let storage = {};

    const get = function (keyName, defaultValue) {
        if (storage.hasOwnProperty(keyName)) {
            return storage[keyName];
        }
        storage[keyName] = defaultValue;
        return storage[keyName];
    };

    const set = function (keyName, value) {
        storage[keyName] = value;
    };

    const check = function (keyName) {
        return get(keyName, false);
    };

    return {
        get: get,
        set: set,
        check: check,
    };
})();