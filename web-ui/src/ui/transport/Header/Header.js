import React from 'react';

import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import Grid from '@material-ui/core/Grid';
import IconButton from '@material-ui/core/IconButton';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import AccountCircle from '@material-ui/icons/AccountCircle';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';

import './Header.css';

const lightColor = 'rgba(255, 255, 255, 0.7)';

const styles = theme => ({
  secondaryBar: {
    zIndex: 0,
    height: '80px'
  },
  menuButton: {
    marginLeft: -theme.spacing.unit,
  },
  iconButtonAvatar: {
    padding: 4,
  },
  link: {
    textDecoration: 'none',
    color: lightColor,
    '&:hover': {
      color: theme.palette.common.white,
    },
  },
  button: {
    borderColor: lightColor,
  },
});

class Header extends React.Component {

  state = {
    anchorEl: null,
  }

  handleCloseMenu() {
    this.setState({
      ...this.state,
      anchorEl: null
    })
  }

  handleMenu = event => {
    this.setState({ anchorEl: event.currentTarget });
  };

  handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('userRole');
      window.location.reload();
  }

  handleProfile() {
    this.props.history.push('profile/');
  }

  render() {
    const { classes, onDrawerToggle } = this.props;
    const open = Boolean(this.state.anchorEl);

    return (
      <React.Fragment>
        <AppBar
          component="div"
          className={classes.secondaryBar}
          color="primary"
          position="static"
          elevation={0}
        >
        <Toolbar>
          <Grid container alignItems="center" spacing={8}>
            <Grid item xs>
              <Typography className='Header' color="inherit" variant="h5" style={{display: 'inline-block', width: '93%'}}>
                Transport Management
              </Typography>
              {true && (
              <div style={{display: 'inline-flex'}}>
                <IconButton
                  aria-owns={open ? 'menu-appbar' : undefined}
                  aria-haspopup="true"
                  onClick={this.handleMenu}
                  color="inherit"
                >
                  <AccountCircle />
                </IconButton>
                <Menu
                  id="menu-appbar"
                  anchorEl={this.state.anchorEl}
                  anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                  }}
                  transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                  }}
                  open={open}
                  onClose={this.handleClose}
                >
                  <MenuItem onClick={() => this.handleProfile()}>Profile</MenuItem>
                  <MenuItem onClick={this.handleLogout}>LogOut</MenuItem>
                </Menu>
              </div>
            )}
            </Grid>
          </Grid>
        </Toolbar>
        </AppBar>
      </React.Fragment>
    );
  }
}

  

Header.propTypes = {
  classes: PropTypes.object.isRequired,
  onDrawerToggle: PropTypes.func.isRequired,
};

export default withStyles(styles)(Header);