import React from 'react';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import TableSortLabel from '@material-ui/core/TableSortLabel';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Checkbox from '@material-ui/core/Checkbox';
import IconButton from '@material-ui/core/IconButton';
import Tooltip from '@material-ui/core/Tooltip';
import DeleteIcon from '@material-ui/icons/Delete';
import FilterListIcon from '@material-ui/icons/FilterList';
import { lighten } from '@material-ui/core/styles/colorManipulator';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';

import './table.css';
import { Button } from '@material-ui/core';
import QuickSearch from '../../../../components/quickSearch/QuickSearch';

function desc(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function stableSort(array, cmp) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = cmp(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  return stabilizedThis.map(el => el[0]);
}

function getSorting(order, orderBy) {
  return order === 'desc' ? (a, b) => desc(a, b, orderBy) : (a, b) => -desc(a, b, orderBy);
}

const ttnState = [
  {
    value: 'NONE',
    label: 'None',
  },
  {
    value: 'ACCEPTED',
    label: 'Accepted',
  },
  {
    value: 'CHECK_COMPLETE',
    label: 'Check complete',
  },
  {
    value: 'TRANSPORTATION_STARTED',
    label: 'Transportation started',
  },
  {
    value: 'DELIVERED',
    label: 'Delivered',
  },
];

const deleted = [
  {
    value: 'false',
    label: 'False'
  },
  {
    value: 'true',
    label: 'True'
  }
]

class EnhancedTableHead extends React.Component {
  createSortHandler = property => event => {
    this.props.onRequestSort(event, property);
  };

  render() {
    const { onSelectAllClick, order, orderBy, numSelected, rowCount } = this.props;

    return (
      <TableHead>
        <TableRow>
          <TableCell padding="checkbox">
            <Checkbox
              indeterminate={numSelected > 0 && numSelected < rowCount}
              checked={numSelected === rowCount}
              onChange={onSelectAllClick}
            />
          </TableCell>
          {this.props.rows.map(
            row => (
              <TableCell
                key={row.id}
                align={row.numeric ? 'right' : 'left'}
                padding={row.disablePadding ? 'none' : 'default'}
                sortDirection={orderBy === row.id ? order : false}
              >
                <Tooltip
                  title="Sort"
                  placement={row.numeric ? 'bottom-end' : 'bottom-start'}
                  enterDelay={300}
                >
                  <TableSortLabel
                    active={orderBy === row.id}
                    direction={order}
                    onClick={this.createSortHandler(row.id)}
                  >
                    {row.label}
                  </TableSortLabel>
                </Tooltip>
              </TableCell>
            ),
            this,
          )}
        </TableRow>
      </TableHead>
    );
  }
}

EnhancedTableHead.propTypes = {
  numSelected: PropTypes.number.isRequired,
  onRequestSort: PropTypes.func.isRequired,
  onSelectAllClick: PropTypes.func.isRequired,
  order: PropTypes.string.isRequired,
  orderBy: PropTypes.string.isRequired,
  rowCount: PropTypes.number.isRequired,
};



const toolbarStyles = theme => ({
  root: {
    paddingRight: theme.spacing.unit,
  },
  highlight:
    theme.palette.type === 'light'
      ? {
          color: theme.palette.secondary.main,
          backgroundColor: lighten(theme.palette.secondary.light, 0.85),
        }
      : {
          color: theme.palette.text.primary,
          backgroundColor: theme.palette.secondary.dark,
        },
  spacer: {
    flex: '1 1 100%',
  },
  actions: {
    color: theme.palette.text.secondary,
  },
  title: {
    flex: '0 0 auto',
  },
});

let EnhancedTableToolbar = props => {
  const { numSelected, classes, selected, defaultTableHead, handlePropChange, propsValue } = props;

  return (
    <Toolbar
      className={classNames(classes.root, {
        [classes.highlight]: numSelected > 0,
      })}
    >
      <div className={classes.title}>
        {numSelected > 0 ? (
          <Typography color="inherit" variant="subtitle1">
            {numSelected} selected
          </Typography>
        ) : (
          <div>
            {defaultTableHead}
          </div>
        )}
      </div>
      <div className={classes.spacer} />
      <div className={classes.actions}>
        {numSelected > 0 ? (
          <Tooltip title="Delete">
            <IconButton aria-label="Delete" onClick={() => props.deleteButtonClicked(selected)}>
              <DeleteIcon />
            </IconButton>
          </Tooltip>
        ) : (
          <div style={{width: '500px', height: '0'}}>

          <TextField
              id="standard-select-currency-native"
              select
              style={{marginRight: '20px'}}
              label="Ttn state"
              value={propsValue.ttnState}
              className='table-search-props'
              onChange={(event) => props.handlePropChange('ttnState', event)}
              SelectProps={{
                MenuProps: {
                  className: 'prop-menu',
                },
              }}
              margin="normal"
            > 
            {ttnState.map(option => (
              <MenuItem className='prop-option' key={option.value} value={option.value}>
                  {option.label}
            </MenuItem>
            ))}
          </TextField>

          <TextField
              id="standard-select-currency-native"
              select
              style={{marginRight: '20px'}}
              label="Deleted"
              value={propsValue.deleted}
              className='table-search-props'
              onChange={(event) => props.handlePropChange('deleted', event)}
              SelectProps={{
                MenuProps: {
                  className: 'prop-menu',
                },
              }}
              margin="normal"
            > 
            {deleted.map(option => (
              <MenuItem className='prop-option' key={option.value} value={option.value}>
                  {option.label}
            </MenuItem>
            ))}
          </TextField>


            <Tooltip title="Create new entity">
              <Button variant='outlined' style={{height: '5px', padding: '0'}} onClick={props.newClicked}>New</Button>
            </Tooltip>
            <Tooltip title="Filter list">
            <IconButton aria-label="Filter list">
              <FilterListIcon />
            </IconButton>
          </Tooltip>
          </div>
        )}
      </div>
    </Toolbar>
  );
};

EnhancedTableToolbar.propTypes = {
  classes: PropTypes.object.isRequired,
  numSelected: PropTypes.number.isRequired,
};

EnhancedTableToolbar = withStyles(toolbarStyles)(EnhancedTableToolbar);

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: theme.spacing.unit * 3,
  },
  table: {
    minWidth: 1020,
  },
  tableWrapper: {
    overflowX: 'auto',
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
    width: 200,
  },
  menu: {
    width: 200,
  },
});

class EnhancedTable extends React.Component {
  state = {
    order: 'asc',
    orderBy: 'calories',
    selected: [],
    
    page: 0,
    count: 0,
    rowsPerPage: 5,
  };

  componentDidMount() {
    let { pageParams } = this.props;
    if (pageParams.page && pageParams.rowsPerPage && pageParams.count) {
      this.setState({
        ...this.state,
        page: pageParams.page,
        count: pageParams.count,
        rowsPerPage: pageParams.rowsPerPage,
      })
    }
  }

  handleRequestSort = (event, property) => {
    const orderBy = property;
    let order = 'desc';

    if (this.state.orderBy === property && this.state.order === 'desc') {
      order = 'asc';
    }

    this.setState({ order, orderBy });
  };

  handleSelectAllClick = event => {
    if (event.target.checked) {
      this.setState(state => ({ selected: this.props.data.map(n => n.id) }));
      return;
    }
    this.setState({ selected: [] });
  };

  handleClick = (event, id) => {
    const { selected } = this.state;
    const selectedIndex = selected.indexOf(id);
    let newSelected = [];

    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, id);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1),
      );
    }

    this.setState({ selected: newSelected });
  };

  handleDelete() {
    this.props.deleteButtonClicked(this.state.selected);
    this.setState({
      ...this.state,
      selected: []
    })
  }

 

  isSelected = id => this.state.selected.indexOf(id) !== -1;

  render() {
    const { classes } = this.props;
    const { handleChangeRowsPerPage, handleChangePage, propsValue, handlePropChange } = this.props;
    const {data, defaultTableHead, rows, newClicked, pageParams } = this.props;
    const { order, orderBy, selected } = this.state;
    const rowsPerPage = pageParams.rowsPerPage;
    const page = pageParams.page;
    const count = pageParams.count;
    const emptyRows = rowsPerPage - data.length;

    return (
      <Paper className={classes.root}>
        <EnhancedTableToolbar 
          propsValue={propsValue}
          handlePropChange={handlePropChange}
          defaultTableHead={defaultTableHead} 
          newClicked={newClicked}
          selected={selected} 
          numSelected={selected.length} 
          deleteButtonClicked={() => this.handleDelete()} />
        <div className={classes.tableWrapper}>
          <Table className={classes.table} aria-labelledby="tableTitle">
            <EnhancedTableHead
              rows={rows}
              numSelected={selected.length}
              order={order}
              orderBy={orderBy}
              onSelectAllClick={this.handleSelectAllClick}
              onRequestSort={this.handleRequestSort}
              rowCount={data.length}
            />
            <TableBody>
              {stableSort(data, getSorting(order, orderBy))
                .map(n => {
                  const isSelected = this.isSelected(n.id);
                  return (
                    <TableRow
                      hover
                      onClick={event => this.handleClick(event, n.id)}
                      role="checkbox"
                      aria-checked={isSelected}
                      tabIndex={-1}
                      key={n.id}
                      selected={isSelected}
                    >
                      <TableCell padding="checkbox">
                        <Checkbox checked={isSelected} />
                      </TableCell>
                      {
                        Object.keys(this.props.rowColums).map( (name, index) => {
                          if (name !== 'id') {
                            if (index === 0) {
                              return (<TableCell component="th" scope="row"  padding="none">
                                        <a className='item-link' onClick={() => this.props.rowOnClick(n.id)}>{n[name]}</a>
                                      </TableCell>)
                            } else {
                              return (
                                <TableCell align="right"><a className='item-link'  onClick={() => this.props.rowOnClick(n.id)}>{n[name]}</a></TableCell>
                              )
                            }
                          }
                        })
                      }
                    </TableRow>
                  );
                })}
              {emptyRows > 0 && (
                <TableRow style={{ height: 49 * emptyRows }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
          </Table>
        </div>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={count}
          rowsPerPage={rowsPerPage}
          page={page}
          backIconButtonProps={{
            'aria-label': 'Previous Page',
          }}
          nextIconButtonProps={{
            'aria-label': 'Next Page',
          }}
          onChangePage={handleChangePage}
          onChangeRowsPerPage={handleChangeRowsPerPage}
        />
      </Paper>
    );
  }
}

EnhancedTable.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(EnhancedTable);