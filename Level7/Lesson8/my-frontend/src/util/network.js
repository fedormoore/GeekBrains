const SERVER = "http://localhost:5555";

function Request(options) {

    const auth = JSON.parse(localStorage.getItem("token"));

    const headers = {
        headers: {
            'Authorization': 'Bearer ' + auth.token,
            "Content-Type": "application/json"
        }
    }

    options = Object.assign({}, headers, options);

    return async dispatch => {
        return fetch(options.url, options).then((response) =>
            response.json().then((json) => {
                if (!response.ok) {
                    return Promise.reject(json);
                }
                return json;
            })
        );
    }
};

// export function getAds() {
//     return Request({
//         url: SERVER + "/api/v1/app/ads",
//         method: "GET",
//     });
// }

export function signIn(body) {
    return async dispatch => {
        try {
            const response = await fetch(SERVER + "/api/v1/auth/login", {
                body: JSON.stringify(body),
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            });
            const json = await response.json();
            localStorage.setItem("token", JSON.stringify(json));
            return Promise.resolve(json);
        } catch (error) {
            console.error('Ошибка:', error);
        }
    }
}

export function createCart() {
    return Request({
        url: SERVER + "/api/v1/cart",
        method: "POST",
    });
}

export function addCart(body) {
    return Request({
        url: SERVER + "/api/v1/cart/add"+body,
        method: "POST",
    });
}

export function signUp(body) {
    return async dispatch => {
        try {
            const response = await fetch(SERVER + "/api/v1/auth/signup", {
                body: JSON.stringify(body),
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            });
            const json = await response.json();
            return Promise.resolve(json);
        } catch (error) {
            console.error('Ошибка:', error);
        }
    }
}

export function logout() {
    localStorage.removeItem("token");
}

export function getCryptocurrency(param) {
    var url = new URL(SERVER + "/api/v1/products/"), params = {param}
    Object.keys(param).forEach(key => url.searchParams.append(key, param[key]))

    return async dispatch => {
        return fetch(url).then((response) =>
            response.json().then((json) => {
                if (!response.ok) {
                    return Promise.reject(json);
                }
                return json;
            })
        );
    }
}

