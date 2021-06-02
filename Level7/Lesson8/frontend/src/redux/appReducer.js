import {LOGOUT, SIGN_IN_OK} from "./types";

const auth = JSON.parse(localStorage.getItem("user"));

const initialState = {
    alert: {header: "", text: "", show: false, severity: "error"},
    auth: auth ? (auth):(null),
    balanceCard: [],
}

export const appReducer = (state = initialState, action) => {
    switch (action.type) {
        case SIGN_IN_OK:
            return {...state, auth: action.payload}
        case LOGOUT:
            return {
                ...state,
                auth: null,
            }
        default:
            return state
    }
}