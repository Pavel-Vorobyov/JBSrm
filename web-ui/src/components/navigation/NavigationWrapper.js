import React, {Component} from 'react';

import PropTypes from 'prop-types';
import { MuiThemeProvider, createMuiTheme, withStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Hidden from '@material-ui/core/Hidden';

import PeopleIcon from '@material-ui/icons/People';
import DnsRoundedIcon from '@material-ui/icons/DnsRounded';
import PermMediaOutlinedIcon from '@material-ui/icons/PhotoSizeSelectActual';
import TimerIcon from '@material-ui/icons/Timer';
import SettingsIcon from '@material-ui/icons/Settings';
import PhonelinkSetupIcon from '@material-ui/icons/PhonelinkSetup';
import DescriptionIcon from '@material-ui/icons/Description';
import ExtensionIcon from '@material-ui/icons/Extension';

import Navigator from './Navigation';
import Background from '../../assets/background.jpg';

const drawerWidth = 230;

let theme = createMuiTheme({
  typography: {
    useNextVariants: true,
    h5: {
      fontWeight: 500,
      fontSize: 20,
      letterSpacing: 0.5,
    },
    fontSize: 27,
  },
  palette: {
    primary: {
      light: '#63ccff',
      main: '#009be5',
      dark: '#006db3',
    },
  },
  shape: {
    borderRadius: 8,
  },
});

theme = {
  ...theme,
  overrides: {
    MuiDrawer: {
      paper: {
        backgroundColor: '#18202c',
      },
    },
    MuiButton: {
      label: {
        textTransform: 'initial',
      },
      contained: {
        boxShadow: 'none',
        '&:active': {
          boxShadow: 'none',
        },
      },
    },
    MuiTabs: {
      root: {
        marginLeft: theme.spacing.unit,
      },
      indicator: {
        height: 3,
        borderTopLeftRadius: 3,
        borderTopRightRadius: 3,
        backgroundColor: theme.palette.common.white,
      },
    },
    MuiTab: {
      root: {
        textTransform: 'initial',
        margin: '0 16px',
        minWidth: 0,
        [theme.breakpoints.up('md')]: {
          minWidth: 0,
        },
      },
      labelContainer: {
        padding: 0,
        [theme.breakpoints.up('md')]: {
          padding: 0,
        },
      },
    },
    MuiIconButton: {
      root: {
        padding: theme.spacing.unit,
      },
    },
    MuiTooltip: {
      tooltip: {
        borderRadius: 4,
      },
    },
    MuiDivider: {
      root: {
        backgroundColor: '#404854',
      },
    },
    MuiListItemText: {
      primary: {
        fontWeight: theme.typography.fontWeightMedium,
      },
    },
    MuiListItemIcon: {
      root: {
        color: 'inherit',
        marginRight: 0,
        '& svg': {
          fontSize: 25,
        },
      },
    },
    MuiAvatar: {
      root: {
        width: 32,
        height: 32,
      },
    },
  },
  props: {
    MuiTab: {
      disableRipple: true,
    },
  },
  mixins: {
    ...theme.mixins,
    toolbar: {
      minHeight: 48,
    },
  },
};

const styles = () => ({
  root: {
    display: 'flex',
    minHeight: '100vh',
  },
  drawer: {
    [theme.breakpoints.up('sm')]: {
      width: drawerWidth,
      flexShrink: 0,
    },
  },
  appContent: {
    flex: 1,
    display: 'flex',
    flexDirection: 'column',
    backgroundImage: "url(" + Background + ")",
    backgroundAttachment: 'fixed',
  },
  mainContent: {
    flex: 1,
    padding: '48px 36px 0',
    background: '#eaeff1',
  },
});

class NavigationWrapper extends Component {
  adminCategories = [
        {
          id: 'Develop',
          children: [
            { id: 'Company', icon: <DnsRoundedIcon />, to: '/company', active: true},
            { id: 'User', icon: <PeopleIcon />, to: '/user' },
            { id: 'Ttn', icon: <DescriptionIcon />, to: '/ttn' },
            { id: 'Transport', icon: <PermMediaOutlinedIcon />, to: '/transport' },
            { id: 'Waybill', icon: <PermMediaOutlinedIcon />, to: '/waybill' },
            // { id: 'Deed', icon: <PermMediaOutlinedIcon />, to: '/deed' }
          ],
        },
        {
          id: 'Store',
          children: [
            {id: 'Product details', icon: <ExtensionIcon />, to: '/product'}
          ]
        },
        // {
        //   id: 'Quality',
        //   children: [
        //     { id: 'Analytics', icon: <SettingsIcon />, to: '/analytics' },
        //   ],
        // },
      ];
    driverCategories = [
      {
        id: 'Driver',
        children: [
          { id: 'Waybill', icon: <PermMediaOutlinedIcon />, to: '/waybill', active: true  },
          // { id: 'Deed', icon: <PermMediaOutlinedIcon />, to: '/deed'}
        ],
      },
    ];
    componentWillMount() {
      if (localStorage.getItem('userRole') === 'ROLE_ADMIN') {
        this.setState({
          ...this.state,
          categories: this.adminCategories,
        })
      };
      if (localStorage.getItem('userRole') === 'ROLE_DRIVER') {
        this.setState({
          ...this.state,
          categories: this.driverCategories,
        })
      }
      
    }
    state = {
        mobileOpen: false,
        currentCategoryId: 'User',
        currentheaderTab: '',
        categories: [
          {
            id: 'Develop',
            children: [
              { id: 'Company', icon: <DnsRoundedIcon />, to: '/company', active: true},
              { id: 'User', icon: <PeopleIcon />, to: '/user' },
              { id: 'Ttn', icon: <DescriptionIcon />, to: '/ttn' },
              { id: 'Transport', icon: <PermMediaOutlinedIcon />, to: '/transport' },
              { id: 'Waybill', icon: <PermMediaOutlinedIcon />, to: '/waybill' },
              // { id: 'Deed', icon: <PermMediaOutlinedIcon />, to: '/deed' }
            ],
          },
          {
            id: 'Store',
            children: [
              {id: 'Product details', icon: <ExtensionIcon />, to: '/product'}
            ]
          },
          // {
          //   id: 'Quality',
          //   children: [
          //     { id: 'Analytics', icon: <SettingsIcon />, to: '/analytics' },
          //   ],
          // },
        ]
      };

      handleCategoriesClicked = (categoryId, childId) => {
        let categories;

        if (localStorage.getItem('userRole') === 'ROLE_ADMIN') {
          categories = [
            {
              id: 'Develop',
              children: [
                { id: 'Company', icon: <DnsRoundedIcon />, to: '/company'},
                { id: 'User', icon: <PeopleIcon />, to: '/user' },
                { id: 'Ttn', icon: <DescriptionIcon />, to: '/ttn' },
                { id: 'Transport', icon: <PermMediaOutlinedIcon />, to: '/transport' },
                { id: 'Waybill', icon: <PermMediaOutlinedIcon />, to: '/waybill' },
                // { id: 'Deed', icon: <PermMediaOutlinedIcon />, to: '/deed' }
              ],
            },
            {
              id: 'Store',
              children: [
                {id: 'Product details', icon: <ExtensionIcon />, to: '/product'}
              ]
            },
            // {
            //   id: 'Quality',
            //   children: [
            //     { id: 'Analytics', icon: <SettingsIcon />, to: '/analytics' },
            //   ],
            // },
          ];
        };
        if (localStorage.getItem('userRole') === 'ROLE_DRIVER') {
          categories = [
            {
              id: 'Driver',
              children: [
                { id: 'Waybill', icon: <PermMediaOutlinedIcon />, to: '/waybill' },
                // { id: 'Deed', icon: <PermMediaOutlinedIcon />, to: '/deed' }
              ],
            },
          ];
        }
    
        categories.find(c => c.id === categoryId)
          .children.find(ch=> ch.id === childId)
          .active = true;
    
          this.setState({
            ...this.state,
            categories: categories,
            currentCategoryId: childId
          })


      };

      handleDrawerToggle = () => {
        this.setState(state => ({ mobileOpen: !state.mobileOpen }));
      };
    
      handleUpdateHeaderTab (tab) {
        this.setState({
          ...this.state,
          currentheaderTab: tab
        })
      }

      render() {
      const { classes } = this.props;

          return (
                <div>
                <MuiThemeProvider theme={theme}>
                <div className={classes.root}>
                    <CssBaseline />
                    <nav className={classes.drawer}>
                    <Hidden smUp implementation="js">
                        <Navigator
                          PaperProps={{ style: { width: drawerWidth } }}
                          variant="temporary"
                          open={this.state.mobileOpen}
                          onClose={this.handleDrawerToggle}
                        />
                    </Hidden>
                    <Hidden xsDown implementation="css">
                        <Navigator 
                          handleCategoriesClicked={this.handleCategoriesClicked}
                          PaperProps={{ style: { width: drawerWidth }}}
                          categories={this.state.categories}/>
                    </Hidden>
                    </nav>
                    <div className={classes.appContent}>
                    {this.props.routs}    {/* Main content */}
                    </div>
                </div>
                </MuiThemeProvider>
            </div>
          )
      }
};

export default withStyles(styles)(NavigationWrapper);