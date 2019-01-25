import React, {Component} from 'react';
import {Route ,Switch} from 'react-router-dom';
import Radium from 'radium';
import './App.css';
import BackgroundVideo from './assets/background/background.mp4';

import UserHeader from './ui/User/Header/Header';
import Auth from './ui/auth/Auth';

import Profile from './ui/User/profile/UpdateUserConteiner';

import NavigationWrapper from './components/navigation/NavigationWrapper';
import UserConteiner from './ui/User/user/UserConteiner';
import UpdateUserConteiner from './ui/User/user/updateUser/UpdateUserConteiner';
import CreateUserConteiner from './ui/User/user/createUser/CreateUserConteiner';
import Message from './components/Message/Message';
import ErrorMessage from './components/Message/ErrorMessage';

import ProductHeader from './ui/testProduct/Header/Header';
import ProductConteiner from './ui/testProduct/product/ProductConteiner';
import ProductCreate from './ui/testProduct/product/details/CreateProductConteiner';
import ProductUpdate from './ui/testProduct/product/details/UpdateProductConteiner';

import TestTtnHeader from './ui/testTtn/Header/Header';
import TestTtnConteiner from './ui/testTtn/ttn/TtnConteiner';
import TestTtnUpdate from './ui/testTtn/ttn/ttnDetails/UpdateTtnConteiner';
import TestTtnCreate from './ui/testTtn/ttn/ttnDetails/CreateTtnConteiner';

import WaybillHeader from './ui/Waybill/Header/Header';
import WaybillConteiner from './ui/Waybill/waybill/WaybillConteiner';
import WaybillCreate from './ui/Waybill/waybill/waybillDetails/map/MapContainer';
import CloseWaybill from './ui/Waybill/close/CloseWaybill';

import TransportHeader from './ui/transport/Header/Header';
import TransportConteeiner from './ui/transport/transport/TransportConteiner';
import CreateTransportConteiner from './ui/transport/transport/details/CreateTransportConteiner';
import UpdateTransportConteiner from './ui/transport/transport/details/UpdateTransportConteiner';

import CompanyHeader from './ui/company/Header/Header';
import CompanyConteiner from './ui/company/content/CompanyConteiner';
import CompanyCreate from './ui/company/content/companyDetails/CreateCompanyConteiner';
import CompanyUpdate from './ui/company/content/companyDetails/UpdateCompanyConteiner';

import DeedHeader from './ui/deed/Header/Header';
import DeedConteiner from './ui/deed/deed/DeedConteiner';
import DeedUpdate from './ui/deed/deed/deedDetails/UpdateDeedConteiner';
import DeedCreate from './ui/deed/deed/deedDetails/CreateDeedConteiner';

import Analytics from './ui/analytics/analitycsConteiner';

import TestTable from './ui/testTable/testTable';

import BuildInfo from './ui/buildInfo/buildInfo';

class App extends Component {
  
  render() {

    let routs;
    if (localStorage.getItem('userRole') === 'ROLE_ADMIN') {
      routs = (
        <div>
          <Switch>
            <Route path='/profile' component={UserHeader} />
            
            <Route path='/company' component={CompanyHeader} />
            <Route path='/user' component={UserHeader} />
            <Route path='/product' component={ProductHeader} />
            <Route path='/ttn' component={TestTtnHeader} />
            <Route path='/waybill' component={WaybillHeader} />
            <Route path='/transport' component={TransportHeader} />
            <Route path='/deed' component={DeedHeader} />
            <Route path='/info' component={BuildInfo} />
          </Switch>

          <Switch>
            <Route path='/profile' exact component={Profile} />

            <Route path='/company' exact component={CompanyConteiner}/>
            <Route path='/company/update/:id' exact component={CompanyUpdate}/>
            <Route path='/company/create' exact component={CompanyCreate}/>

            <Route path='/user' exact component={UserConteiner}/>
            <Route path='/user/update/:id' exact component={UpdateUserConteiner}/>
            <Route path='/user/create' exact component={CreateUserConteiner}/>

            <Route path='/product' exact component={ProductConteiner} />
            <Route path='/product/create' exact component={ProductCreate} />
            <Route path='/product/update/:id' exact component={ProductUpdate} />

            <Route path='/ttn' exact component={TestTtnConteiner} />
            <Route path='/ttn/update/:id' exact component={TestTtnUpdate} />
            <Route path='/ttn/create' exact component={TestTtnCreate} />

            <Route path='/waybill' exact component={WaybillConteiner} />
            <Route path='/waybill/update/:id' exact component={WaybillCreate} />
            <Route path='/waybill/create' exact component={WaybillCreate} />
            <Route path='/waybill/close/:id' exact component={CloseWaybill} />

            <Route path='/transport' exact component={TransportConteeiner} />
            <Route path='/transport/create' exact component={CreateTransportConteiner} />
            <Route path='/transport/update/:id' exact component={UpdateTransportConteiner} />

            <Route path='/deed' exact component={DeedConteiner} />
            <Route path='/deed/create' exact component={DeedCreate} />
            <Route path='/deed/update/:id' exact component={DeedUpdate} />

            <Route path='/analytics' exect component={Analytics} />
            <Route path='/table' exact component={TestTable} />
          </Switch>
        </div>
      );
    }

    if(localStorage.getItem('userRole') === 'ROLE_DRIVER') {
      routs = (
        <div>
          <Switch>
            <Route path='/table' exact component={TestTable} />
            <Route path='/profile' exact component={UserHeader} />
            <Route path='/waybill' component={WaybillHeader} />
            <Route path='/deed' component={DeedHeader} />
            <Route path='/info' component={BuildInfo} />
          </Switch>
          <Switch>
            <Route path='/profile' exact component={Profile} />

            <Route path='/waybill' exact component={WaybillConteiner} />
            <Route path='/waybill/update/:id' exact component={WaybillCreate} />
            <Route path='/waybill/create' exact component={WaybillCreate} />

            <Route path='/deed' exact component={DeedConteiner} />
            <Route path='/deed/create' exact component={DeedCreate} />
            <Route path='/deed/update/:id' exact component={DeedUpdate} />
          </Switch>
        </div>
      )
    }

    return (
      <div className="App">
        {localStorage.getItem('token') ? 
          <div>
            <NavigationWrapper routs={routs} />
            <Message />
            <ErrorMessage />
          </div>
       :<div>
          <div className="fullscreen-bg">
            <video className='fullscreen-bg__video' autoPlay loop muted>
              <source src={BackgroundVideo} type='video/mp4' />
            </video>
          </div>
          {window.location.pathname.includes('info') ? <BuildInfo /> : <Auth />}
        </div>}
      </div>
    );
  }
}

export default Radium(App);
