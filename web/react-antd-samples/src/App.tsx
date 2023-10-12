import React, { forwardRef, useImperativeHandle, useRef } from 'react';
import { Button } from 'antd';

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
    <Button type="primary">Button</Button>
    <Parent></Parent>
  </div>
);

export default App;
