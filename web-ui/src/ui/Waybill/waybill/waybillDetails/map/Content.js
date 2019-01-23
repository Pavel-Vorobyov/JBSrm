import React, {Component} from 'react';
import {connect} from 'react-redux';

import { withStyles } from '@material-ui/core/styles';
import { TextField, Paper, Table, Fab, TableBody, TableCell, TableRow, Button, Dialog, DialogTitle, DialogContent } from '@material-ui/core';
import CloseIcon from '@material-ui/icons/Close';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import Checkbox from '@material-ui/core/Checkbox';

import * as messageActions from '../../../../../store/MessageAction';
import QuickSearch from '../../../../../components/quickSearch/QuickSearch';
import {dateConverte} from '../../../../../utill/DateConverter';

const tabProps = {
    window1: {
        maxWidth: '287px',
        maxHeight: '392px',
        overflow: 'auto',
    },
    window2: {
        maxHeight: '280px',
    }
}

const styles = theme => ({
    container: {
      display: 'flex',
      flexWrap: 'wrap',
    },
    textField: {
      marginLeft: theme.spacing.unit,
      marginRight: theme.spacing.unit,
      width: 240,
      float: 'right',

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
    markTitle: {
        display: 'inline-block',
    },
    deleteButton: {
        float: 'right',
        marginRight: '12px',
    }
  });

class test2 extends Component {

    state = {
        window1: {
            maxWidth: '287px',
            maxHeight: '392px',
            overflow: 'auto',
            isVisible: true,

        },
        window2: {
            maxHeight: '280px',
        }
    }

    handleCloseWindowButton() {
        let window1 = this.state.window1;
        window1.isVisible = !window1.isVisible;

        this.setState({
            ...this.state,
            window1: window1,
        })
        this.handleUpdateWindow();
    }

    handleUpdateWindow() {
        let window1 = this.state.window1;
        let window2 = this.state.window2;

        if (this.state.window1.isVisible) {
            window1.maxHeight = tabProps.window1.maxHeight;
            window1.maxWidth = tabProps.window1.maxWidth;
            window1.overflow = tabProps.window1.overflow;
            window2.maxHeight = tabProps.window2.maxHeight;
        } else {
            window1.maxHeight = '45px';
            window1.maxWidth = '45px';
            window1.overflow = 'hidden';
            window2.maxHeight = '45px';
        }

        this.setState({
            ...this.state,
            window1: window1,
            window2: window2
        })
    }

    render() {
        let { classes, waybillDto } = this.props;
        console.log(waybillDto.startDate)

        return (
            <div className='MapInboxWrapper'>
                <div className='MapInbox' 
                    style={{
                        height: this.state.window1.maxHeight, 
                        width: this.state.window1.maxWidth,
                        overflow: this.state.window1.overflow,
                    }}>

                <Fab color="primary" size='small' style={{float: 'right', top: '-5px', right: '-5px', height: '35px', width: '35px'}} onClick={() => this.handleCloseWindowButton()}>
                </Fab>

                    Waybill details:
    
                    <div className='text-field'>
                        <p style={{fontWeight: '400', borderBottom:'1px solid rgba(0,0,0,0.15)'}}>
                            
                        </p>
                        <p>
                            Created at: {waybillDto.createAt}
                        </p>
                        <p>
                            <TextField
                                id="date"
                                label="Start date"
                                type="date"
                                value={dateConverte(waybillDto.startDate)}
                                className={classes.textField}
                                onChange={event => this.props.handleDateChange(event, 'start')}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                        </p>
                        
                        <p style={{paddingBottom:'90px'}}>
                            <TextField
                                id="date"
                                label="End dateна"
                                type="date"
                                value={dateConverte(waybillDto.endDate)}
                                className={classes.textField}
                                onChange={event => this.props.handleDateChange(event, 'end')}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                        </p>
                        <p style={{fontWeight: '400', borderBottom:'1px solid rgba(0,0,0,0.15)'}}>
                            Ttn details:
                        </p>
                        
                        <p>
                            Driver
                        </p>
                        <p>
                            Name: {this.props.waybillDto.ttn.driver.name}
                        </p>
                        <p>
                            Surname: {this.props.waybillDto.ttn.driver.surname}
                        </p>
                        <p>
                            Created at: {this.props.waybillDto.ttn.createAt}
                        </p>
                        <Button style={{float:'left'}} variant="outlined" color="primary" onClick={() => this.props.handleSaveButton()}>
                            Save
                        </Button>
                        <Button style={{float:'right'}} variant="outlined" color="primary" onClick={() => this.props.handleUpdateTtnSelectWindow(true)}>
                            Select ttn
                        </Button>
                        <Dialog
                            onClose={() => this.props.handleUpdateTtnSelectWindow(false)}
                            aria-labelledby="customized-dialog-title"
                            open={this.props.selectTtnShow}
                            >
                            <DialogTitle 
                                id="customized-dialog-title" 
                                style={{borderBottom: '1px solid rgba(0,0,0,0.2)', padding: '5px', paddingBottom: '0'}}
                                onClose={() => this.props.handleUpdateTtnSelectWindow(false)}>
                                <p style={{float:'left', padding: '12px'}}>
                                    Select ttn:
                                </p>
                                <IconButton style={{float: 'right'}} aria-label="Close" onClick={() => this.props.handleUpdateTtnSelectWindow(false)}>
                                    <CloseIcon />
                                </IconButton>
                            </DialogTitle>
                            <DialogContent style={{paddingTop: '10px', width: '400px', overflow: 'visible'}}>
                                <QuickSearch
                                    searchQuery={'/api/ttns/quickSearch/' + 'ACCEPTED '} 
                                    handleClick={this.props.handleTtnClick}
                                    objectMappingResult={{
                                        driverName: 'Driver name',
                                        driverSurname:'',
                                        ttnState: 'Ttn state',
                                        createdByName: 'Created by',
                                        createdBySurname: '',
                                        createdAt: 'Created at',
                                    }}
                                />
                            </DialogContent>
                        </Dialog>
                    </div>
                </div>
                <div className='MapInbox' 
                    style={{
                        padding: '0',
                        maxHeight: this.state.window2.maxHeight, 
                        width: this.state.window1.maxWidth, 
                        overflow: this.state.window1.overflow
                    }}>
                    {this.props.marks.map((mark, index) => 
                    <div className='mark-wrapper'>
                        <Checkbox
                            checked={mark.checkPointStatus === 'PASSED'}
                            value="checkedB"
                            color="primary"
                        />
                        <p key={index} className='MarkInbox' onClick={() => this.props.handleMarkListClicked(index)}>{mark.title}</p>
                        <IconButton onClick={() => this.props.handleMarkDeleteButton(index)} aria-label="Delete" className='' >
                            <DeleteIcon fontSize="small" />
                        </IconButton>
                    </div>
                        
                    )}
                </div>
            </div>
        )
    }
};

const MapToStore = dispatch => {
    return {
        showMessage: (message) => dispatch({type: messageActions.SHOW_MESSAGE, message: message})
    }
};

export default connect(null, MapToStore)(withStyles(styles)(test2));