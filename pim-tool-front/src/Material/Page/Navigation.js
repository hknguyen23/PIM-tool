import React from 'react';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  NavLink
} from 'react-router-dom';
import Translate from 'react-translate-component';
import {
  Drawer,
  List,
  ListItem,
} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import SearchProject from './SearchProject/SearchProject';
import ProjectManagement from './ProjectManagement';
import ErrorScreen from './ErrorScreen';
import '../Style/Navigation.css';

const drawerWidth = 350;

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
    margin: '100px'
  },
  // necessary for content to be below app bar
  drawerPaper: {
    width: drawerWidth,
    marginTop: '98px'
  },
  listItem: {
    padding: 0,
    fontFamily: 'Segoe UI',
  },
  subItem: {
    fontSize: '14pt',
    color: '#666666'
  },
  selectedSubItem: {
    fontSize: '14pt',
    fontWeight: 500,
    color: '#333333'
  }
}));

const routes = [
  {
    path: "/",
    exact: true,
    main: () => <SearchProject />
  }, {
    path: "/projects/new",
    main: () => <ProjectManagement mode="new" />
  }, {
    path: "/projects/update/:id",
    main: () => <ProjectManagement mode="update" />
  }, {
    path: "/error",
    main: () => <ErrorScreen />
  }
];

export default function Navigation() {
  const classes = useStyles();

  const drawer = (
    <div style={{ marginLeft: '150px' }}>
      <List>
        <ListItem className={classes.listItem} component={NavLink} to="/">
          <p className="text-semi-bold first-element">
            <Translate content="navigation.title" />
          </p>
        </ListItem>

        <ListItem className={classes.listItem}>
          <p className="text-semi-bold">
            <Translate content="navigation.new" />
          </p>
        </ListItem>

        <ListItem className={classes.listItem} component={NavLink} to="/projects/new">
          <p className={classes.selectedSubItem}>
            <Translate content="navigation.project" />
          </p>
        </ListItem>

        <ListItem className={classes.listItem}>
          <p className={classes.subItem}>
            <Translate content="navigation.customer" />
          </p>
        </ListItem>

        <ListItem className={classes.listItem}>
          <p className={classes.subItem}>
            <Translate content="navigation.supplier" />
          </p>
        </ListItem>
      </List>
    </div>
  );

  return (
    <div>
      <Router>
        <Drawer
          classes={{
            paper: classes.drawerPaper,
          }}
          variant="permanent"
          open
        >
          {drawer}
        </Drawer>
        <main>
          <Switch>
            {routes.map((route, index) => {
              return (
                <Route
                  key={index}
                  exact={route.exact}
                  path={route.path}
                  children={<route.main />}
                />
              );
            })}
          </Switch>
        </main>
      </Router>
    </div>
  );
}
