import React, {Component} from 'react';
import axios from '../../../axios-messages';
import {mapData} from '../../../utill/ObjectMapper';
import {connect} from 'react-redux';
import * as messageActions from '../../../store/MessageAction';

import Grid from '@material-ui/core/Grid';
import SearchIcon from '@material-ui/icons/Search';
import Toolbar from '@material-ui/core/Toolbar';

import '../../common/contentTable.css';

import Table from '../table/table';
import QuickSearch from '../../../components/quickSearch/QuickSearch';

class TestTable extends Component {

    state = {
        content: [],
        quickSerachResult: [],
        page: {
            content: []
        },
        pageParams: {
            page: null,
            rowsPerPage: null,
            count: null,
        },
        waybillFilter: {
            deleted: false,
            ttnState: 'NONE',
            page: 0,
            size: 20,
        },
    }

    rows = [
        { id: 'driverName', numeric: false, disablePadding: true, label: 'Driver name' },
        { id: 'ttnState', numeric: false, disablePadding: true, label: 'TtnState' },
        { id: 'startDate', numeric: false, disablePadding: true, label: 'Start date' },
        { id: 'endDate', numeric: false, disablePadding: true, label: 'End date' },
        { id: 'createAt', numeric: false, disablePadding: true, label: 'Created at' },
      ];

    rowColums = {
        id: '',
        driverName: '',
        ttnState: '',
        startDate: '',
        endDate: '',
        createAt: '',
    }

    defaultTableHead = (
        <div className='tool-bar'>
            <Toolbar className='tool-bar'>
                <Grid item>
                    <SearchIcon  color="inherit" />
                </Grid>
                <Grid item xs style={{marginLeft: '10px'}}>
                    <QuickSearch 
                        placeholder="Search by id, title, transport consumption or driver..."
                        searchQuery={'/api/waybills/quickSearch/'} 
                        handleClick={(id) => this.handleDetailsClick(id)}
                        objectMappingResult={{
                            createdAt: 'Create at',
                            startDate:'Start date',
                            endDate: 'End date',
                            ttnState: 'Ttn state',
                        }}/>
                </Grid>
            </Toolbar>
        </div>
    )

    handleDetailsClick = id => {
        let currentPath = this.props.history.location.pathname;
        this.props.history.push(currentPath + '/update/' + id);
    }

    componentWillMount() {
        this.updatePage(0, 10, false);
    }

    updatePage(currentPage, rowsPerPage) {
        let pageContent = this.state.page;
        let page = currentPage ? currentPage : this.state.pageParams.page;
        let size = rowsPerPage ? rowsPerPage : this.state.pageParams.rowsPerPage;
        let deleted = this.state.waybillFilter.deleted;
        let ttnState = this.state.waybillFilter.ttnState.includes('NONE') ? null : this.state.waybillFilter.ttnState;

        let url = this.state.waybillFilter.ttnState.includes('NONE') ? '/api/waybills/?page=' + page + '&size=' + size + '&deleted=' + deleted
            : '/api/waybills/?page=' + page + '&size=' + size + '&deleted=' + deleted + '&ttnState=' + ttnState;
        
        axios.get(url)
        .then ( response => {
            pageContent = response.data;

            let page = this.state.page;
            page.content = [];
            this.setState({
                ...this.state,
                page: page,
            })

            console.log(response)

            this.setState({
                ...this.state, 
                page: pageContent,
                pageParams: {
                    page: pageContent.number,
                    rowsPerPage: pageContent.size,
                    count: pageContent.totalElements,
                }
            });
        });
    }

    handleDeleteButton(selected) {
        let counter = 0;
        selected.forEach( id => {
            axios.put('/api/waybills/' + id + '/delete?deleted=true')
            .then ( response => {
                counter ++;
                if (counter === selected.length) {
                    this.updatePage(0, this.state.pageParams.rowsPerPage, false);
                }
            });
        });
    }

    handleRowClicked = id => {
        let currentPath = this.props.history.location.pathname;
        this.props.history.push(currentPath + '/update/' + id);
    }

    handleNewButtonClicked() {
        let currentPath = this.props.history.location.pathname;
        this.props.history.push(currentPath + '/create');
    }

    handleChangeRowsPerPage = event => {
        let currentPage = this.state.page.number;
        let currentRowsPerPage = event.target.value ? event.target.value : this.state.page.rowsPerPage;
        axios.get('/api/waybills/?page=' + currentPage + '&size=' + currentRowsPerPage + '&deleted=' + this.state.waybillFilter.deleted)
        .then ( response => {
            let pageParams = this.state.pageParams;
            pageParams.rowsPerPage = event.target.value;

            this.setState({...this.state, page: response.data, pageParams: pageParams});
        });
      };

    handleChangePage = (event, page) => {
        this.updatePage(page, null);
    };

    updateData(data) {
        console.log(this.state.page.content)
        if (data.length !== 0 && this.state.page) {
            data.forEach(element => {
                element['driverName'] = this.state.page.content.find(c => c.id === element.id).ttn.driver.name + ' ' + this.state.page.content.find(c => c.id === element.id).ttn.driver.surname;
                element['ttnState'] = this.state.page.content.find(c => c.id === element.id).ttn.ttnState;
                element['startDate'] = this.state.page.content.find(c => c.id === element.id).startDate;
                element['endDate'] = this.state.page.content.find(c => c.id === element.id).endDate;
                element['createAt'] = this.state.page.content.find(c => c.id === element.id).createdAt;
                console.log(element);
                console.log(this.state.page.content);
                console.log(this.state.page.content.find(c => c.id = element.id));
            });
        }
        return data;
    }

    handlePropChange = (name, event) => {
        let waybillFilter = this.state.waybillFilter;
        waybillFilter[name] = event.target.value;
        this.setState({
            waybillFilter: waybillFilter,
          });
        this.updatePage(0, null);
    }

    handleCloseWaybillClicked = selected => {
        if (typeof selected === 'object' && selected.length > 0) {
            let counter = 0;
            selected.forEach( id => {
                let waybillItem = this.state.page.content.find(c => {return c.id == selected});
                let waybillId = waybillItem.id;
                let ttnState = 'DELIVERED';
                axios.put('/api/waybills/' + id, {waybillId, ttnState})
                .then ( response => {
                    counter ++;
                    if (counter === selected.length) {
                        this.updatePage(0, this.state.pageParams.rowsPerPage, false);
                    }
                })
                .catch( error => {
                    this.props.showErrorMessage(error.toString());
                });
            });
        } else {
            if (typeof selected === 'object') {
                this.props.showErrorMessage('PLease select at least one item!')
            }
        }
    }

    render() {

        return (
            <div className="test-table">
                <Table 
                    handlePropChange={(name, event) => this.handlePropChange(name, event)}
                    propsValue={this.state.waybillFilter}
                    rows={this.rows}
                    pageParams={this.state.pageParams}
                    newClicked={() => this.handleNewButtonClicked()}
                    closeClicked={(selected) => this.handleCloseWaybillClicked(selected)}
                    rowOnClick={(id) => this.handleRowClicked(id)}
                    data={this.updateData(mapData(this.state.page.content, this.rowColums))}
                    defaultTableHead={this.defaultTableHead}
                    rowColums={this.rowColums}
                    handleChangeRowsPerPage={(event) => this.handleChangeRowsPerPage(event)}
                    handleChangePage={(event, page) => this.handleChangePage(event, page)}
                    deleteButtonClicked={(selected) => this.handleDeleteButton(selected)}
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

export default connect(null, MapToStore)(TestTable);