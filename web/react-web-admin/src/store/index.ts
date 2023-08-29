import {
  combineReducers,
  applyMiddleware,
  compose
} from 'redux'
import { persistStore, persistReducer } from 'redux-persist'

import { configureStore } from '@reduxjs/toolkit'

import storage from 'redux-persist/lib/storage'
import UserReducer from './user/reducer'
import RouteReducer from './route/reducer'
import NotPersistenceReducer from './notPersistence/reducer'
import ConfigReducer from './config/reducer'
import thunk from 'redux-thunk'
const reducer = combineReducers({
  user: UserReducer,
  route: RouteReducer,
  notPersistence: NotPersistenceReducer,
  config: ConfigReducer
})
const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['user', 'route', 'config'] // 白名单
}

// 初始化redux: https://blog.csdn.net/qq_28745941/article/details/126086446
const myReducer = persistReducer(persistConfig, reducer)

const store = configureStore({
  reducer: myReducer,
  // Disabling the non-serializable data warning https://github.com/reduxjs/redux-toolkit/issues/870
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false
    })
})

const persistor = persistStore(store)
export { store, persistor }
