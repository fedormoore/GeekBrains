import React from "react";

import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import FormControl from '@material-ui/core/FormControl';
import InputAdornment from '@material-ui/core/InputAdornment';
import IconButton from '@material-ui/core/IconButton';
import InputLabel from "@material-ui/core/InputLabel";

import {makeStyles} from '@material-ui/core/styles';

import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';
import OutlinedInput from "@material-ui/core/OutlinedInput";
import {createCart, signIn} from "../util/network";
import {useDispatch} from "react-redux";

const useStyles = makeStyles((theme) => ({
    root: {
        margin: theme.spacing(1),
        width: '30ch',
    },
    button: {
        margin: theme.spacing(1),
    },
}));

function Login(props) {

    const classes = useStyles();

    const dispatch = useDispatch();

    const [values, setValues] = React.useState({
        login: '',
        password: '',
        showPassword: false,
    });

    const [errors, setErrors] = React.useState([]);

    const handleChange = (prop) => (event) => {
        setValues({...values, [prop]: event.target.value});

        const index = errors.indexOf(prop);
        if (index !== -1) {
            errors.splice(errors.indexOf(prop), 1);
        }
    };

    const handleClickShowPassword = () => {
        setValues({...values, showPassword: !values.showPassword});
    };

    const handleLogin = () => {
        const errors = [];

        if (values.login === "") {
            errors.push("login");
        }

        if (values.password === "") {
            errors.push("password");
        }

        if (errors.length > 0) {
            setErrors(errors);
            return false;
        }

        dispatch(signIn(values))
            .then(() => {
                props.history.push('/product')
                dispatch(createCart())
                    .then((response) => {
                        localStorage.setItem("card_id", response)
                    })
                    .catch((error) => {
                        console.log(error);
                    });

            })
            .catch(() => {
            });

    };

    return (
        <form className={classes.root} noValidate>
            <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="login"
                label="Логин"
                name="login"
                autoComplete="login"
                autoFocus
                inputProps={{tabIndex: "1"}}
                value={values.login}
                onChange={handleChange('login')}
                error={errors.indexOf("login") !== -1}
            />

            <FormControl margin="normal" variant="outlined" error={errors.indexOf("password") !== -1}>
                <InputLabel htmlFor="outlined-adornment-password"
                            error={errors.indexOf("password") !== -1}>Пароль</InputLabel>
                <OutlinedInput
                    id="password"
                    inputProps={{tabIndex: "2"}}
                    style={{padding: 0, margin: 0}}
                    label="Пароль"
                    type={values.showPassword ? 'text' : 'password'}
                    value={values.password}
                    onChange={handleChange('password')}
                    endAdornment={
                        <InputAdornment position="end">
                            <IconButton
                                aria-label="toggle password visibility"
                                onClick={handleClickShowPassword}
                            >
                                {values.showPassword ? <Visibility/> : <VisibilityOff/>}
                            </IconButton>
                        </InputAdornment>
                    }
                />
            </FormControl>
            <Button variant="contained" color="primary" className={classes.button}
                    tabIndex={'3'}
                    onClick={handleLogin}>
                Вход
            </Button>
        </form>
    )
}

export default Login