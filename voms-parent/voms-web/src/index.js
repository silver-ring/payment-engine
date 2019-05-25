import React from 'react';
import ReactDOM from 'react-dom';

import registerServiceWorker from './registerServiceWorker';
import {BrowserRouter} from "react-router-dom";
import IndexRouter from "./router/IndexRouter";

ReactDOM.render(<BrowserRouter><IndexRouter/></BrowserRouter>, document.getElementById('app'));

registerServiceWorker();
