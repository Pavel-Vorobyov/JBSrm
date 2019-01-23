import React, {Component} from 'react';
import axios from '../../../../axios-messages';
import {connect} from 'react-redux';
import * as messageActions from '../../../../store/MessageAction';

import { withStyles } from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import Popper from '@material-ui/core/Popper';
import Fade from '@material-ui/core/Fade';
import Typography from '@material-ui/core/Typography';
import { Paper, Grid, Button, Dialog, DialogTitle, DialogContent, FormControl, InputLabel, Select, Input, DialogActions } from '@material-ui/core';

import './transportDetails.css';
import QuickSearch from '../../../../components/quickSearch/QuickSearch';

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
    descriptionInput: {
      height: '100px'
    },
  });
  
  const requiredType = [
    {
      value: 'COVERED_BODY',
      label: "Covered body",
    },
    {
      value: 'REFRIGERATOR',
      label: "Refrigeratop",
    },
    {
      value: 'TANK',
      label: "Tank",
    }
  ]

const search = () => {
  return
}

class CreateUserConteiner extends Component {

    state = {
        transportDetailsDto: {
            title: '',
            consumption: '',
            owner: {
              id: ''
            },
            transportType: 'COVERED_BODY',
          },
          ownerSelectPopShow: false,
    }

    handleChange = name => event => {
        let transportDetailsDto = this.state.transportDetailsDto;
    
        transportDetailsDto[name] = event.target.value;
    
        this.setState({
          ...this.state,
          transportDetailsDto: transportDetailsDto
        })
      }

    handleSaveButton() {
      console.log(this.state.transportDetailsDto)
        axios.post('/api/transports/', {
          bodyType: this.state.transportDetailsDto.bodyType,
          consumption: this.state.transportDetailsDto.consumption,
          deleted: this.state.transportDetailsDto.deleted,
          id: this.state.transportDetailsDto.id,
          ownerId: this.state.transportDetailsDto.owner.id,
          title: this.state.transportDetailsDto.title,
        })
        .then(response => {
           this.props.showMessage('Transport details has been created successfully!')
           window.history.back();
        });
    }

    handleOwnerSelectClick() {
      this.closeOwnerSelectWindow();
    }

    closeOwnerSelectWindow() {
      this.setState({
        ...this.state,
        ownerSelectPopShow: !this.state.ownerSelectPopShow
      })
    }

    handleDetailsClick = id => {
      axios.get('/api/companies/' + id)
      .then(response => {

        let transportDetailsDto = this.state.transportDetailsDto;
        transportDetailsDto.owner = response.data;
        this.setState({
          ...this.state,
          transportDetailsDto: transportDetailsDto,
          ownerSelectPopShow: false
        })
      })
    }

    render() {
        const { classes } = this.props;
        const { anchorEl, ownerSelectPopShow } = this.state;

        return (
            <Paper className='TransportDetails'>

                    <TextField
                        id="product-title"
                        label="Title"
                        className={classes.textField}
                        value={this.state.transportDetailsDto.title}
                        onChange={this.handleChange('title')}
                        margin="normal"
                    />
                    
                    <TextField
                        id="product-title"
                        label="Consumption"
                        type="number"
                        className={classes.textField}
                        value={this.state.transportDetailsDto.consumption}
                        onChange={this.handleChange('consumption')}
                        margin="normal"
                    />

                    <div className='transport-owner'>
                      <div className='transport-owner-name'>
                        {this.state.transportDetailsDto.owner.title}
                      </div>
                      <a className='owner-picker' onClick={() => this.handleOwnerSelectClick()}>Select</a>
                    </div>
                    <Modal
                      open={this.state.ownerSelectPopShow}
                      onClose={() => this.closeOwnerSelectWindow()}
                    >
                    <Paper className='modal-select-owner'>
                      <Typography variant="h6" id="modal-title">
                        Select transport owner
                      </Typography>
                      <QuickSearch 
                                searchQuery={'/api/companies/quickSearch/'} 
                                handleClick={this.handleDetailsClick}
                                objectMappingResult={{
                                    title: 'Title',
                                    email: 'Email',
                                    phone: 'Phone',
                                }}/>
                    </Paper>
                        
                    </Modal>
                    <TextField
                        id="transport-type"
                        label="Body type"
                        select
                        className={classes.textField}
                        value={this.state.transportDetailsDto.transportType}
                        onChange={this.handleChange('transportType')}
                        SelectProps={{
                          native: true,
                          MenuProps: {
                          className: classes.menu,
                          },
                        }}
                        margin="normal"
                    >
                    {requiredType.map(option => (
                        <option key={option.value} value={option.value}>
                          {option.label}
                        </option>
                    ))}
                    </TextField>

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

export default connect(null, MapToStore)(withStyles(styles)(CreateUserConteiner));
