import React, { forwardRef, useImperativeHandle, useRef } from 'react';
import { Button } from 'antd';
import DataTable from './components/datatable';
import ResultSetTable from './components/ResultSetTable';


import { ReactComponent as Menu } from './icons/svg/menu.svg'

console.log(Menu);

interface IRefProps {
  childMethod: () => void;
}

type Props = {};

const Child = forwardRef<IRefProps, Props>((props, ref) => {

  useImperativeHandle(ref, () => ({
    childMethod
  }), [])

  const childMethod = () => {
    console.log('cCom data')
  }

  return (
    <div>子组件</div>
  )
})

const Parent = () => {

  const childRef = useRef<IRefProps>(null);

  const handleClick = () => {
    if (childRef.current) {
      childRef.current?.childMethod()
    }
  }

  return (<div>
    <button onClick={handleClick}>触发子组件的hello方法</button>
    <Child ref={childRef}></Child>
  </div>)
}

const App: React.FC = () => (
  <div className="App">

    <Menu width={22}></Menu>
    <ResultSetTable></ResultSetTable>
  </div>
);

export default App;
