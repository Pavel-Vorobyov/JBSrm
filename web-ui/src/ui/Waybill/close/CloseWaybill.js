import React from 'react';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';

const closeWaybill = props => {

    let waibillId = props.match.params.id;

    return (
        <div>
            <Paper>
            <Typography variant="h5" component="h3">
                This is a sheet of paper.
            </Typography>
         </Paper>
        </div>
    );
};

export default closeWaybill;