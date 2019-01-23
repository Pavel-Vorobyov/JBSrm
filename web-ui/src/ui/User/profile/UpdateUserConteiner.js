import React, {Component} from 'react';
import axios from '../../../axios-messages';
import {connect} from 'react-redux';

import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Paper, Button,} from '@material-ui/core';

import * as messageActions from '../../../store/MessageAction';

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
  
  const userGenders = [
    {
      value: 'MALE',
      label: "Male",
    },
    {
      value: 'FEMALE',
      label: "Female",
    }
  ]

class UpdateUserConteiner extends Component {

    state = {
        userDto: {
            id: '',
            name: '',
            surname: '',
            userGender: '',
            birthday: '2017-05-24',
            email: '',
            phone: '',
            companyId: '',
          },
        companyTitle: ''
    }

    componentDidMount() {
        let currentDto = this.state.userDto;
        axios.get('/api/users/profile/')
        .then(response => {
            let userDto = response.data;

            Object.keys(currentDto).map( (key, index) => {
                if (userDto.hasOwnProperty(key)) {
                    currentDto[key] = userDto[key];
                }
            });

            currentDto.companyId = userDto.company.id;

            this.setState({
                ...this.state,
                userDto: currentDto,
                companyTitle: userDto.company.title
            })
        });
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

      handleOnKeyUp = event => {
        switch (event.key) {
          case "Escape": 
              this.setState({...this.state, searchOpen:false});
              break;
          case "Enter":
              break;
          default:
              if (event.target.value ) {
                  axios.get('/api/companies/quickSearch/' + event.target.value)
                  .then ( response => {
                      this.setState({
                          ...this.state,
                          quickSerachResult: response.data, searchOpen: true});
                  })
              } else {
                  this.setState({...this.state, searchOpen:false})
              }
      }
    }

    handleChange = name => event => {
        let userDto = this.state.userDto;
    
        userDto[name] = event.target.value;
    
        this.setState({
          ...this.state,
          userDto: userDto
        })
      }

    handleBirthdayUpdate = event => {
    let userDto = this.state.userDto;

    userDto['birthday'] = event.target.value;

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

    handleSaveButton() {
        axios.put('/api/users/' + this.state.userDto.id, this.state.userDto)
        .then(response => {
            this.props.showMessage('User has been updated successfully!')
        });
    }

    render() {
        let style = this.state.searchOpen ? {display: 'block'} : {display: 'none'}
        const { classes } = this.props;

        return (
            <Paper className='UserDetails'>
                <TextField
                    id="standard-name"
                    label="Name"
                    className={classes.textField}
                    value={this.state.userDto.name}
                    onChange={this.handleChange('name')}
                    margin="normal"
                    />
                <TextField
                    id="standard-surname"
                    label="Surname"
                    className={classes.textField}
                    value={this.state.userDto.surname}
                    onChange={this.handleChange('surname')}
                    margin="normal"
                    />
                <TextField
                    id="standard-select-gender"
                    select
                    label="Select your gender"
                    className={classes.textField}
                    value={this.state.userDto.userGender}
                    onChange={this.handleChange('userGender')}
                    SelectProps={{
                        native: true,
                        MenuProps: {
                        className: classes.menu,
                        },
                    }}
                    helperText="Please select your currency"
                    margin="normal"
                    >
                    {userGenders.map(option => (
                        <option key={option.value} value={option.value}>
                        {option.label}
                        </option>
                    ))}
                    </TextField>

                    <TextField
                        id="date"
                        label="Birthday"
                        type="date"
                        onChange={this.handleChange('birthday')}
                        defaultValue={this.state.userDto.birthday}
                        className={classes.textField}
                        InputLabelProps={{
                        shrink: true,
                        }}
                    />

                    <TextField
                        id="standard-email"
                        label="Email"
                        className={classes.textField}
                        value={this.state.userDto.email}
                        margin="normal"
                        />

                    <TextField
                        id="standard-phone"
                        label="Phone"
                        className={classes.textField}
                        value={this.state.userDto.phone}
                        onChange={this.handleChange('phone')}
                        margin="normal"
                        />

                    <TextField
                        id="standard-company"
                        label="Company"
                        className={classes.textField}
                        value={this.state.companyTitle}
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

export default connect(null, MapToStore)(withStyles(styles)(UpdateUserConteiner));