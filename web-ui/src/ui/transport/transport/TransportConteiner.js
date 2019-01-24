import React, {Component} from 'react';
import axios from '../../../axios-messages';
import {mapData} from '../../../utill/ObjectMapper';

import Grid from '@material-ui/core/Grid';
import SearchIcon from '@material-ui/icons/Search';
import Typography from '@material-ui/core/Typography';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';

import '../../common/contentTable.css';

import Table from './table/table';
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
        transportFilter: {
            deleted: false,
            transportState: 'FREE',
            bodyType: 'NONE',
            page: 0,
            size: 20,
        },
    }

    rows = [
        { id: 'title', numeric: false, disablePadding: true, label: 'Title' },
        { id: 'owner', numeric: false, disablePadding: true, label: 'Owner' },
        { id: 'consumption', numeric: false, disablePadding: true, label: 'Consumption' },
        { id: 'bodyType', numeric: false, disablePadding: true, label: 'Body type' },
      ];

    rowColums = {
        id: '',
        title: '',
        owner: '',
        consumption: '',
        bodyType: '',
    }

    defaultTableHead = (
        <div className='tool-bar'>
            <Toolbar className='tool-bar'>
                <Grid item>
                    <SearchIcon  color="inherit" />
                </Grid>
                <Grid item xs style={{marginLeft: '10px'}}>
                    <QuickSearch 
                        placeholder="Search by id, title, body type or consumption..."
                        searchQuery={'/api/transports/quickSearch/'} 
                        handleClick={(id) => this.handleDetailsClick(id)}
                        objectMappingResult={{
                            title: 'Title',
                            consumption:'Consumption',
                            bodyType: 'Body type',
                            transportState: 'Transport state',
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
        this.updatePage(0, 10);
    }

    updatePage(currentPage, rowsPerPage) {
        let pageContent = this.state.page;
        let page = currentPage; 
        let size = rowsPerPage && rowsPerPage !== 0 ? rowsPerPage : this.state.pageParams.rowsPerPage;
        let deleted = this.state.transportFilter.deleted;
        let transportState = this.state.transportFilter.transportState;
        let bodyType = this.state.transportFilter.bodyType;
        
        let url = bodyType === 'NONE'
            ? '/api/transports/?page=' + page + '&size=' + size + '&deleted=' + deleted + '&transportState=' + transportState
            : '/api/transports/?page=' + page + '&size=' + size + '&deleted=' + deleted + '&transportState=' + transportState  + '&bodyType=' + bodyType

        axios.get(url)
        .then ( response => {
            pageContent = response.data;
            this.setState({
                ...this.state, 
                page: pageContent,
                pageParams: {
                    page: pageContent.number,
                    rowsPerPage: pageContent.size,
                    count: pageContent.totalElements,
                }
            });
        })
        .catch( error => {
        });
    }

    handleDeleteButton(selected) {
        let counter = 0;
        selected.forEach( id => {
            axios.put('/api/transports/' + id + '/delete?deleted=true')
            .then ( response => {
                counter ++;
                if (counter === selected.length) {
                    this.updatePage(0, null, false);
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
        axios.get('/api/transports/?page=' + currentPage + '&size=' + currentRowsPerPage + '&deleted=' + this.state.transportFilter.deleted)
        .then ( response => {
            let pageParams = this.state.pageParams;
            pageParams.rowsPerPage = event.target.value;

            this.setState({...this.state, page: response.data, pageParams: pageParams});
        });
      };

    handleChangePage = (event, page) => {
        this.updatePage(page, null)

    };

    updateData(data) {
        if (data.length !== 0 && this.state.page) {
            data.forEach(element => {
                element['owner'] = this.state.page.content.find(c => c.id === element.id).owner.title;
            });
        }
        return data;
    }

    handlePropChange = (name, event) => {
        let transportFilter = this.state.transportFilter;
        transportFilter[name] = event.target.value;
        this.setState({
            transportFilter: transportFilter,
          });
        this.updatePage(0, null);
    }

    render() {

        return (
            <div className="test-table">
                <Table 
                    handlePropChange={(name, event) => this.handlePropChange(name, event)}
                    propsValue={this.state.transportFilter}
                    rows={this.rows}
                    pageParams={this.state.pageParams}
                    newClicked={() => this.handleNewButtonClicked()}
                    rowOnClick={(id) => this.handleRowClicked(id)}
                    data={this.updateData(mapData(this.state.page.content, this.rowColums))}
                    defaultTableHead={this.defaultTableHead}
                    rowColums={this.rowColums}
                    handleChangeRowsPerPage={(event) => this.handleChangeRowsPerPage(event)}
                    handleChangePage={(event, page) => this.handleChangePage(event, page)}
                    deleteButtonClicked={(selected) => this.handleDeleteButton(selected)}
                />
                />
            </div>
        )
    };
};

export default TestTable;