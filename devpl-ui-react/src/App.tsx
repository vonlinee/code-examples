import { useState } from 'react'
import './App.css'

import { MyMenu } from "./menu";

function App() {
  const [count, setCount] = useState(0)

  return (
    <MyMenu></MyMenu>
  )
}

export default App
