import React, {Component} from 'react';
import axios from '../../../../axios-messages';
import {connect} from 'react-redux';

import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Paper, Button} from '@material-ui/core';

import './deedDetails.css';
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

  const productState = [
    {
      value: 'DELIVERED',
      label: "Delivered",
    },
    {
      value: 'LOST',
      label: "Lost",
    }
  ]

class UpdateDeedConteiner extends Component {

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
        deedDto: {
            product: {
                productDetails: {
                    title: '',
                },
                amount: '',
                productState: '',
            },
            price: '',
            createAt: '',
          },
    }

    componentDidMount() {
        let companyId = this.props.match.params.id;
        axios.get('/api/deeds/' + companyId)
        .then(response => {
            this.setState({
                ...this.state,
                deedDto: response.data,
            })
        });
    }

    handleTextUpdate = name => event => {
        let deedDto = this.state.deedDto;

        deedDto[name] = event.target.value;

        this.setState({
            ...this.state,
            deedDto: deedDto
        })
    }

    handleUpdateProduct = name => event => {
        let deedDto = this.state.deedDto;

        deedDto.product[name] = event.target.value;

        this.setState({
            ...this.state,
            deedDto: deedDto
        })
    }

    config = {
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          }
        }

    handleSaveButton() {
        axios.put('/api/deeds/' + this.state.deedDto.id, {
            productId: this.state.deedDto.product.id,
            amount: this.state.deedDto.product.amount,
            price: this.state.deedDto.price,
        })
        .then(response => {
            this.props.showMessage('Company has been updated successfully!');
            window.history.back();
        });
    }

    render() {
        const { classes } = this.props;

        return (
            <Paper className='deed-details'>
                 <TextField
                    id="standard-title"
                    label="Product title"
                    className={classes.textField}
                    value={this.state.deedDto.product.productDetails.title}
                    margin="normal"
                    />
                <TextField
                    id="standard-amount"
                    label="Amount"
                    type="number"
                    className={classes.textField}
                    value={this.state.deedDto.product.amount}
                    onChange={this.handleUpdateProduct('amount')}
                    margin="normal"
                    />
                <TextField
                    id="standard-select-gender"
                    select
                    label="Select your gender"
                    className={classes.textField}
                    value={this.state.deedDto.product.productState}
                    onChange={this.handleUpdateProduct('productState')}
                    SelectProps={{
                        native: true,
                        MenuProps: {
                        className: classes.menu,
                        },
                    }}
                    helperText="Please select product state"
                    margin="normal"
                    >
                    {productState.map(option => (
                        <option key={option.value} value={option.value}>
                        {option.label}
                        </option>
                    ))}
                    </TextField>

                    <TextField
                        id="standard-price"
                        label="Price"
                        type="number"
                        className={classes.textField}
                        value={this.state.deedDto.price}
                        onChange={this.handleTextUpdate('price')}
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

export default connect(null, MapToStore)(withStyles(styles)(UpdateDeedConteiner));