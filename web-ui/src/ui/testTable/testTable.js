import React, {Component} from 'react';
import axios from '../../axios-messages';
import {mapData} from '../../utill/ObjectMapper';

import Typography from '@material-ui/core/Typography';

import Table from '../../components/table/table';

let counter = 0;

function createData(name, calories, fat, carbs, protein, test) {
    counter += 1;
    return { id: counter, name, calories, fat, carbs, protein, test };
  }

class TestTable extends Component {

    rows = [
        { id: 'name', numeric: false, disablePadding: true, label: 'Name' },
        { id: 'surname', numeric: false, disablePadding: true, label: 'Surname' },
        { id: 'email', numeric: false, disablePadding: true, label: 'Email' },
        { id: 'phone', numeric: true, disablePadding: false, label: 'Phone' },
        { id: 'company', numeric: false, disablePadding: true, label: 'Company' },
        { id: 'role', numeric: false, disablePadding: true, label: 'Role'}
      ];

    data = [
        createData('Cupcake', 305, 3.7, 67, 4.3, 'a'),
        createData('Donut', 452, 25.0, 51, 4.9, 'b'),
        createData('Eclair', 262, 16.0, 24, 6.0, 'c'),
        createData('Frozen yoghurt', 159, 6.0, 24, 4.0, 'd'),
        createData('Gingerbread', 356, 16.0, 49, 3.9, 'e'),
        createData('Honeycomb', 408, 3.2, 87, 6.5, 'r'),
        createData('Ice cream sandwich', 237, 9.0, 37, 4.3, 'b'),
        createData('Jelly Bean', 375, 0.0, 94, 0.0, 'b'),
        createData('KitKat', 518, 26.0, 65, 7.0, 'b'),
        createData('Lollipop', 392, 0.2, 98, 0.0, 'b'),
        createData('Marshmallow', 318, 0, 81, 2.0, 'b'),
        createData('Nougat', 360, 19.0, 9, 37.0, 'b'),
        createData('Oreo', 437, 18.0, 63, 4.0, 'b'),
    ];

    rowColums = {
        id: '',
        name: '',
        surname: '',
        email: '',
        phone: '',
        company: '',
        role: '',
    }

    defaultTableHead = (
        <Typography variant="h6" id="tableTitle">
            Nutrition
        </Typography>
    )

    state = {
        content: [],
        quickSerachResult: [],
        page: {
            content: []
        },
        userFilter: {
            age: null,
            companyId: null,
            userRole: null,
            deleted: false,
            page: 0,
            size: 20,
        },
        shouldShow: false,
        updateModalShow: false,
        modalIsOpen: false,
        modalContent: [],
        updateModalContent: {},
        clientDto: {
            name: '',
            surname: '',
            userGender: 'MALE',
            birthday: '2017-05-24',
            email: '',
            phone: '',
            company: '',
            clientRole: 'DRIVER'
          },
          companyName: 'User company...',
    }

    componentWillMount() {
        this.updatePage(0, 20, false);
    }

    updatePage(currentPage, rowsPerPage, currentDeleted) {
        let pageContent = this.state.page;
        let page = currentPage || currentPage !== 0 ? currentPage : this.state.page.number;
        let size = rowsPerPage ? rowsPerPage : this.state.page.numberOfElements;
        let deleted = currentDeleted ? currentDeleted : this.state.userFilter.deleted;
        
        axios.get('/api/users/?page=' + page + '&size=' + size + '&deleted=' + deleted)
        .then ( response => {
            pageContent = response.data;
            this.setState({...this.state, page: pageContent});
        });
    }

    updateData(data) {
        if (data.length !== 0 && this.state.page) {
            data.forEach(element => {
                element['company'] = this.state.page.content.find(c => c.id = element.id).company.title;
                element['role'] = this.state.page.content.find(c => c.id = element.id).userRole;
            });
        }
        return data;
    }

    render() {

        return (
            <div className="test-table">
                <Table 
                    rows={this.rows}
                    data={this.updateData(mapData(this.state.page.content, this.rowColums))}
                    defaultTableHead={this.defaultTableHead}
                    rowColums={this.rowColums}
                />
            </div>
        )
    };
};

export default TestTable;