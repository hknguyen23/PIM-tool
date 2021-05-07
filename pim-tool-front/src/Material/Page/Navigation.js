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
import SearchProject from './SearchProject/SearchProject';
import ProjectManagement from './ProjectManagement';
import ErrorScreen from './ErrorScreen';
import '../Style/Navigation.css';

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
  const drawer = (
    <div style={{ marginLeft: '150px' }}>
      <List>
        <ListItem className="listItem" component={NavLink} to="/">
          <p className="text-semi-bold first-element">
            <Translate content="navigation.title" />
          </p>
        </ListItem>

        <ListItem className="listItem">
          <p className="text-semi-bold">
            <Translate content="navigation.new" />
          </p>
        </ListItem>

        <ListItem className="listItem" component={NavLink} to="/projects/new">
          <p className="selectedSubItem">
            <Translate content="navigation.project" />
          </p>
        </ListItem>

        <ListItem className="listItem">
          <p className="subItem">
            <Translate content="navigation.customer" />
          </p>
        </ListItem>

        <ListItem className="listItem">
          <p className="subItem">
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
            paper: "drawerPaper",
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
