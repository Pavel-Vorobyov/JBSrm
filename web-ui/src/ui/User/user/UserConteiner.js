import React, {Component} from 'react';
import axios from '../../../axios-messages';
import {mapData} from '../../../utill/ObjectMapper';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';


import Grid from '@material-ui/core/Grid';
import SearchIcon from '@material-ui/icons/Search';
import Toolbar from '@material-ui/core/Toolbar';

import '../../common/contentTable.css';
import './userConteiner.css';

import Table from './table/table';
import QuickSearch from '../../../components/quickSearch/QuickSearch';

const styles = theme => ({
    textField: {
      marginLeft: theme.spacing.unit,
      marginRight: theme.spacing.unit,
      width: 200,
    },
    menu: {
      width: 250,
    },
  });

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
        userFilter: {
            userRole: 'NONE',
            deleted: false,
            page: 0,
            size: 20,
        },
    }

    rows = [
        { id: 'name', numeric: false, disablePadding: false, label: 'Name' },
        { id: 'surname', numeric: false, disablePadding: false, label: 'Surname' },
        { id: 'email', numeric: false, disablePadding: false, label: 'Email' },
        { id: 'companyTitle', numeric: false, disablePadding: false, label: 'Company Title' },
        { id: 'userRole', numeric: false, disablePadding: false, label: 'User Role' },
      ];

    rowColums = {
        id: '',
        name: '',
        surname: '',
        email: '',
        companyTitle: '',
        userRole: '',
    }

    getValue() {
        return this.state.userFilter.userRole;
    }

    defaultTableHead = (
        <div className='tool-bar'>
            <Toolbar className='tool-bar'>
                <Grid item>
                    <SearchIcon  color="inherit" />
                </Grid>
                <Grid item xs style={{marginLeft: '10px'}}>
                    <QuickSearch 
                        placeholder="Search by email address, phone number, or user UID..."
                        searchQuery={'/api/users/quickSearch/'} 
                        handleClick={(id) => this.handleDetailsClick(id)}
                        objectMappingResult={{
                            name: 'Name',
                            surname:'Surname',
                            email:'Email',
                            phone: 'Phone',
                            userRole: 'User role',
                        }}/>
                </Grid>
            </Toolbar>
        </div>
    )

    componentWillMount() {
        this.updatePage(0, 10);
    }

    handleDetailsClick = id => {
        let currentPath = this.props.history.location.pathname;
        this.props.history.push(currentPath + '/update/' + id);
    }

    updatePage(currentPage, rowsPerPage) {
        let pageContent = this.state.page;
        let page = currentPage ? currentPage : this.state.pageParams.page;
        let size = rowsPerPage ? rowsPerPage : this.state.pageParams.rowsPerPage;
        let deleted = this.state.userFilter.deleted;
        let userRole = this.state.userFilter.userRole.includes('NONE') ? null : this.state.userFilter.userRole;

        let url = this.state.userFilter.userRole.includes('NONE') ? '/api/users/?page=' + page + '&size=' + size + '&deleted=' + deleted
            : '/api/users/?page=' + page + '&size=' + size + '&deleted=' + deleted + '&userRole=' + userRole;
        axios.get(url)
        .then ( response => {
            pageContent = response.data;

            let page = this.state.page;
            page.content = [];
            this.setState({
                ...this.state,
                page: page,
            });

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

    updateData(data) {
        if (data.length !== 0 && this.state.page) {
            data.forEach(element => {
                element['companyTitle'] = this.state.page.content.find(c => c.id === element.id).company.title;
            });
        }
        return data;
    }

    handleDeleteButton(selected) {
        let counter = 0;
        selected.forEach( id => {
            axios.put('/api/users/' + id + '/delete?deleted=true')
            .then ( response => {
                counter ++;
                if (counter === selected.length) {
                    this.updatePage(0, null);
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
        this.updatePage(currentPage, currentRowsPerPage);
      };

    handleChangePage = (event, page) => {
        this.updatePage(page, null)
    };

    handlePropChange = (name, event) => {
        let userFilter = this.state.userFilter;
        userFilter[name] = event.target.value;
        this.setState({
            userFilter: userFilter,
          });
        console.log(this.state.userFilter)
        this.updatePage(0, null);
    }

    render() {

        return (
            <div className="test-table">
                <Table 
                    handlePropChange={(name, event) => this.handlePropChange(name, event)}
                    propsValue={this.state.userFilter}
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

export default withStyles(styles)(TestTable);