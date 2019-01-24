import React, {Component} from 'react';
import axios from '../../../../axios-messages';
import {connect} from 'react-redux';

import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import { Paper, Table, Fab, Typography, TableBody, TableCell, TableRow, Button, Dialog, DialogTitle, DialogContent, DialogActions } from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import Menu from '@material-ui/core/Menu';
import CloseIcon from '@material-ui/icons/Close';
import KeyboardArrowLeft from '@material-ui/icons/KeyboardArrowLeft';
import KeyboardArrowRight from '@material-ui/icons/KeyboardArrowRight';

import './ttnDetails.css';
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
      width: 250,
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

const userRoles = [
    {
      value: 'SYSTEM_ADMIN',
      label: 'System admin',
    },
    {
      value: 'ADMIN',
      label: 'Admin',
    },
    {
      value: 'DISPATCHER',
      label: 'Dispatcher',
    },
    {
      value: 'MANAGER',
      label: 'Manager',
    },
    {
      value: 'DRIVER',
      label: 'Driver',
    },
    {
      value: 'COMPANY_OWNER',
      label: 'Company owner',
    },
  ];
  
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

class UpdateTtnConteiner extends Component {

    tableStyle = {
        TableCell: {
            fontSize: '18px',
            color: 'rgba(0, 0, 0, 0.87)',
            fontWeight: '400',
            overflow: 'hidden',
            paddingRight: '10px',
            paddingLeft: '9px',
        },
        TableHeaderCell: {
            fontSize: '18px',
            color: 'rgba(0, 0, 0, 0.54)',
            fontWeight: '600',
            overflow: 'hidden',
            padding: '18px',
        },
        TableButton: {
            padding: '0',
        },
        TableInputStyle: {
            width: '50px',
        }
    }

    state = {
        ttnDto: {
            transport: {
                bodyType: '',
                consumption: '',
                owner: {
                    title: ''
                }
            },
            driver: {},
            products: [],
        },
        driverSelectShow: false,
        productSearchShow: false,
        transportSearchShow: false,
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

    handleChange = id => event => {
        let ttnDto = this.state.ttnDto;
        let amountExtension = ttnDto.products[id].amount - Number(event.target.value);
        ttnDto.products[id].amount = Number(event.target.value);
        ttnDto.products[id].deed = ttnDto.products[id].deed + amountExtension;
        
        this.setState({
        ...this.state,
        ttnDto: ttnDto
        })
      }
    handleDeedChange = id => event => {
        let ttnDto = this.state.ttnDto;
        let deedExtension = ttnDto.products[id].deed - Number(event.target.value);
        ttnDto.products[id].deed = Number(event.target.value);
        ttnDto.products[id].amount = ttnDto.products[id].amount + deedExtension;
        
        this.setState({
            ...this.state,
            ttnDto: ttnDto
        })
    }

    config = {
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          }
        }

    handleSaveButton() {
        if (this.isValidDto()) {
            axios.post('/api/ttns/', {
                driverId: this.state.ttnDto.driver.id,
                products: this.state.ttnDto.products,
                transportId: this.state.ttnDto.transport.id,
            })
            .then(response => {
                this.props.showMessage('Ttn has been updated successfully!');
                window.history.back();
            })
            .catch( error => {
                this.props.showErrorMessage( error.toString() )
            });
        }
    }

    isValidDto() {
        let ttnDto = this.state.ttnDto;
        let result = true;

        if (ttnDto.products.length <= 0) {
            this.props.showErrorMessage('Select one product at least!')
            result = false;
        }
        if (!ttnDto.transport.id) {
            this.props.showErrorMessage('Select transport before!')
            result = false;
        }
        if (!ttnDto.driver.id) {
            this.props.showErrorMessage('Select driver before!')
            result = false;
        }

        return result;
    }

    handleAddButton = id => {
        this.updateProductAmount(id, +1);
    }

    handleRemoveButton = id => {
        this.updateProductAmount(id, -1);
    }

    updateProductAmount(id, extension) {
        let ttnDto = this.state.ttnDto;
        ttnDto.products[id].amount = ttnDto.products[id].amount + extension;

        this.setState({
            ...this.state,
            ttnDto: ttnDto,
        })
    }

    handleDeedAddButton = id => {
        this.updateDeedProductAmount(id, +1);
    }

    handleDeedRemoveButton = id => {
        this.updateDeedProductAmount(id, -1);
    }

    updateDeedProductAmount(id, extension) {
        let ttnDto = this.state.ttnDto;

        if (this.isChangeable(id, extension)) {
            ttnDto.products[id].deed = ttnDto.products[id].deed + extension;

            this.setState({
                ...this.state,
                ttnDto: ttnDto,
            })
        }
    }

    isChangeable(id, extension) {
        let result = false;
        let ttnDto = this.state.ttnDto;

        let nextAmount = ttnDto.products[id].amount;
        let nextDeed = ttnDto.products[id].deed + extension;

        if (nextDeed <= nextAmount && nextDeed >= 0) {
            result = true;
        }
        if (!result) {
            this.props.showErrorMessage('You can\'t increase  or decrease deed any more!')
        }

        return result;
    }

    handelDeleteButton = id => {
        let ttnDto = this.state.ttnDto;
        ttnDto.products.splice(id, 1);
        console.log(ttnDto.products)

        this.setState({
            ...this.state,
            ttnDto: ttnDto,
        })
    }

    handleDriverClick = id => {
        axios.get('/api/users/' + id)
                  .then ( response => {
                      let ttnDto = this.state.ttnDto;
                      ttnDto.driver = response.data;
                      this.setState({
                          ...this.state,
                          ttnDto: ttnDto,
                          driverName: response.data.name,
                          driverSurname: response.data.surname,
                          driverSelectShow: false
                        });
                  })
      };

    handleUpdateDriverSelectWindow = isOpen => {
        this.setState({
            ...this.state,
            driverSelectShow: isOpen,
        });
    }

    handleProductDetailsSearch = isOpnen => {
        if (!this.state.ttnDto.transport.bodyType) {
            this.props.showErrorMessage('Please select transport before!');
        } else {
            this.setState({
                ...this.state,
                productSearchShow: isOpnen,
            });
        }
    }

    handleProductDetailsClick = id => {
        axios.get('/api/product-details/' + id)
        .then ( response => {
            let ttnDto = this.state.ttnDto;
            ttnDto.products.push({
                productDetails: response.data,
                amount: 0,
                deed: 0,
            })

            this.setState({
                ...this.state,
                ttnDto: ttnDto,
                productSearchShow: false
              });
        })
    };

    handleTransportDetailsShow = isOpen => {
        this.setState({
            ...this.state,
            transportSearchShow: isOpen,
        });
    };

    handleTransportClicked = id => {
        axios.get('/api/transports/' + id)
        .then ( response => {
            let ttnDto = this.state.ttnDto;
            ttnDto.transport = response.data;
            console.log(response)
            this.setState({
                ...this.state,
                ttnDto: ttnDto,
                transportSearchShow: false
              });
        })
    };

    render() {

        let testStyle = {
            fontSize: '14px'
        }

        let ttnProducts = this.state.ttnDto.products.map( (child, index) => {
            return (
                <TableRow hover>
                    <TableCell style={this.tableStyle.TableCell}>{child.productDetails.title}</TableCell>
                    <TableCell style={this.tableStyle.TableCell} align="right">{child.productDetails.price}</TableCell>
                    <TableCell style={this.tableStyle.TableCell} align="right">{child.productDetails.requiredType}</TableCell>
                    <TableCell style={{padding:'0', paddingRight: '10px'}}>
                        <TableCell style={this.tableStyle.TableButton}>
                            <IconButton variant="contained" color="primary" size="small" style={testStyle} onClick={() => {this.handleRemoveButton(index)}}>
                                <KeyboardArrowLeft />
                            </IconButton>
                    </TableCell>
                    <TableCell style={this.tableStyle.TableCell} align="right">
                            <TextField
                                id={index}
                                label="Amount"
                                style={this.tableStyle.TableInputStyle}
                                value={child.amount}
                                onChange={this.handleChange(index)}
                                margin="normal"
                                />
                        </TableCell>
                        <TableCell style={this.tableStyle.TableButton}>
                            <IconButton variant="contained" color="primary" size="small" style={testStyle} onClick={() => {this.handleAddButton(index)}}>
                                <KeyboardArrowRight />
                            </IconButton>
                        </TableCell>
                    </TableCell>
                    <TableCell style={{padding:'0', paddingRight: '10px'}}>
                        <TableCell style={this.tableStyle.TableButton}>
                            <IconButton variant="contained" color="primary" size="small" style={testStyle} onClick={() => {this.handleDeedRemoveButton(index)}}>
                                <KeyboardArrowLeft />
                            </IconButton>
                    </TableCell>
                    <TableCell style={this.tableStyle.TableCell} align="right">
                            <TextField
                                id={index}
                                label="Deed"
                                style={this.tableStyle.TableInputStyle}
                                value={child.deed}
                                onChange={this.handleDeedChange(index)}
                                margin="normal"
                                />
                        </TableCell>
                        <TableCell style={this.tableStyle.TableButton}>
                            <IconButton variant="contained" color="primary" size="small" style={testStyle} onClick={() => {this.handleDeedAddButton(index)}}>
                                <KeyboardArrowRight />
                            </IconButton>
                        </TableCell>
                    </TableCell>
                   <TableCell style={this.tableStyle.TableButton}>
                    <div style={{margin: 'auto'}}>
                        <IconButton aria-label="Delete" className='Button' onClick={() => {this.handelDeleteButton(index)}}>
                        <DeleteIcon fontSize="small" />
                        </IconButton>
                    </div>
                   </TableCell>
                </TableRow>
            )
        });

        return (
            <div style={{paddingRight: '14%'}}>

            <Paper className='TtnProducts'>
            Tests Ttn products:
                <Table >
                    <TableBody> 
                            <TableCell style={this.tableStyle.TableHeaderCell}>Title:</TableCell>
                            <TableCell style={this.tableStyle.TableHeaderCell} align="right">Price:</TableCell>
                            <TableCell style={this.tableStyle.TableHeaderCell} align="right">Required type:</TableCell>
                            <TableCell style={this.tableStyle.TableHeaderCell} align="right">Amount:</TableCell>
                            <TableCell style={this.tableStyle.TableButton}>
                                <Button onClick={() => this.handleSaveButton()} variant="contained" color="primary" size="small" style={testStyle}>Save</Button>
                            </TableCell>

                            {ttnProducts}
                            <TableRow>
                                <TableCell style={{borderBottom: 'none', width: '100%'}}>
                                    <Fab size="small" color="secondary" aria-label="Add" onClick={() => this.handleProductDetailsSearch(true)}>
                                        <AddIcon />
                                    </Fab>
                                </TableCell>
                                <Dialog
                                    onClose={() => this.handleProductDetailsSearch(false)}
                                    aria-labelledby="customized-dialog-title"
                                    open={this.state.productSearchShow}
                                    >
                                    <DialogTitle 
                                        id="customized-dialog-title" 
                                        style={{borderBottom: '1px solid rgba(0,0,0,0.2)', padding: '5px', paddingBottom: '0'}}
                                        onClose={() => this.handleProductDetailsSearch(false)}>
                                        <p style={{float:'left', padding: '12px'}}>
                                            Product search
                                        </p>
                                        <IconButton style={{float: 'right'}} aria-label="Close" onClick={() => this.handleProductDetailsSearch(false)}>
                                            <CloseIcon />
                                        </IconButton>
                                    </DialogTitle>
                                    <DialogContent style={{paddingTop: '10px', width: '400px', overflow: 'visible'}}>
                                        <QuickSearch
                                            placeholder="Search by id, title, required type or price..."
                                            searchQuery={'/api/product-details/quickSearch/'  + this.state.ttnDto.transport.bodyType + ' '} 
                                            handleClick={this.handleProductDetailsClick}
                                            objectMappingResult={{
                                                title: 'Title',
                                                price: 'Price',
                                                requiredType:'Required type',
                                            }}
                                        />
                                    </DialogContent>
                                </Dialog>
                            </TableRow>
                    </TableBody>
                </Table>
                    
            </Paper>

                <Paper className='TtnDetails' style={{height: '370px'}}>
                Testw Ttn details:

                <div className='TextField'>
                    <p style={{fontWeight: '400', borderBottom:'1px solid rgba(0,0,0,0.15)'}}>
                        Driver details:
                    </p>
                    
                    <p>
                        Name: {this.state.ttnDto.driver.name}
                    </p>
                    <p>
                        Surname: {this.state.ttnDto.driver.surname}
                    </p>
                    <Button style={{float:'right'}} variant="outlined" color="primary" onClick={() => this.handleUpdateDriverSelectWindow(true)}>
                        Select driver
                    </Button>
                    <Dialog
                        onClose={() => this.handleUpdateDriverSelectWindow(false)}
                        aria-labelledby="customized-dialog-title"
                        open={this.state.driverSelectShow}
                        >
                        <DialogTitle 
                            id="customized-dialog-title" 
                            style={{borderBottom: '1px solid rgba(0,0,0,0.2)', padding: '5px', paddingBottom: '0'}}
                            onClose={() => this.handleUpdateDriverSelectWindow(false)}>
                            <p style={{float:'left', padding: '12px'}}>
                                Driver search
                            </p>
                            <IconButton style={{float: 'right'}} aria-label="Close" onClick={() => this.handleUpdateDriverSelectWindow(false)}>
                                <CloseIcon />
                            </IconButton>
                        </DialogTitle>
                        <DialogContent style={{paddingTop: '10px', width: '400px', overflow: 'visible'}}>
                            <QuickSearch
                                placeholder="Search by email address, phone number, or user UID..."
                                searchQuery={'/api/users/quickSearch/'} 
                                handleClick={this.handleDriverClick}
                                objectMappingResult={{
                                    name: 'Name',
                                    surname:'Surname',
                                    email: 'Email',
                                    phone: 'Phone',
                                }}
                            />
                        </DialogContent>
                    </Dialog>
                </div>
                <div className='DriverDetails'>
                    <p style={{fontWeight: '400', borderBottom:'1px solid rgba(0,0,0,0.15)'}}>
                        Transport details:
                    </p>
                    <p>
                        Body type: {this.state.ttnDto.transport.bodyType}
                    </p>
                    <p>
                        Consumption: {this.state.ttnDto.transport.consumption}
                    </p>
                    <p>
                        Owner: {this.state.ttnDto.transport.owner.title}
                    </p>
                    <Button style={{float:'right'}} variant="outlined" color="primary" onClick={() => this.handleTransportDetailsShow(true)}>
                        Select transport
                    </Button>
                    <Dialog
                        onClose={() => this.handleTransportDetailsShow(false)}
                        aria-labelledby="customized-dialog-title"
                        open={this.state.transportSearchShow}
                        >
                        <DialogTitle 
                            id="customized-dialog-title" 
                            style={{borderBottom: '1px solid rgba(0,0,0,0.2)', padding: '5px', paddingBottom: '0'}}
                            onClose={() => this.handleTransportDetailsShow(false)}>
                            <p style={{float:'left', padding: '12px'}}>
                                Transport search:
                            </p>
                            <IconButton style={{float: 'right'}} aria-label="Close" onClick={() => this.handleTransportDetailsShow(false)}>
                                <CloseIcon />
                            </IconButton>
                        </DialogTitle>
                        <DialogContent style={{paddingTop: '10px', width: '400px', overflow: 'visible'}}>
                            <QuickSearch
                                placeholder="Search by id, title, body type or consumption..."
                                searchQuery={'/api/transports/quickSearch/'} 
                                handleClick={this.handleTransportClicked}
                                objectMappingResult={{
                                    title: 'Title',
                                    bodyType: 'Body type',
                                    consumption:'Consumption',
                                }}
                            />
                        </DialogContent>
                    </Dialog>
                </div>
            </Paper>
            </div>
        )
    };
};

const MapToStore = dispatch => {
    return {
        showMessage: (message) => dispatch({type: messageActions.SHOW_MESSAGE, message: message}),
        showErrorMessage: (message) => dispatch({type: messageActions.SHOW_ERROR_MESSAGE, message: message})
    }
};

export default connect(null, MapToStore)(withStyles(styles)(UpdateTtnConteiner));