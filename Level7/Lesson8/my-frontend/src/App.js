import React from 'react';
import {BrowserRouter as Router, Route, Switch, Link} from "react-router-dom";

import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import IconButton from "@material-ui/core/IconButton";
import {AccountCircle} from "@material-ui/icons";

import {makeStyles} from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';

import Login from "./Page/Login";
import Registration from "./Page/Registration";
import {useDispatch} from "react-redux";
import {logout} from "./util/network";
import Product from "./Page/Product";

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    title: {
        flexGrow: 1,
    },
}));

function App() {

    const classes = useStyles();

    const dispatch = useDispatch();

    const auth = JSON.parse(localStorage.getItem("token"));

    const [anchorEl, setAnchorEl] = React.useState(null);
    const menuId = 'primary-search-account-menu';
    const isMenuOpen = Boolean(anchorEl);

    const handleProfileMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const logOut = () => {
        setAnchorEl(null);
        logout()
    };

    const renderMenu = (
        <Menu
            anchorEl={anchorEl}
            anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
            id={menuId}
            keepMounted
            transformOrigin={{ vertical: 'top', horizontal: 'right' }}
            open={isMenuOpen}
            onClose={handleMenuClose}
        >
            <MenuItem component={Link} to="/profile" onClick={handleMenuClose}>Профиль</MenuItem>
            <MenuItem onClick={logOut}>Выход</MenuItem>
        </Menu>
    );



    return (
        <Router>
            <div className={classes.root}>
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="h6" className={classes.title}>
                            Интернет магазин
                        </Typography>
                        {!auth ? (
                            <div>
                                <Link to="/login" style={{color: "inherit", textDecoration: "none"}}>
                                    <ListItem button>
                                        <ListItemText primary="Вход"/>
                                    </ListItem></Link>
                                <Link to="/registration" style={{color: "inherit", textDecoration: "none"}}>
                                    <ListItem button>
                                        <ListItemText primary="Регистрация"/>
                                    </ListItem>
                                </Link>
                            </div>
                        ) : (
                            <IconButton
                                edge="end"
                                aria-label="account of current user"
                                aria-controls={menuId}
                                aria-haspopup="true"
                                onClick={handleProfileMenuOpen}
                                color="inherit"
                            >
                                <AccountCircle />
                            </IconButton>
                        )}
                    </Toolbar>
                </AppBar>
            </div>
            {renderMenu}
            <Switch>
                <Route exact path="/login" component={Login}/>
                <Route exact path="/registration" component={Registration}/>
                <Route exact path="/product" component={Product}/>
            </Switch>
        </Router>
    );
}

export default App;
