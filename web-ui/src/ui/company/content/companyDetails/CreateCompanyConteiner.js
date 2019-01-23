import React, {Component} from 'react';
import axios from '../../../../axios-messages';
import {connect} from 'react-redux';

import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Paper, Button} from '@material-ui/core';

import './companyDetails.css';
import * as messageActions from '../../../../store/MessageAction';

const styles = theme => ({
    container: {
      display: 'flex',
      flexWrap: 'wrap',
    },
    textField: {
      marginLeft: theme.spacing.unit,
      marginRight: theme.spacing.unit,
      width: 318,
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

class CreateCompanyConteiner extends Component {

    tableStyle = {
        TableCell: {
            fontSize: '18px',
            color: 'rgba(0, 0, 0, 0.87)',
            fontWeight: '400',
            overflow: 'hidden',
        },
        TableHeaderCell: {
            fontSize: '18px',
            color: 'rgba(0, 0, 0, 0.54)',
            fontWeight: '600',
            overflow: 'hidden',
            padding: '18px',
        },
        TableButton: {
            padding: '4px',
        },
        TableInputStyle: {
            width: '50px',
        }
    }

    state = {
        companyDto: {
            title: '',
            email: '',
            phone: '',
            systemAdminEmail: '',
            systemAdminPassword: '',
        },
        emailEror: false,
        passwordError: false,
        emailErrorText: '',
        passwordErrorText: '',
    }

    handleTextUpdate = name => event => {
        let companyDto = this.state.companyDto;

        companyDto[name] = event.target.value;

        this.setState({
            ...this.state,
            companyDto: companyDto
        })
    }

    config = {
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          }
        }

    handleSaveButton() {
        if (this.сredentialIsValid(this.state.companyDto.systemAdminEmail, this.state.companyDto.systemAdminPassword)) {
            axios.post('/api/companies/', this.state.companyDto)
            .then(response => {
                this.props.showMessage('Company has been created successfully!');
                window.history.back();
            })
            .catch( error => {
                if (error.toString().includes('409')) {
                    this.setState({
                        ...this.state,
                        emailEror: true,
                        emailErrorText: 'This email is already present\'s',
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

        let emailErrorText = emailIsValid ? this.state.emailErrorText : 'Please enter valid email...';
        let passwordErrorText = passwordIsValid? this.state.passwordErrorText : 'Please enter password from 3 to 15 chars...';

        this.setState({
            ...this.state,
            emailEror: !emailIsValid,
            passwordError: !passwordIsValid,
            emailErrorText: emailErrorText,
            passwordErrorText: passwordErrorText,
        });

        return emailIsValid && passwordIsValid;
    }

    render() {
        const { classes } = this.props;

        return (
            <Paper className='create-company-details'>
                <TextField
                    id="standard-title"
                    label="Title"
                    className={classes.textField}
                    value={this.state.companyDto.title}
                    onChange={this.handleTextUpdate('title')}
                    margin="normal"
                />

                <TextField
                    id="standard-email"
                    label="Email"
                    className={classes.textField}
                    value={this.state.companyDto.email}
                    onChange={this.handleTextUpdate('email')}
                    margin="normal"
                />

                <TextField
                    id="standard-phone"
                    label="Phone"
                    className={classes.textField}
                    value={this.state.companyDto.phone}
                    onChange={this.handleTextUpdate('phone')}
                    margin="normal"
                />

                <TextField
                    id="standard-email"
                    label="System admin email"
                    error={this.state.emailEror}
                    helperText={this.state.emailErrorText}
                    className={classes.textField}
                    value={this.state.companyDto.systemAdminEmail}
                    onChange={this.handleTextUpdate('systemAdminEmail')}
                    margin="normal"
                />

                <TextField
                    id="standard-phone"
                    label="System admin password"
                    error={this.state.passwordError}
                    helperText={this.state.passwordErrorText}
                    className={classes.textField}
                    value={this.state.companyDto.systemAdminPassword}
                    onChange={this.handleTextUpdate('systemAdminPassword')}
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

const MapToStore = dispatch => {
    return {
        showMessage: (message) => dispatch({type: messageActions.SHOW_MESSAGE, message: message})
    }
};

export default connect(null, MapToStore)(withStyles(styles)(CreateCompanyConteiner));