import { applyMiddleware, createStore } from "redux";
import thunk from "redux-thunk";
import reducer from "./reducers";

// import { configureStore } from "@reduxjs/toolkit";

const store = createStore(reducer, applyMiddleware(thunk))

export default store;
