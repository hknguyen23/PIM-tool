import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import counterpart from 'counterpart';

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

function App() {
  return (
    <Router>
      <div className="root">
        <Header counterpart={counterpart} />

        <main className="content">
          <MainPage />
        </main>
      </div>
    </Router>
  );
}

export default App;