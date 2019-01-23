import React, {Component} from 'react';
import axios from '../../../../../axios-messages';
import './mapIndox.css';

import {Map, InfoWindow, Marker, GoogleApiWrapper, Polyline} from 'google-maps-react';

import Test from './test';

export class MapContainer extends Component {
  
  state = {
    triangleCoords: [
    ],
    currentMapCenter: {
      coords: {
        lat: 25.774,
        lng: -80.190,
      },
      title: 'Title'
    },
    popIsShow: true,
  }

  componentDidMount() {
    if (window.location.pathname.includes('update')) {
      let waybillId = this.props.match.params.id;
      axios.get('/api/waybills/' + waybillId)
      .then( response => {
        this.setState({
          ...this.state,
          triangleCoords: response.data.checkPoints,
          waybillId: response.data.id,
        })
      })
    }
  }

  updateMapCenter (lat, lng) {
    if (lat && lng) {
      this.setState({
        ...this.state,
        currentMapCenter: {
          lat: lat,
          lng: lng,
        }
      })
      console.log(this.state.currentMapCenter)
    }
  }


  handleClick = (c, map) => {

    if (c.lat() && c.lng() && this.checkRole()) {
      let isPresent = this.state.triangleCoords.find(
        coord => 
        coord.lat.toFixed(3) === c.lat().toFixed(3) 
        && coord.lng.toFixed(3) === c.lng().toFixed(3));

      if (!isPresent) {
        let triangleCoords = this.state.triangleCoords;
        triangleCoords.push({title:'mark ' + (triangleCoords.length + 1), lat: c.lat(), lng: c.lng()})
        
        this.setState({
          ...this.state,
          triangleCoords: triangleCoords,
        })
        console.log(this.state.triangleCoords)
      }
    }
  }

  checkRole() {
    let userRole = localStorage.getItem('userRole');
    return userRole === 'ROLE_MANAGER' || userRole === 'ROLE_ADMIN';
  }

  handleMarkDeleteButton = index => {
    if (this.checkRole()) {
      let triangleCoords = this.state.triangleCoords;
      triangleCoords.splice(index, 1);
      this.setState({
        ...this.state,
        triangleCoords: triangleCoords,
      })
    }
  }

  handleMarkListClicked = index => {
      let triangleCoords = this.state.triangleCoords;
      triangleCoords[index].checkPointStatus = triangleCoords[index].checkPointStatus === 'PASSED' ? 'NOT_PASSED' : 'PASSED';
      this.setState({
        ...this.state,
        triangleCoords: triangleCoords,
      })
  }

  render() {
    const style = {
      width: '100%',
      height: '100%'
    }

    return (
      <Map 
        google={this.props.google}
        style={style}
        className={'map'}
        zoom={5}
        initialCenter={{
          lat: 25.774,
          lng: -80.190
        }}
        onClick={(t, map, c) => this.handleClick(c.latLng, map)}
      >
      {this.state.triangleCoords.map((coords) => 
        <Marker 
          position={coords}
        >
        
          <InfoWindow><span>InfoWindow</span></InfoWindow>
        </Marker>
      )}
        <div >
          <Test
            marks={this.state.triangleCoords}
            waybillId={this.state.waybillId}
            handleMarkListClicked={index => this.handleMarkListClicked(index)}
            handleMarkDeleteButton={index => this.handleMarkDeleteButton(index)}
          />
        </div>
      </Map>
    );
  }
};
  
  export default GoogleApiWrapper({
    apiKey: ''
  })(MapContainer)