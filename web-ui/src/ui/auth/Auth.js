import React, {Component} from 'react';
import axios from '../../axios-messages';
import {connect} from 'react-redux';
import * as authAction from '../../store/AuthentificationAction';

import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';

import './auth.css';

const style = {
    textField: {
        width: '250px',
        position: 'relative',
        left: '-24px',
    },
    submitButton: {
        float: 'right',
        marginRight: '50px',
        marginTop: '10px',
    },
}

class Auth extends Component {

    state = {
        email: '',
        password: '',
        emailEror: false,
        passwordError: false,
        emailHelperText: '',
        passwordHelperText: '',
    }

    handleTextUpdate = name => event => {
        let value = event.target.value;

        if (name === 'email') {
            this.setState({
                ...this.state,
                email: value,
            })
        } else {
            this.setState({
                ...this.state,
                password: value,
            })
        }
    }

    handleSubmitButton(event) {
        let email = this.state.email;
        let password = this.state.password;

        if (this.сredentialIsValid(email, password)) {
            axios.post('/api/auth/login', {
                email: email,
                password: password,
            })
            .then( response => {
                
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('tokenType', response.data.tokenType);
                localStorage.setItem('userRole', response.data.userRole);
                window.location.reload();
            })
            .catch( e => {
                if (e.toString().includes('401')) {
                    this.setState({
                        ...this.state,
                        emailEror: true,
                        emailHelperText: 'The email or password is incorrect.'
                    })
                }
            });
        }
    }

    сredentialIsValid(email, password) {
        var emailRegex = /^.+@(gmail.com|mail.ru|yandex.ru)$/g;
        var passwordRegex = /^.{3,15}$/g;

        let emailIsValid = emailRegex.test(email);
        let passwordIsValid = passwordRegex.test(password);
        let passwordHelperText = '';
        if(!passwordIsValid) {
            passwordHelperText = 'Password cannot be empty.'
        }

        this.setState({
            ...this.state,
            emailEror: !emailIsValid,
            passwordError: !passwordIsValid,
            emailHelperText: 'The email is not a valid email address.',
            passwordHelperText: passwordHelperText,
        });

        return emailIsValid && passwordIsValid;
    }

    render() {
        return(
            <div class='main-conteiner'>
                <div class='auth-conteiner'>
                <div className='login-title'>Login</div>
                    <TextField
                        required
                        error={this.state.emailEror}
                        id="login-email"
                        label="Email"
                        margin="normal"
                        helperText={this.state.emailHelperText}
                        style={style.textField}
                        onChange={this.handleTextUpdate('email')}
                    />
                    <TextField
                        required
                        error={this.state.passwordError}
                        id="login-password"
                        label="Password"
                        margin="normal"
                        helperText={this.state.passwordHelperText}
                        style={style.textField}
                        onChange={this.handleTextUpdate('password')}
                    />
                    <Button 
                        variant="outlined" 
                        color="primary" 
                        style={style.submitButton}
                        onClick={() => this.handleSubmitButton()}
                    >
                        Submit
                    </Button>
                </div>
            </div>
        )
    };
};

const mapStateToProps = state => {
    return {
        token: state.message,
        role: state.role,
    }
  };
  
  const mapDispatchToProps = dispatch => {
    return {
        logIn: (token, role) => dispatch({type: authAction.LOG_IN, token: token, role: role})
    }
  };

export default connect(mapStateToProps, mapDispatchToProps)(Auth);
