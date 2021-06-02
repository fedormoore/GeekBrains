import {logout} from "../redux/actions";

const SERVER = "http://localhost:8080";

function Request(options) {

    const auth = JSON.parse(localStorage.getItem("user"));

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
                    if (response.status === 401) {
                        dispatch(logout())
                    }
                    return Promise.reject(json);
                }
                return json;
            })
        );
    }
};

export function getCryptocurrency() {
    return Request({
        url: SERVER + "/api/v1/app/spr/cryptocurrency",
        method: "GET",
    });
}

export function saveUser(body) {
    return Request({
        url: SERVER + "/api/v1/app/user",
        method: "POST",
        body: JSON.stringify(body),
    });
}