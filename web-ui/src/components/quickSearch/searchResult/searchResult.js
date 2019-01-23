import React from 'react';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import { TableHead } from '@material-ui/core';

const style ={
    tableHeader: {

    },
    taleHeaderContent: {

    },
    tableCell: {
        fontSize: '18px',
    color: 'rgba(0, 0, 0, 0.87)',
    fontWeight: 700,
    overflow: 'hidden',
    }
}

const searchResult = props => {

    let resultHeader;
    let result;

    if (props.shouldShow && props.content) {
        let headerContent = Object.keys(props.objectMappingResult).map( (key, index) => {
            return <TableCell key={index} style={style.taleHeaderContent}>{props.objectMappingResult[key]}</TableCell>
        });
        resultHeader = <TableRow style={style.tableHeader}>{headerContent}</TableRow>;
        result = props.content.map( (child, index) => {
            let resultList;
            if (props.objectMappingResult) {
                resultList = Object.keys(props.objectMappingResult).map( key => {
                    if (key !== 'id' && child.hasOwnProperty(key)) {
                        return <TableCell key={index} style={style.tableCell}>{child[key]}</TableCell>
                    }
                });
            }

            return <TableRow hover onClick={() => {props.handleDetailsClick(child.id)}}>{resultList}</TableRow>;
        });
    }


    return (
            <Table>
                <TableHead >{resultHeader}</TableHead>
                <TableBody style={{padding: '20px'}}>
                    {result}
                </TableBody>
            </Table>
    )
};

export default searchResult;