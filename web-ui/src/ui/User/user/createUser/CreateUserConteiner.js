import React, {Component} from 'react';
import axios from '../../../../axios-messages';

import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Paper, Button} from '@material-ui/core';

import './userDetails.css';

const styles = theme => ({
    container: {
      display: 'flex',
      flexWrap: 'wrap',
    },
    textField: {
      marginLeft: theme.spacing.unit,
      marginRight: theme.spacing.unit,
      width: 300,
    },
    dense: {
      marginTop: 19,
    },
    menu: {
      width: 300,
    },
    button: {
        margin: theme.spacing.unit,
        float: 'right'
      },
  });

class CreateUserConteiner extends Component {

    state = {
        userDto: {
            email: '',
            password: '',
          },
        emailEror: false,
        passwordError: false,
        emailHelpText: ''
    }

    handleOnClick = (companyIdValue, companyTitleValue) => {
        let clientDto = this.state.clientDto;
        let companyTitle = this.state.companyTitle;
        clientDto['company'] = companyIdValue;
        companyTitle = companyTitleValue;
    
        this.setState({
          ...this.state,
          clientDto: clientDto,
          companyName: companyTitle,
          open: false,
          searchOpen: false
        })
      }

    handleChange = name => event => {
        let userDto = this.state.userDto;
    
        userDto[name] = event.target.value;
    
        this.setState({
          ...this.state,
          userDto: userDto
        })
      }

    config = {
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          }
        }

    сredentialIsValid(email, password) {
        var emailRegex = /^.+@(gmail.com|mail.ru|yandex.ru)$/g;
        var passwordRegex = /^.{3,15}$/g;

        let emailIsValid = emailRegex.test(email);
        let passwordIsValid = passwordRegex.test(password);

        this.setState({
            ...this.state,
            emailEror: !emailIsValid,
            passwordError: !passwordIsValid,
        });

        return emailIsValid && passwordIsValid;
    }

    handleSaveButton() {
        if (this.сredentialIsValid(this.state.userDto.email, this.state.userDto.password)) {
            axios.post('/api/users/', this.state.userDto)
            .then( response => {
                window.history.back();
            })
            .catch( error => {
                if (error.toString().includes('409')) {
                    this.setState({
                        ...this.state,
                        emailEror: true,
                        emailHelpText: 'Email is present...'
                    })
                }
                if (error.toString().includes('404')) {
                    this.setState({
                        ...this.state,
                        emailEror: true,
                        emailHelpText: 'Such email is not exists...'
                    })
                }
                
            });
        }
    }

    render() {
        const { classes } = this.props;

        return (
            <Paper className='create-user-conteiner'>

                <TextField
                    id="standard-email"
                    label="Email"
                    error={this.state.emailEror}
                    helperText={this.state.emailHelpText}
                    className={classes.textField}
                    value={this.state.userDto.name}
                    onChange={this.handleChange('email')}
                    margin="normal"
                    />

                <TextField
                    id="standard-password"
                    label="Password"
                    error={this.state.passwordError}
                    className={classes.textField}
                    value={this.state.userDto.surname}
                    onChange={this.handleChange('password')}
                    margin="normal"
                    />

                <Button 
                    variant="contained" 
                    color="primary" 
                    className={classes.button}
                    onClick={() => {this.handleSaveButton()}}>
                        Submit
                    </Button>
                    
            </Paper>
        )
    };
};

export default withStyles(styles)(CreateUserConteiner);