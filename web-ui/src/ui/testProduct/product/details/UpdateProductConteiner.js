import React, {Component} from 'react';
import axios from '../../../../axios-messages';
import {connect} from 'react-redux';
import * as messageActions from '../../../../store/MessageAction';

import { withStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Modal from '@material-ui/core/Modal';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import { Paper, Grid, Button, Dialog, DialogTitle, DialogContent, FormControl, InputLabel, Select, Input, DialogActions } from '@material-ui/core';

import './productDetails.css';

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

class CreateUserConteiner extends Component {

    state = {
        productDetailsDto: {
            id: '',
            title: '',
            description: '',
            price: '',
            requiredType: 'COVERED_BODY',
          },
    }

    componentWillMount() {
      let productDetailsId = this.props.match.params.id;
        axios.get('/api/product-details/' + productDetailsId)
        .then(response => {
            this.setState({
                ...this.state,
                productDetailsDto: response.data,
            })
        });
    }

    handleChange = name => event => {
        let productDetailsDto = this.state.productDetailsDto;
    
        productDetailsDto[name] = event.target.value;
    
        this.setState({
          ...this.state,
          productDetailsDto: productDetailsDto
        })
      }

    handleSaveButton() {
        axios.put('/api/product-details/' + this.state.productDetailsDto.id, this.state.productDetailsDto)
        .then(response => {
           this.props.showMessage('Product details has been created successfully!');
           window.history.back();
        });
    }

    render() {
        const { classes } = this.props;

        return (
            <Paper className='product-details'>
                

                    <TextField
                        id="product-title"
                        label="Title"
                        className={classes.textField}
                        value={this.state.productDetailsDto.title}
                        onChange={this.handleChange('title')}
                        margin="normal"
                    />
                    <TextField
                      id="product-description"
                      label="Description"
                      multiline
                      rowsMax="16"
                      value={this.state.productDetailsDto.description}
                      onChange={this.handleChange('description')}
                      className={classes.textField}
                      margin="normal"
                      helperText="Enter the description of the product///"
                      variant="outlined"
                    />
                    <TextField
                        id="product-title"
                        label="price"
                        className={classes.textField}
                        value={this.state.productDetailsDto.price}
                        onChange={this.handleChange('price')}
                        margin="normal"
                    />
                    <TextField
                        id="product-type"
                        label="Require type"
                        select
                        className={classes.textField}
                        value={this.state.productDetailsDto.requiredType}
                        onChange={this.handleChange('requiredType')}
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
