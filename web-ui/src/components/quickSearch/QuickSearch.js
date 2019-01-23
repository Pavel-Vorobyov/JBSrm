import React, {Component} from 'react';
import axios from '../../axios-messages';

import TextField from '@material-ui/core/TextField';

import './QuickSearch.css';
import SearchResult from './searchResult/searchResult';

class QuickSearch extends Component {

    state = {
        searchResult: [],
        rsultShouldShow: false,
    }

    handleOnKeyUp(event) {
        let searchPrefix = this.props.searchPrefix ? this.props.searchPrefix + ' ' : '';

        switch (event.key) {
            case "Escape": 
                this.setState({...this.state, rsultShouldShow:false});
                break;
            case "Enter":
                break;
            default:
                if (event.target.value) {
                    axios.get(this.props.searchQuery + searchPrefix + event.target.value)
                    .then(response => {
                        this.setState({
                            ...this.state,
                            searchResult: response.data,
                            rsultShouldShow: true,
                        })
                    });
                } else {
                    this.setState({...this.state, rsultShouldShow:false})
                }
        }
    }

    render() {
        return (
            <div className='SearchBar' style={{fontSize:'18px'}}>
               <TextField
                    fullWidth
                    placeholder="Search by email address, phone number, or user UID"
                    InputProps={{
                    disableUnderline: true,
                    }}
                    onKeyUp={(event) => this.handleOnKeyUp(event)}
                    onBlur={this.props.onBlur}
                />
                <div className='SearchResult'  >
                    <SearchResult 
                        handleDetailsClick={this.props.handleClick}
                        content={this.state.searchResult} 
                        shouldShow={this.state.rsultShouldShow} 
                        objectMappingResult={this.props.objectMappingResult}/>
                </div>
            </div>
        )
    };
};

export default QuickSearch;