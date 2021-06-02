import {LOGOUT, SIGN_IN_OK} from "./types";

export function signUp(text) {
    const url = 'http://localhost:5555/api/v1/auth/signUp';
    return async dispatch => {
        try {
            const response = await fetch(url, {
                body: JSON.stringify(text),
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            });
            const json = await response.json();
            if (!response.ok) {

                return Promise.reject();
            }
            return Promise.resolve(json);
        } catch (error) {
            console.error('Ошибка:', error);
        }
        // }finally {
        //     //dispatch(hideLoader())
        // }
    }
}

export function signIn(text) {
    const url = 'http://localhost:5555/api/v1/auth/signIn';
    return async dispatch => {

        let message = {
            body: JSON.stringify(text),
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            }
        }

        try {
            const response = await fetch(url, message);
            const json = await response.json();
            if (!response.ok) {

                return Promise.reject();
            }
            localStorage.setItem("user", JSON.stringify(json));
            dispatch({
                type: SIGN_IN_OK,
                payload: json
            })
            return Promise.resolve(json);
        } catch (error) {
            console.error('Ошибка:', error);
        }
        // }finally {
        //     //dispatch(hideLoader())
        // }
    }
}

export const logout = () => (dispatch) => {
    localStorage.removeItem("user");
    dispatch({
        type: LOGOUT,
    });
};