import React, {Component} from 'react';
import classes from './Auth.css';
import axios from '../../axios-messages';
import {connect} from 'react-redux';

import * as authAction from '../../store/AuthentificationAction.js';

class Auth extends Component{
    render() {
        return (
            <div className={classes.Auth}>
                <form onSubmit={(event) => {submitHandler(event, this.props)}}>
                    <p>Authentication</p>
                    <input type='text' placeholder='Type your name...'/>
                    <button>Submit</button>
                </form>
            </div>
        )
    }
}

const submitHandler = (event, props) => {
    event.preventDefault();

    let user = {
        name: event.target.children[1].value
    };

    axios.post('/users.json', user)
        .then(response => {
            if (response.status === 200) {
                localStorage.setItem('name', user.name);
                localStorage.setItem('token', response.data.name);
                props.history.push('/feature');
            }
        })
};

const mapDispatchToState = dispatch => {
  return {
      bindUserData: (name, token) => dispatch(authAction.logIn(name, token))
  }

    // {
    //     type: authAction.LOG_IN,
    //         name: name,
    //     token: token
    // }
};

export default connect(null, mapDispatchToState)(Auth);