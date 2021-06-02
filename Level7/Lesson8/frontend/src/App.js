import React from 'react';
import {connect} from "react-redux";

import 'fontsource-roboto';

import MainHeader from "./Components/MainHeader";
import AppHeader from "./Components/AppHeader";
import CssBaseline from "@material-ui/core/CssBaseline";

function App(props) {
    const currentUser = props.user;
    return (
        <div>
            {currentUser ? (
                <div className="App">
                    <CssBaseline/>
                    <AppHeader/>
                </div>
            ) : (
                <MainHeader/>
            )}
        </div>
    )
}

function mapStateToProps(state) {
    return {
        user: state.app.auth,
    };
}

export default connect(mapStateToProps)(App);
