
import { useState } from "react";
import MonacoEditor from "react-monaco-editor";


export default function CodeEditor() {
  const [cssCode, setCssCode] = useState("");
  return (
    <MonacoEditor
      onChange={(e) => {
        console.log("onChange => ", e);
      }}
      language="css"
      height={400}
      theme="twilight"
      value={cssCode}
    />
  );
}
