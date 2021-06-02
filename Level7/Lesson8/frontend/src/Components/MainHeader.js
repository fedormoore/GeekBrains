import React from "react";
import {BrowserRouter as Router, Switch, Route, Link} from "react-router-dom";

import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import AppBar from "@material-ui/core/AppBar";
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Container from "@material-ui/core/Container";

import {makeStyles} from "@material-ui/core";

import Login from "./Page/Login"
import Registration from "./Page/Registration"


const useStyles = makeStyles((theme) => ({
    title: {
        flexGrow: 1,
    },
}));

function MainHeader() {

    const classes = useStyles();

    return (
        <Router>
            <AppBar position="fixed">
                <Toolbar>
                    <Typography variant="h6" className={classes.title}>
                        Мой любимый магазин
                    </Typography>
                    <Link to="/login" style={{color: "inherit", textDecoration: "none"}}>
                        <ListItem button>
                            <ListItemText primary="Вход"/>
                        </ListItem></Link>

                    <Link to="/registration" style={{color: "inherit", textDecoration: "none"}}>
                        <ListItem button>
                            <ListItemText primary="Регистрация"/>
                        </ListItem>
                    </Link>
                </Toolbar>
            </AppBar>

            <Toolbar/>

            <Container maxWidth="sm" align="center">
                <Switch>
                    <Route exact path="/login" component={Login}/>
                    <Route exact path="/registration" component={Registration}/>
                </Switch>
            </Container>
        </Router>
    )
}

export default MainHeader