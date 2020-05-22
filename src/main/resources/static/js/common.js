const Api = {
    get: function (url) {
        return fetch(url, {
            method: 'GET',
        });
    },
    post: function (url, data) {
        return fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });
    },
    put: function (url, data) {
        return fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
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