import React, {Component} from 'react';
import axios from '../../../../../axios-messages';
import {connect} from 'react-redux';

import Content from './Content';
import * as messageActions from '../../../../../store/MessageAction';

class Test extends Component {

    state = {
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
        selectTtnShow: false,
    }

    componentDidMount() {
        if (window.location.pathname.includes('update')) {
            let waybillId = window.location.pathname.substring(15);
            axios.get('/api/waybills' + waybillId)
            .then( response => {
                this.setState({
                    ...this.state,
                    waybillDto: response.data,
                });
            });
          }
    }

    handleUpdateTtnSelectWindow = isOpen => {
        this.setState({
            ...this.state,
            selectTtnShow: isOpen,
        });
    }

    handleTtnClick = id => {
        if (this.checkRole()) {
            axios.get('/api/ttns/' + id)
            .then(response => {
                let waybillDto = this.state.waybillDto;
                waybillDto.ttn = response.data;
                this.setState({
                    ...this.state,
                    waybillDto: waybillDto,
                    selectTtnShow: false,
                })
                console.log(this.state.waybillDto)
            });
        }
    }

    handleDateChange = (event, type) => {
        if (this.checkRole()) {
            let waybillDto = this.state.waybillDto;
            
            if(type === 'start') {
                waybillDto.startDate = event.target.value;
            } else {
                waybillDto.endDate = event.target.value;
            }

            this.setState({
                ...this.state,
                waybillDto: waybillDto,
            })
        }
    }

    checkRole() {
        let userRole = localStorage.getItem('userRole');
        return userRole === 'ROLE_MANAGER' || userRole === 'ROLE_ADMIN';
      }

    handleSaveButton() {
        let waybillDto = this.state.waybillDto;
        waybillDto.checkPoints = this.props.marks;
        if (window.location.pathname.includes('update')) {
            axios.put('api/waybills/' + waybillDto.id, {
                ttnId: this.state.waybillDto.ttn.id,
                ttnState: this.state.waybillDto.ttn.ttnState,
                startDate: this.state.waybillDto.startDate,
                endDate: this.state.waybillDto.endDate,
                checkPoints: this.props.marks,
            })
            .then(response => {
                this.props.showMessage('Waybill has been created successfully!');
                window.history.back();
            }).catch(error => {
                this.props.showErrorMessage(error.toString());
            });
        } else {
            if (this.checkRole() ) {
                console.log(this.state.waybillDto)
                axios.post('api/waybills/', {
                    ttnId: this.state.waybillDto.ttn.id,
                    ttnState: this.state.waybillDto.ttn.ttnState,
                    startDate: this.state.waybillDto.startDate,
                    endDate: this.state.waybillDto.endDate,
                    checkPoints: this.props.marks,
                })
                .then(response => {
                    this.props.showMessage('Waybill has been created successfully!');
                    window.history.back();
                }).catch(error => {
                    this.props.showErrorMessage('Please enter all fields!');
                });
            }
        }
    }

    handleDeliveredClicked() {
        let waybillDto = this.state.waybillDto;

        axios.put('api/waybills/' + waybillDto.id, {
            ttnId: this.state.waybillDto.ttn.id,
            ttnState: 'DELIVERED',
            startDate: this.state.waybillDto.startDate,
            endDate: this.state.waybillDto.endDate,
            checkPoints: this.props.marks,
        })
        .then(response => {
            this.props.showMessage('Waybill has been created successfully!');
            window.history.back();
        }).catch(error => {
            this.props.showErrorMessage('Please enter all fields!');
        });
    }

    render() {
        return(
            <div >
                <Content
                    waybillDto={this.state.waybillDto}
                    selectTtnShow={this.state.selectTtnShow}
                    marks={this.props.marks}
                    handleMarkListClicked={index => this.props.handleMarkListClicked(index)}
                    handleMarkDeleteButton={index => this.props.handleMarkDeleteButton(index)}
                    handleUpdateTtnSelectWindow={isOpen => this.handleUpdateTtnSelectWindow(isOpen)}
                    handleDeliveredClicked={() => this.handleDeliveredClicked()}
                    handleTtnClick={id => this.handleTtnClick(id)}
                    handleDateChange={(event, type) => this.handleDateChange(event, type)}
                    handleSaveButton={() => this.handleSaveButton()}
                 />
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

export default connect(null, MapToStore)(Test);