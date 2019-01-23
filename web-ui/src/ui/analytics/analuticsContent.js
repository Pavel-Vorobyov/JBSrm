import React from 'react';

import './analytics.css';
import { Paper, Grid, Button } from '@material-ui/core';

const analyticsContent = props => {
    
    return (
        <div className='analytics-content'>
            <Grid
                container 
                justify='center' 
                alignItems='center'
             >
             
                <Grid
                    item
                    xs={12}
                >
                    <Paper className='total'>
                        a
                    </Paper>
                </Grid>

                <div style={{width: '100%', marginTop: '20px'}}>
                    <Paper className='graphic'>
                        a
                    </Paper>

                    <div style={{width: '40px', display: 'inline-block'}}>

                    </div>
                    <Paper className='graphic'>
                        a
                    </Paper>
                </div>

            </Grid>
        </div>
    )
};

export default analyticsContent;