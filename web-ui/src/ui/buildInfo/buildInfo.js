import React from 'react';
import axios from '../../axios-messages';
import {connect} from 'react-redux';
import Paper from '@material-ui/core/Paper';
import * as messageActions from '../../store/MessageAction';

class buildInfo extends React.Component {

    state = {
        buildInfo: ''
    }

    componentDidMount() {
        axios.get('/api/info')
        .then( response => {
            this.setState({
                buildInfo: response.data
            });
        })
        .catch( error => {
            this.props.showErrorMessage( error.toString() );
        })
    }

    render() {
        return (
            <div> 
                <Paper style={{width: '400px', margin: 'auto', padding: '10px'}}>
                    {Object.keys(this.state.buildInfo).map( key => {
                        return <div style={{textAlign: 'left'}}>{key + '-> ' + this.state.buildInfo[key]}</div>
                    })}
                </Paper>
            </div>
        )
    }
};

const MapToStore = dispatch => {
    return {
        showMessage: (message) => dispatch({type: messageActions.SHOW_MESSAGE, message: message}),
        showErrorMessage: (message) => dispatch({type: messageActions.SHOW_ERROR_MESSAGE, message: message})
    }
};

export default connect(null, MapToStore)(buildInfo);