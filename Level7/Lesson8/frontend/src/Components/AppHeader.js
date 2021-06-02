import React from "react";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {Link} from "react-router-dom";

import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import AppBar from "@material-ui/core/AppBar";
import Drawer from '@material-ui/core/Drawer';
import MenuIcon from '@material-ui/icons/Menu';

import {makeStyles} from "@material-ui/core";

import {connect, useDispatch} from "react-redux";
import {logout} from "../redux/actions";
import IconButton from "@material-ui/core/IconButton";
import clsx from "clsx";
import {AccountCircle} from "@material-ui/icons";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
    },
    drawerPaper: {
        width: drawerWidth,
    },
    drawerHeader: {
        display: 'flex',
        alignItems: 'center',
        padding: theme.spacing(0, 1),
        // necessary for content to be below app bar
        ...theme.mixins.toolbar,
        justifyContent: 'flex-end',
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(1),
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        marginLeft: -drawerWidth,
    },
    contentShift: {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
        marginLeft: 0,
    },
}));

function AppHeader(props) {

    const classes = useStyles();

    const dispatch = useDispatch();

    const [open, setOpen] = React.useState(false);

    const menuId = 'primary-search-account-menu';

    const [anchorEl, setAnchorEl] = React.useState(null);

    const isMenuOpen = Boolean(anchorEl);

    const logOut = () => {
        setAnchorEl(null);
        dispatch(logout())
    };

    const handleDrawerOpen = () => {
        setOpen(!open);
    };

    const handleProfileMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
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
            <MenuItem onClick={logOut}>Выход</MenuItem>
        </Menu>
    );

    return (
        <Router>
            <div style={{display: 'flex'}}>
                <AppBar position="fixed" className={classes.appBar}>
                    <Toolbar>
                        <IconButton edge="start" style={{marginRight: 16}} color="inherit" aria-label="open drawer"
                                    onClick={handleDrawerOpen}>
                            <MenuIcon/>
                        </IconButton>
                        <Typography variant="h6" style={{flexGrow: 1}}>
                            Мой любимый магазин
                        </Typography>
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
                    </Toolbar>
                </AppBar>

                <Drawer
                    className={classes.drawer}
                    variant="persistent"
                    anchor="left"
                    open={open}
                    classes={{
                        paper: classes.drawerPaper,
                    }}
                >
                </Drawer>

                <main className={clsx(classes.content, {[classes.contentShift]: open,})}>
                    <div className={classes.drawerHeader}/>
                    <Switch>
                        {/*<Route exact path="/card" component={Card}/>*/}
                        {/*<Route exact path="/card_transaction" component={CardTran}/>*/}
                        {/*<Route exact path="/trade" component={Trade}/>*/}
                        {/*<Route exact path="/profile" component={Profile}/>*/}
                    </Switch>
                </main>
                {renderMenu}
            </div>
        </Router>
    )
}

function mapStateToProps(state) {
    return {
        user: state.app.auth,
    };
}

export default connect(mapStateToProps)(AppHeader);