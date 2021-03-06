import React, {Component} from 'react';
import axios from '../../../axios-messages';
import {mapData} from '../../../utill/ObjectMapper';

import Grid from '@material-ui/core/Grid';
import SearchIcon from '@material-ui/icons/Search';
import Typography from '@material-ui/core/Typography';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';

import '../../common/contentTable.css';

import Table from '../../../components/table/table';
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
        companyFilter: {
            deleted: false,
            page: 0,
            size: 20,
        },
    }

    rows = [
        { id: 'productTitle', numeric: false, disablePadding: true, label: 'Product Title' },
        { id: 'amount', numeric: false, disablePadding: false, label: 'Amount' },
        { id: 'productState', numeric: false, disablePadding: false, label: 'Product state' },
        { id: 'price', numeric: true, disablePadding: false, label: 'Price' },
        { id: 'createAt', numeric: false, disablePadding: false, label: 'Create at' },
      ];

    rowColums = {
        id: '',
        productTitle: '',
        amount: '',
        productState: '',
        price: '',
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
                        searchQuery={'/api/deeds/quickSearch/'} 
                        handleClick={this.handleDetailsClick}
                        objectMappingResult={{
                            title: 'Title:',
                            email:'Email:',
                            phone: 'Phone:',
                        }}/>
                </Grid>
            </Toolbar>
        </div>
    )

    componentWillMount() {
        this.updatePage(0, 10, false);
    }

    updatePage(currentPage, rowsPerPage, currentDeleted) {
        let pageContent = this.state.page;
        let page = currentPage; 
        let size = rowsPerPage && rowsPerPage !== 0 ? rowsPerPage : this.state.page.numberOfElements;
        let deleted = currentDeleted ? currentDeleted : this.state.companyFilter.deleted;
        
        axios.get('/api/deeds/?page=' + page + '&size=' + size + '&deleted=' + deleted)
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
        });
    }

    handleDeleteButton(selected) {
        let counter = 0;
        selected.forEach( id => {
            axios.put('/api/deeds/' + id + '/delete?deleted=true')
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
        axios.get('/api/deeds/?page=' + currentPage + '&size=' + currentRowsPerPage + '&deleted=' + this.state.companyFilter.deleted)
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
                element['productTitle'] = this.state.page.content.find(c => c.id = element.id).product.productDetails.title;
                element['amount'] = this.state.page.content.find(c => c.id = element.id).product.amount;
                element['productState'] = this.state.page.content.find(c => c.id = element.id).product.productState;
                element['createAt'] = this.state.page.content.find(c => c.id = element.id).createAt;
            });
        }
        return data;
    }

    render() {

        return (
            <div className="test-table">
                <Table 
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
            </div>
        )
    };
};

export default TestTable;