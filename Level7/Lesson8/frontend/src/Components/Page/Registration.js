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
import {signUp} from "../../redux/actions";
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

function Registration(props) {

    const dispatch = useDispatch();

    const classes = useStyles();

    const [values, setValues] = React.useState({
        name: '',
        email: '',
        password: '',
        password2: '',
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

        if (values.name === "") {
            errors.push("name");
        }

        if (values.email === "") {
            errors.push("email");
        }

        if (values.login === "") {
            errors.push("login");
        }

        if (values.password === "") {
            errors.push("password");
        }

        if (values.password2 === "") {
            errors.push("password2");
        }

        if (values.password !== values.password2) {
            errors.push("password");
            errors.push("password2");
        }

        if (errors.length > 0) {
            setErrors(errors);
            return false;
        }

        setLoading(true);

        (dispatch(signUp(values)))
            .then(() => {
                setLoading(false);
                props.history.push('/login')
            })
            .catch(() => {
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
                id="name"
                label="Как к Вам обращаться"
                name="name"
                autoFocus
                value={values.name}
                onChange={handleChange('name')}
                error={errors.indexOf("name") !== -1}
            />
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
                value={values.email}
                onChange={handleChange('email')}
                error={errors.indexOf("email") !== -1}
            />
            <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="login"
                label="Логин"
                name="login"
                autoFocus
                value={values.login}
                onChange={handleChange('login')}
                error={errors.indexOf("login") !== -1}
            />
            <FormControl margin="normal" variant="outlined" error={errors.indexOf("password") !== -1}>
                <InputLabel htmlFor="outlined-adornment-password" error={errors.indexOf("password") !== -1}>Пароль</InputLabel>
                <OutlinedInput
                    id="password"
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
            <FormControl margin="normal" variant="outlined" error={errors.indexOf("password2") !== -1}>
                <InputLabel htmlFor="outlined-adornment-password" error={errors.indexOf("password2") !== -1}>Подтверждение пароля</InputLabel>
                <OutlinedInput
                    id="password2"
                    style={{padding: 0, margin: 0}}
                    label="Подтверждение пароля"
                    type={values.showPassword ? 'text' : 'password'}
                    value={values.password2}
                    onChange={handleChange('password2')}
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
                    onClick={handleLogin}>
                {loading && (
                    <span className="spinner-border spinner-border-sm"></span>
                )}Регистрация</Button>
        </form>
    )
}

export default Registration