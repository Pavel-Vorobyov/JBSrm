import React, {Component} from 'react';
import axios from '../../../../axios-messages';
import {connect} from 'react-redux';

import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Paper, Button, Dialog, DialogTitle, DialogContent} from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import Typography from '@material-ui/core/Typography';

import './deedDetails.css';
import * as messageActions from '../../../../store/MessageAction';
import QuickSearch from '../../../../components/quickSearch/QuickSearch';

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

class CreateDeedConteiner extends Component {

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
          waybillDto: {
            ttn: {
                id: '',
                driver: {
                    name: '',
                    surname: '',
                },
                createAt: '',
            },
            createdAt: [],
            startDate: [],
            endDate: [],
            checkPoints: [],
        },
        showSelectProduct: false,
        showSelectWaybill: false,
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

    handleShowSelectProduct = isVisible => {
        this.setState({
            ...this.state,
            showSelectProduct: isVisible,
        });
    }

    handleShowSelectWaybill = isVisible => {
        this.setState({
            ...this.state,
            showSelectWaybill: isVisible,
        });
    }

    config = {
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          }
        }

    handleSaveButton() {
        axios.post('/api/deeds/', {
            productDetailsId: this.state.deedDto.product.productDetails.id,
            amount: this.state.deedDto.product.amount,
            price: this.state.deedDto.price,
        })
        .then(response => {
            this.props.showMessage('Company has been updated successfully!');
            window.history.back();
        });
    }

    handleProductDetailsClick = id => {
        axios.get('/api/product-details/' + id)
        .then ( response => {
            let deedDto = this.state.deedDto;
            deedDto.product.productDetails = response.data;

            this.setState({
                ...this.state,
                deedDto: deedDto,
                showSelectProduct: false
              });
        })
    };

    handleWaybillClick = id => {
        axios.get('/api/waybills/' + id)
        .then ( response => {
            let waybillDto = this.state.waybillDto;
            waybillDto = response.data;

            this.setState({
                ...this.state,
                waybillDto: waybillDto,
                showSelectWaybill: false
              });
        })
    };

    render() {
        const { classes } = this.props;

        return (
            <div style={{width: '768px', margin:'auto'}}>
            <Paper className='deed-details' style={{float: 'right'}}>
                 <TextField
                    id="standard-title"
                    label="Product title"
                    className={classes.textField}
                    style={{width: '223px'}}
                    value={this.state.deedDto.product.productDetails.title}
                    margin="normal"
                    />
                <Button 
                    variant="outlined" 
                    color="primary" 
                    style={{
                        position: 'relative',
                        top: '12px'
                    }}
                    className={classes.button}
                    onClick={() => {this.handleShowSelectProduct(true)}}>
                        Select
                </Button>
                <Dialog
                    onClose={() => this.handleShowSelectProduct(false)}
                    aria-labelledby="customized-dialog-title"
                    open={this.state.showSelectProduct}
                    >
                    <DialogTitle 
                        id="customized-dialog-title" 
                        style={{borderBottom: '1px solid rgba(0,0,0,0.2)', padding: '5px', paddingBottom: '0'}}
                        onClose={() => this.handleShowSelectProduct(false)}>
                        <p style={{float:'left', padding: '12px'}}>
                            Modal title
                        </p>
                        <IconButton style={{float: 'right'}} aria-label="Close" onClick={() => this.handleShowSelectProduct(false)}>
                            <CloseIcon />
                        </IconButton>
                    </DialogTitle>
                    <DialogContent style={{paddingTop: '10px', width: '400px', overflow: 'visible'}}>
                        <QuickSearch
                            searchQuery={'/api/product-details/quickSearch/'} 
                            handleClick={this.handleProductDetailsClick}
                            objectMappingResult={{
                                title: '',
                                price: '',
                                requiredType:'',
                            }}
                        />
                    </DialogContent>
                </Dialog>
                <TextField
                    id="standard-amount"
                    label="amount"
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
                        label="price"
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

                        <Dialog
                            onClose={() => this.handleShowSelectWaybill(false)}
                            aria-labelledby="customized-dialog-title"
                            open={this.state.showSelectWaybill}
                            >
                            <DialogTitle 
                                id="customized-dialog-title" 
                                style={{borderBottom: '1px solid rgba(0,0,0,0.2)', padding: '5px', paddingBottom: '0'}}
                                onClose={() => this.handleShowSelectWaybill(false)}>
                                <p style={{float:'left', padding: '12px'}}>
                                    Modal title
                                </p>
                                <IconButton style={{float: 'right'}} aria-label="Close" onClick={() => this.handleShowSelectWaybill(false)}>
                                    <CloseIcon />
                                </IconButton>
                            </DialogTitle>
                            <DialogContent style={{paddingTop: '10px', width: '400px', overflow: 'visible'}}>
                                <QuickSearch
                                    searchQuery={'/api/waybills/quickSearch/'} 
                                    handleClick={this.handleWaybillClick}
                                    objectMappingResult={{
                                        createAt: 'Create at:',
                                        startDate: 'Start date:',
                                        endDate:'End date:',
                                        ttnState: 'Ttn state:',
                                    }}
                                />
                            </DialogContent>
                        </Dialog>
                    
            </Paper>
            <Paper className='deed-details' style={{float: 'right', marginRight: '10px', textAlign:'left', height: '230px'}}>
                <Typography variant='h5' style={{textAlign: 'center'}}>Waybill</Typography>
                <Typography variant='h5'>Create at: {this.state.waybillDto.createdAt}</Typography>
                <Typography variant='h5'>Start date: {this.state.waybillDto.startDate}</Typography>
                <Typography variant='h5'>End date: {this.state.waybillDto.endDate}</Typography>
                <Typography variant='h5'>Ttn state: {this.state.waybillDto.ttn.ttnState}</Typography>
                <Button 
                        variant="outlined" 
                        color="primary" 
                        className={classes.button}
                        onClick={() => {this.handleShowSelectWaybill(true)}}>
                            Select
                        </Button>

            </Paper>
            </div>
        )
    };
};

const MapToStore = dispatch => {
    return {
        showMessage: (message) => dispatch({type: messageActions.SHOW_MESSAGE, message: message})
    }
};

export default connect(null, MapToStore)(withStyles(styles)(CreateDeedConteiner));