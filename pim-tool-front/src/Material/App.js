import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import counterpart from 'counterpart';
import { makeStyles } from '@material-ui/core/styles';

import './App.css';
import Header from './Page/Header';
import MainPage from './Page/MainPage';

import en from './lang/en';
import vn from './lang/vn';
import fr from './lang/fr';

const locales = [
  { name: 'en', data: en },
  { name: 'vn', data: vn },
  { name: 'fr', data: fr }
];

locales.forEach(locale => counterpart.registerTranslations(locale.name, locale.data));

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
    margin: '100px'
  },
  content: {
    flexGrow: 1,
    marginTop: '10px',
    marginLeft: '270px',
  },
}));

function App() {
  const classes = useStyles();

  return (
    <Router>
      <div className={classes.root}>
        <Header counterpart={counterpart} />

        <main className={classes.content}>
          <MainPage />
        </main>
      </div>
    </Router>
  );
}

export default App;