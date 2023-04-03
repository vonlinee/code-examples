// require active-line.js
import 'codemirror/addon/selection/active-line.js'
// styleSelectedText
import 'codemirror/addon/selection/mark-selection.js'
// hint
import 'codemirror/addon/hint/show-hint.js'
import 'codemirror/addon/hint/sql-hint.js'
import 'codemirror/addon/hint/show-hint.css'
import 'codemirror/addon/hint/javascript-hint.js'
// highlightSelectionMatches
import 'codemirror/addon/scroll/annotatescrollbar.js'
import 'codemirror/addon/search/matchesonscrollbar.js'
import 'codemirror/addon/search/match-highlighter.js'

// 引入语言模式 可以从 codemirror/mode/ 下引入多个
import 'codemirror/mode/clike/clike.js'
import 'codemirror/mode/sql/sql.js'
import 'codemirror/mode/javascript/javascript.js';
import 'codemirror/mode/yaml/yaml.js';
import 'codemirror/mode/xml/xml.js';

// keyMap
import 'codemirror/addon/edit/matchbrackets.js'
import 'codemirror/addon/comment/comment.js'
import 'codemirror/addon/dialog/dialog.js'
import 'codemirror/addon/dialog/dialog.css'
import 'codemirror/addon/search/searchcursor.js'
import 'codemirror/addon/search/search.js'
import 'codemirror/keymap/sublime'; //sublime编辑器效果
// foldGutter 代码折叠
import 'codemirror/addon/fold/foldgutter.css'
import 'codemirror/addon/fold/brace-fold.js'
import 'codemirror/addon/fold/comment-fold.js'
import 'codemirror/addon/fold/foldcode.js'
import 'codemirror/addon/fold/foldgutter.js'
import 'codemirror/addon/fold/indent-fold.js'
import 'codemirror/addon/fold/markdown-fold.js'
import 'codemirror/addon/fold/xml-fold.js'
// 引入主题 可以从 codemirror/theme/ 下引入多个
import 'codemirror/theme/idea.css';
import 'codemirror/theme/eclipse.css';
import 'codemirror/theme/darcula.css';
import 'codemirror/theme/blackboard.css';
import 'codemirror/theme/3024-day.css';
import 'codemirror/lib/codemirror.css';
import 'codemirror/theme/monokai.css'
import 'codemirror/theme/material.css';
import 'codemirror/theme/base16-light.css'
//光标行背景高亮，配置里面也需要styleActiveLine设置为true
import 'codemirror/addon/selection/active-line';