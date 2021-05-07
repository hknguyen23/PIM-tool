import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import Translate from 'react-translate-component';
import '../Style/ErrorScreen.css';
import errorImage from '../Images/error.png'
import { Typography } from '@material-ui/core';

export default function ErrorScreen() {
  const location = useLocation();

  return (
    <React.Fragment>
      <div className="container">
        <img src={errorImage} alt="error" className="error" />
        <div className="text-container">
          <div>
            <Typography>
              <Translate content="errorScreen.unexpected" />
            </Typography>
          </div>
          <div>
            <Typography>
              {location.state === undefined ? "" : location.state.errorMessage.toString()}
            </Typography>
          </div>
          <Typography>
            <Translate content="errorScreen.please" />
            &nbsp;
            <Translate className="red-text" content="errorScreen.contact" />
          </Typography>
          <p></p>
          <div>
            <Typography>
              <Translate content="errorScreen.or" />
              &nbsp;
              <Link to="/">
                <Translate content="errorScreen.back" />
              </Link>
            </Typography>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
}
