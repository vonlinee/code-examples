
import { useState } from 'react';
import './App.css';

function Card({ children }) {
  let {num, setNum} = useState(0)

  return (
    <div className="card">
      {children}

      <button onClick={() => setNum(1)}>+</button>
    </div>
  );
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
      <Card>
        <Button text='111' onClick={alertMsg}></Button>
      </Card>
    </div>
  );
}

export default App;
