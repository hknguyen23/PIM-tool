import React, { useState } from 'react';
// import { Link } from 'react-router-dom';
import { Button, Link } from '@material-ui/core';
import Translate from 'react-translate-component';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';

import '../Style/Header.css';
import logo from '../Images/logo_elca.png';
import en from '../lang/en';
import vn from '../lang/vn';
import fr from '../lang/fr';

const locales = [
  { name: 'en', data: en },
  { name: 'vn', data: vn },
  { name: 'fr', data: fr }
];

const useStyles = makeStyles((theme) => ({
  grow: {
    flexGrow: 1,
  },
  header: {
    backgroundColor: 'white',
    padding: '10px 100px 10px 100px'
  },
  button: {
    padding: 0,
    borderRadius: '8px',
    lineHeight: 'normal',
    fontWeight: 'normal',
    textTransform: 'none'
  },
}));

export default function Header({ counterpart }) {
  const classes = useStyles();
  const [language, setLanguage] = useState({ name: 'en', data: en });

  const handleChangeLocale = (locale) => {
    setLanguage(locale);
    counterpart.setLocale(locale.name);
  }

  return (
    <div>
      <AppBar className={classes.header}>
        <Toolbar>

          <Link href='/' style={{ cursor: 'pointer' }}>
            <div className="logo">
              <img src={logo} alt="logo" />
            </div>
          </Link>

          <Link href='/' style={{ cursor: 'pointer', textDecoration: 'none' }}>
            <div className="name">
              <Translate content="header.name" />
            </div>
          </Link>

          <div className={classes.grow} />

          <div className="language">
            {locales.map((locale, i) => {
              return (
                <React.Fragment key={i}>
                  <Link style={{ cursor: 'pointer' }} onClick={() => handleChangeLocale(locale)}>
                    <div style={{ fontWeight: 'bold', color: locale.name === language.name ? '#666666' : '#2f85fa' }}>
                      {locale.name.toUpperCase()}
                    </div>
                  </Link>
                  <div style={{ color: '#111111' }}>
                    &nbsp;{i === locales.length - 1 ? '' : '|'}&nbsp;
                  </div>
                </React.Fragment>
              );
            })}
          </div>

          <Button className={classes.button}>
            <Translate className="help" content="header.help" />
          </Button>

          <Button className={classes.button}>
            <Translate className="logging" content="header.login" />
          </Button>
        </Toolbar>
      </AppBar>
    </div >
  );
}