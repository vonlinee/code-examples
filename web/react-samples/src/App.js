
import React, { useState } from 'react';
import './App.css';

function Card({ children }) {
  let {num, setNum} = useState(0)

  return (
    <div className="card">
      {children}
      <button >+</button>
    </div>
  );
}

class MyComponent extends React.Component {

  constructor(props) {
    super(props)
  }

  render() {
    console.log(this);
    return <h1>11111</h1>
  }
}

function Button(props) {
  console.log(props);
  return <button onClick={props.onClick}>{props.text}</button>
}

function alertMsg() {
  alert("hello world")
}

let obj = {
  "name": "zs",
  "age": 26
}

console.log(obj.name);

function App() {
  return (
    <div className="App">
      <MyComponent></MyComponent>
    </div>
  );
}

export default App;
