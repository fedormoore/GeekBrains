import React from "react";
import { useHistory } from "react-router-dom";

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
import {signIn} from "../../redux/actions";
import {useDispatch} from "react-redux";
import keycode from "keycode";

const useStyles = makeStyles((theme) => ({
    root: {
        margin: theme.spacing(1),
        width: '30ch',
    },
    button: {
        margin: theme.spacing(1),
    },
}));

function Login() {

    const dispatch = useDispatch()

    const classes = useStyles();
    const history = useHistory();
    const [values, setValues] = React.useState({
        email: '',
        password: '',
        showPassword: false,
    });

    const [loading, setLoading] = React.useState(false);
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

        const expression = /\S+@\S+/;
        const validEmail = expression.test(String(values.email).toLowerCase());

        if (!validEmail) {
            errors.push("email");
        }

        if (values.email === "") {
            errors.push("email");
        }

        if (values.password === "") {
            errors.push("password");
        }

        if (errors.length > 0) {
            setErrors(errors);
            return false;
        }

        setLoading(true);

        (dispatch(signIn(values)))
            .then(() => {
                history.push("/app");
            })
            .catch(() => {

            })
            .finally(()=>{
                setLoading(false);
            });

    };

    return (
        <form className={classes.root} noValidate>
            <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="email"
                label="Электронная почта"
                name="email"
                autoComplete="email"
                autoFocus
                inputProps={{ tabIndex: "1" }}
                value={values.email}
                onChange={handleChange('email')}
                error={errors.indexOf("email") !== -1}
            />

            <FormControl margin="normal" variant="outlined" error={errors.indexOf("password") !== -1}>
                <InputLabel htmlFor="outlined-adornment-password" error={errors.indexOf("password") !== -1}>Пароль</InputLabel>
                <OutlinedInput
                    id="password"
                    inputProps={{ tabIndex: "2" }}
                    style={{padding: 0, margin: 0}}
                    label="Пароль"
                    type={values.showPassword ? 'text' : 'password'}
                    value={values.password}
                    onChange={handleChange('password')}
                    onKeyDown={(event)=>{
                        if (keycode(event)==='enter'){
                            handleLogin();
                        }
                    }}
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
                    disabled={loading}
                    tabIndex={'3'}
                    onClick={handleLogin}>
                {loading && (
                    <span className="spinner-border spinner-border-sm"></span>
                )}Вход</Button>
        </form>
    )
}

export default Login