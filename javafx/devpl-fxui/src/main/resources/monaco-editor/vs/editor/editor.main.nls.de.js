/*!-----------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Version: 0.8.2(undefined)
 * Released under the MIT license
 * https://github.com/Microsoft/vscode/blob/master/LICENSE.txt
 *-----------------------------------------------------------*/

define("vs/editor/editor.main.nls.de", {
	"vs/base/browser/ui/actionbar/actionbar": [
		"{0} ({1})",
	],
	"vs/base/browser/ui/aria/aria": [
		"{0} (erneut aufgetreten)",
	],
	"vs/base/browser/ui/findinput/findInput": [
		"Eingabe",
	],
	"vs/base/browser/ui/findinput/findInputCheckboxes": [
		"Groß-/Kleinschreibung beachten",
		"Nur ganzes Wort suchen",
		"Regulären Ausdruck verwenden",
	],
	"vs/base/browser/ui/inputbox/inputBox": [
		"Fehler: {0}",
		"Warnung: {0}",
		"Info: {0}",
	],
	"vs/base/common/json": [
		"Ungültiges Symbol",
		"Ungültiges Zahlenformat.",
		"Ein Eigenschaftenname wurde erwartet.",
		"Ein Wert wurde erwartet.",
		"Ein Doppelpunkt wurde erwartet.",
		"Ein Komma wurde erwartet.",
		"Eine schließende geschweifte Klammer wurde erwartet.",
		"Eine schließende Klammer wurde erwartet.",
		"Das Dateiende wurde erwartet.",
	],
	"vs/base/common/severity": [
		"Fehler",
		"Warnung",
		"Info",
	],
	"vs/base/parts/quickopen/browser/quickOpenModel": [
		"{0}, Auswahl",
		"Auswahl",
	],
	"vs/base/parts/quickopen/browser/quickOpenWidget": [
		"Schnellauswahl. Nehmen Sie eine Eingabe vor, um die Ergebnisse einzugrenzen.",
		"Schnellauswahl",
	],
	"vs/base/parts/tree/browser/treeDefaults": [
		"Zuklappen",
	],
	"vs/editor/common/config/commonEditorConfig": [
		"Editor",
		"Steuert die Schriftfamilie.",
		"Steuert die Schriftbreite.",
		"Steuert den Schriftgrad in Pixeln.",
		"Steuert die Zeilenhöhe. Verwenden Sie 0, um LineHeight aus der FontSize-Angabe zu berechnen.",
		"Steuert die Anzeige von Zeilennummern. Mögliche Werte sind \"Ein\", \"Aus\" und \"Relativ\". \"Relativ\" zeigt die Zeilenanzahl ab der aktuellen Cursorposition.",
		"Spalten, an denen vertikale Lineale angezeigt werden sollen",
		"Zeichen, die als Worttrennzeichen verwendet werden, wenn wortbezogene Navigationen oder Vorgänge ausgeführt werden.",
		"Die Anzahl der Leerzeichen, denen ein Tabstopp entspricht. Diese Einstellung wird basierend auf dem Inhalt der Datei überschrieben, wenn \"editor.detectIndentation\" aktiviert ist.",
		"\"number\" wurde erwartet. Beachten Sie, dass der Wert \"auto\" durch die Einstellung \"editor.detectIndentation\" ersetzt wurde.",
		"Fügt beim Drücken der TAB-TASTE Leerzeichen ein. Diese Einstellung wird basierend auf dem Inhalt der Datei überschrieben, wenn \"editor.detectIndentation\" aktiviert ist.",
		"\"boolean\" wurde erwartet. Beachten Sie, dass der Wert \"auto\" durch die Einstellung \"editor.detectIndentation\" ersetzt wurde.",
		"Beim Öffnen einer Datei werden \"editor.tabSize\" und \"editor.insertSpaces\" basierend auf den Dateiinhalten erkannt.",
		"Steuert, ob die Auswahl runde Ecken aufweist.",
		"Legt fest, ob der Editor Bildläufe über die letzte Zeile hinaus ausführt.",
		"Steuert, ob die Minikarte angezeigt wird",
		"Die tatsächlichen Zeichen in einer Zeile rendern (im Gegensatz zu Farbblöcken)",
		"Breite der Minikarte beschränken, um höchstens eine bestimmte Anzahl von Spalten zu rendern",
		"Zeilenumbrüche erfolgen nie.",
		"Der Zeilenumbruch erfolgt an der Breite des Anzeigebereichs.",
		"Der Zeilenbereich erfolgt bei \"editor.wordWrapColumn\".",
		"Der Zeilenumbruch erfolgt beim Mindestanzeigebereich und \"editor.wordWrapColumn\".",
		"Steuert den Zeilenumbruch. Mögliche Einstellungen sind:\n - \"off\" (Umbruch deaktivieren),\n - \"on\" (Anzeigebereichsumbruch),\n - \"wordWrapColumn\" (Umbruch bei \"editor.wordWrapColumn\") oder \n - \"bounded\" (der Zeilenumbruch erfolgt beim Mindestanzeigebereich und \"editor.wordWrapColumn\").",
		"Steuert die Umbruchspalte des Editors, wenn für \"editor.wordWrap\" die Option \"wordWrapColumn\" oder \"bounded\" festgelegt ist.",
		"Steuert den Einzug der umbrochenen Zeilen. Der Wert kann \"none\", \"same\" oder \"indent\" sein.",
		"Ein Multiplikator, der für die Mausrad-Bildlaufereignisse \"deltaX\" und \"deltaY\" verwendet werden soll.",
		"Schnellvorschläge innerhalb von Zeichenfolgen aktivieren.",
		"Schnellvorschläge innerhalb von Kommentaren aktivieren.",
		"Schnellvorschläge außerhalb von Zeichenfolgen und Kommentaren aktivieren.",
		"Steuert, ob Vorschläge während der Eingabe automatisch angezeigt werden sollen.",
		"Steuert die Verzögerung in ms für die Anzeige der Schnellvorschläge.",
		"Aktiviert Parameterhinweise.",
		"Steuert, ob der Editor Klammern automatisch nach dem Öffnen schließt.",
		"Steuert, ob der Editor Zeilen automatisch nach der Eingabe formatiert.",
		"Steuert, ob der Editor den eingefügten Inhalt automatisch formatiert.",
		"Steuert, ob Vorschläge automatisch bei der Eingabe von Triggerzeichen angezeigt werden.",
		"Steuert, ob Vorschläge über die Eingabetaste (zusätzlich zur TAB-Taste) angenommen werden sollen. Vermeidet Mehrdeutigkeit zwischen dem Einfügen neuer Zeilen oder dem Annehmen von Vorschlägen.",
		"Steuert, ob Vorschläge über Commitzeichen angenommen werden sollen. In JavaScript kann ein Semikolon (\";\") beispielsweise ein Commitzeichen sein, das einen Vorschlag annimmt und dieses Zeichen eingibt.",
		"Steuert, ob Codeausschnitte mit anderen Vorschlägen angezeigt und wie diese sortiert werden.",
		"Steuert, ob ein Kopiervorgang ohne Auswahl die aktuelle Zeile kopiert.",
		"Steuert, ob Vervollständigungen auf Grundlage der Wörter im Dokument berechnet werden sollen.",
		"Schriftgröße für Vorschlagswidget",
		"Zeilenhöhe für Vorschlagswidget",
		"Steuert, ob der Editor der Auswahl ähnelnde Übereinstimmungen hervorheben soll.",
		"Steuert, ob der Editor das Vorkommen semantischer Symbole markieren soll.",
		"Steuert die Anzahl von Dekorationen, die an derselben Position im Übersichtslineal angezeigt werden.",
		"Steuert, ob um das Übersichtslineal ein Rahmen gezeichnet werden soll.",
		"Steuert den Cursoranimationsstil. Gültige Werte sind \"blink\", \"smooth\", \"phase\", \"expand\" und \"solid\".",
		"Schriftart des Editors vergrößern, wenn das Mausrad verwendet und die STRG-TASTE gedrückt wird",
		"Steuert den Cursorstil. Gültige Werte sind  \"block\", \"block-outline\", \"line\", \"line-thin\", \"underline\" und \"underline-thin\".",
		"Aktiviert Schriftartligaturen.",
		"Steuert die Sichtbarkeit des Cursors im Übersichtslineal.",
		"Steuert, wie der Editor Leerzeichen rendert. Mögliche Optionen: \"none\", \"boundary\" und \"all\". Die Option \"boundary\" rendert keine einzelnen Leerzeichen zwischen Wörtern.",
		"Steuert, ob der Editor Steuerzeichen rendern soll.",
		"Steuert, ob der Editor Einzugsführungslinien rendern soll.",
		"Steuert, wie der Editor die aktuelle Zeilenhervorhebung rendern soll. Mögliche Werte sind \"none\", \"gutter\", \"line\" und \"all\".",
		"Steuert, ob der Editor CodeLenses anzeigt.",
		"Steuert, ob für den Editor Codefaltung aktiviert ist.",
		"Übereinstimmende Klammern hervorheben, wenn eine davon ausgewählt wird.",
		"Steuert, ob der Editor den vertikalen Glyphenrand rendert. Der Glyphenrand wird hauptsächlich zum Debuggen verwendet.",
		"Das Einfügen und Löschen von Leerzeichen folgt auf Tabstopps.",
		"Nachfolgendes automatisch eingefügtes Leerzeichen entfernen",
		"Peek-Editoren geöffnet lassen, auch wenn auf ihren Inhalt doppelgeklickt oder die ESC-TASTE gedrückt wird.",
		"Steuert, ob der Editor das Verschieben einer Auswahl per Drag and Drop zulässt.",
		"Steuert, ob der Diff-Editor das Diff nebeneinander oder inline anzeigt.",
		"Steuert, ob der Diff-Editor Änderungen in führenden oder nachgestellten Leerzeichen als Diffs anzeigt.",
		"Steuert, ob der Diff-Editor die Indikatoren \"+\" und \"-\" für hinzugefügte/entfernte Änderungen anzeigt.",
		"Steuert, ob die primäre Linux-Zwischenablage unterstützt werden soll.",
	],
	"vs/editor/common/config/defaultConfig": [
		"Editor-Inhalt",
	],
	"vs/editor/common/controller/cursor": [
		"Unerwartete Ausnahme beim Ausführen des Befehls.",
	],
	"vs/editor/common/model/textModelWithTokens": [
		"Fehler des Modus bei der Tokenumwandlung der Eingabe.",
	],
	"vs/editor/common/modes/modesRegistry": [
		"Nur-Text",
	],
	"vs/editor/common/services/bulkEdit": [
		"Die folgenden Dateien wurden in der Zwischenzeit geändert: {0}",
		"Made no edits",
		"Made {0} text edits in {1} files",
		"Made {0} text edits in one file",
	],
	"vs/editor/common/services/modeServiceImpl": [
		"Contributes-Sprachdeklarationen",
		"Die ID der Sprache.",
		"Namealiase für die Sprache.",
		"Dateierweiterungen, die der Sprache zugeordnet sind.",
		"Dateinamen, die der Sprache zugeordnet sind.",
		"Dateinamen-Globmuster, die Sprache zugeordnet sind.",
		"MIME-Typen, die der Sprache zugeordnet sind.",
		"Ein regulärer Ausdruck, der mit der ersten Zeile einer Datei der Sprache übereinstimmt.",
		"Ein relativer Pfad zu einer Datei mit Konfigurationsoptionen für die Sprache.",
	],
	"vs/editor/common/services/modelServiceImpl": [
		"[{0}]\n{1}",
		"[{0}] {1}",
	],
	"vs/editor/common/view/editorColorRegistry": [
		"Hintergrundfarbe zur Hervorhebung der Zeile an der Cursorposition.",
		"Hintergrundfarbe für den Rahmen um die Zeile an der Cursorposition.",
		"Hintergrundfarbe hervorgehobener Bereiche (beispielsweise durch Features wie Quick Open und Suche).",
		"Farbe des Cursors im Editor.",
		"Farbe der Leerzeichen im Editor.",
		"Farbe der Führungslinien für Einzüge im Editor.",
		"Zeilennummernfarbe im Editor.",
	],
	"vs/editor/contrib/bracketMatching/common/bracketMatching": [
		"Gehe zu Klammer",
	],
	"vs/editor/contrib/caretOperations/common/caretOperations": [
		"Caretzeichen nach links verschieben",
		"Caretzeichen nach rechts verschieben",
	],
	"vs/editor/contrib/caretOperations/common/transpose": [
		"Buchstaben austauschen",
	],
	"vs/editor/contrib/clipboard/browser/clipboard": [
		"Ausschneiden",
		"Kopieren",
		"Einfügen",
		"Mit Syntaxhervorhebung kopieren",
	],
	"vs/editor/contrib/comment/common/comment": [
		"Zeilenkommentar umschalten",
		"Zeilenkommentar hinzufügen",
		"Zeilenkommentar entfernen",
		"Blockkommentar umschalten",
	],
	"vs/editor/contrib/contextmenu/browser/contextmenu": [
		"Editor-Kontextmenü anzeigen",
	],
	"vs/editor/contrib/find/browser/findWidget": [
		"Suchen",
		"Suchen",
		"Vorherige Übereinstimmung",
		"Nächste Übereinstimmung",
		"In Auswahl suchen",
		"Schließen",
		"Ersetzen",
		"Ersetzen",
		"Ersetzen",
		"Alle ersetzen",
		"Ersetzen-Modus wechseln",
		"Nur die ersten 999 Ergebnisse werden hervorgehoben, alle Suchvorgänge beziehen sich aber auf den gesamten Text.",
		"{0} von {1}",
		"Keine Ergebnisse",
	],
	"vs/editor/contrib/find/common/findController": [
		"Suchen",
		"Nächstes Element suchen",
		"Vorheriges Element suchen",
		"Nächste Auswahl suchen",
		"Vorherige Auswahl suchen",
		"Auswahl zur nächsten Übereinstimmungssuche hinzufügen",
		"Letzte Auswahl zu vorheriger Übereinstimmungssuche hinzufügen",
		"Letzte Auswahl in nächste Übereinstimmungssuche verschieben",
		"Letzte Auswahl in vorherige Übereinstimmungssuche verschieben",
		"Alle Vorkommen auswählen und Übereinstimmung suchen",
		"Alle Vorkommen ändern",
	],
	"vs/editor/contrib/folding/browser/folding": [
		"Auffalten",
		"Faltung rekursiv aufheben",
		"Falten",
		"Rekursiv falten",
		"Alle falten",
		"Alle auffalten",
		"Faltebene {0}",
	],
	"vs/editor/contrib/format/browser/formatActions": [
		"1 Formatierung in Zeile {0} vorgenommen",
		"{0} Formatierungen in Zeile {1} vorgenommen",
		"1 Formatierung zwischen Zeilen {0} und {1} vorgenommen",
		"{0} Formatierungen zwischen Zeilen {1} und {2} vorgenommen",
		"Dokument formatieren",
		"Auswahl formatieren",
	],
	"vs/editor/contrib/goToDeclaration/browser/goToDeclaration": [
		"Keine Definition gefunden für \"{0}\".",
		"Keine Definition gefunden",
		" – {0} Definitionen",
		"Gehe zu Definition",
		"Definition an der Seite öffnen",
		"Peek-Definition",
		"Keine Implementierung gefunden für \"{0}\"",
		"Keine Implementierung gefunden",
		" – {0} implementations",
		"Zur Implementierung wechseln",
		"Vorschau der Implementierung anzeigen",
		"Keine Typendefinition gefunden für \"{0}\"",
		"Keine Typendefinition gefunden",
		" – {0} type definitions",
		"Zur Typdefinition wechseln",
		"Vorschau der Typdefinition anzeigen",
		"Klicken Sie, um {0} Definitionen anzuzeigen.",
	],
	"vs/editor/contrib/gotoError/browser/gotoError": [
		"({0}/{1})",
		"Gehe zum nächsten Fehler oder zur nächsten Warnung",
		"Gehe zum vorherigen Fehler oder zur vorherigen Warnung",
		"Editormarkierung: Farbe bei Fehler des Navigationswidgets.",
		"Editormarkierung: Farbe bei Warnung des Navigationswidgets.",
		"Editormarkierung: Hintergrund des Navigationswidgets.",
	],
	"vs/editor/contrib/hover/browser/hover": [
		"Hovern anzeigen",
		"Hervorhebung eines Worts, unter dem ein Mauszeiger angezeigt wird.",
		"Hintergrundfarbe des Editor-Mauszeigers.",
		"Rahmenfarbe des Editor-Mauszeigers.",
	],
	"vs/editor/contrib/hover/browser/modesContentHover": [
		"Wird geladen ...",
	],
	"vs/editor/contrib/inPlaceReplace/common/inPlaceReplace": [
		"Durch vorherigen Wert ersetzen",
		"Durch nächsten Wert ersetzen",
	],
	"vs/editor/contrib/inspectTokens/browser/inspectTokens": [
		"Developer: Inspect Tokens",
	],
	"vs/editor/contrib/linesOperations/common/linesOperations": [
		"Zeile nach oben kopieren",
		"Zeile nach unten kopieren",
		"Zeile nach oben verschieben",
		"Zeile nach unten verschieben",
		"Zeilen aufsteigend sortieren",
		"Zeilen absteigend sortieren",
		"Nachgestelltes Leerzeichen kürzen",
		"Zeile löschen",
		"Zeileneinzug",
		"Zeile ausrücken",
		"Alle übrigen löschen",
		"Alle rechts löschen",
		"Zeilen verknüpfen",
		"Zeichen um den Cursor herum transponieren",
		"In Großbuchstaben umwandeln",
		"In Kleinbuchstaben umwandeln",
	],
	"vs/editor/contrib/links/browser/links": [
		"BEFEHLSTASTE + Mausklick zum Aufrufen des Links",
		"STRG + Mausklick zum Aufrufen des Links",
		"Fehler beim Öffnen dieses Links, weil er nicht wohlgeformt ist: {0}",
		"Fehler beim Öffnen dieses Links, weil das Ziel fehlt.",
		"Link öffnen",
	],
	"vs/editor/contrib/multicursor/common/multicursor": [
		"Cursor oberhalb hinzufügen",
		"Cursor unterhalb hinzufügen",
		"Mehrere Cursor aus ausgewählten Zeilen erstellen",
	],
	"vs/editor/contrib/parameterHints/browser/parameterHints": [
		"Parameterhinweise auslösen",
	],
	"vs/editor/contrib/parameterHints/browser/parameterHintsWidget": [
		"{0}, Hinweis",
	],
	"vs/editor/contrib/quickFix/browser/quickFixCommands": [
		"Show Fixes ({0})",
		"Show Fixes",
		"Quick Fix",
	],
	"vs/editor/contrib/quickOpen/browser/gotoLine": [
		"Gehe zur Zeile {0} und Spalte {1}",
		"Gehe zu Zeile {0}",
		"Zeilennummer zwischen 1 und {0} eingeben, zu der navigiert werden soll",
		"Geben Sie eine Spaltennummer zwischen 1 und {0} ein, zu der navigiert werden soll",
		"Go to line {0}",
		"Geben Sie eine Zeilennummer ein, gefolgt von einem optionalen Doppelpunkt und einer Spaltennummer, zu der Sie navigieren möchten",
		"Gehe zu Zeile...",
	],
	"vs/editor/contrib/quickOpen/browser/quickCommand": [
		"{0}, commands",
		"Name der zu ausführenden Aktion eingeben",
		"Befehlspalette",
	],
	"vs/editor/contrib/quickOpen/browser/quickOutline": [
		"{0}, symbols",
		"Name des Bezeichners eingeben, zu dem navigiert werden soll",
		"Gehe zu Symbol...",
		"Symbole ({0})",
		"Module ({0})",
		"Klassen ({0})",
		"Schnittstellen ({0})",
		"Methoden ({0})",
		"Funktionen ({0})",
		"Eigenschaften ({0})",
		"Variablen ({0})",
		"Variablen ({0})",
		"Konstruktoren ({0})",
		"Aufrufe ({0})",
	],
	"vs/editor/contrib/referenceSearch/browser/referenceSearch": [
		" – {0} Verweise",
		"Alle Verweise suchen",
	],
	"vs/editor/contrib/referenceSearch/browser/referencesController": [
		"Wird geladen...",
	],
	"vs/editor/contrib/referenceSearch/browser/referencesWidget": [
		"Fehler beim Auflösen der Datei.",
		"{0} Verweise",
		"{0} Verweis",
		"1 reference in {0}",
		"{0} references in {1}",
		"reference in {0} on line {1} at column {2}",
		"Keine Vorschau verfügbar.",
		"Verweise",
		"Keine Ergebnisse",
		"Found {0} references",
		"Verweise",
		"Hintergrundfarbe des Titelbereichs der Peek-Ansicht.",
		"Farbe des Titels in der Peek-Ansicht.",
		"Farbe der Titelinformationen in der Peek-Ansicht.",
		"Farbe der Peek-Ansichtsränder und des Pfeils.",
		"Hintergrundfarbe der Ergebnisliste in der Peek-Ansicht.",
		"Vordergrund für Übereinstimmungseinträge in der Ergebnisliste der Peek-Ansicht.",
		"Vordergrund für Dateieinträge in der Ergebnisliste der Peek-Ansicht.",
		"Hintergrundfarbe des ausgewählten Eintrags in der Ergebnisliste der Peek-Ansicht.",
		"Vordergrundfarbe des ausgewählten Eintrags in der Ergebnisliste der Peek-Ansicht.",
		"Hintergrundfarbe des Peek-Editors.",
		"Farbe für Übereinstimmungsmarkierungen in der Ergebnisliste der Peek-Ansicht.",
		"Farbe für Übereinstimmungsmarkierungen im Peek-Editor.",
	],
	"vs/editor/contrib/rename/browser/rename": [
		"No result.",
		"Successfully renamed \'{0}\' to \'{1}\'. Summary: {2}",
		"Fehler bei der Ausführung der Umbenennung.",
		"Symbol umbenennen",
	],
	"vs/editor/contrib/rename/browser/renameInputField": [
		"Benennen Sie die Eingabe um. Geben Sie einen neuen Namen ein, und drücken Sie die EINGABETASTE, um den Commit auszuführen.",
	],
	"vs/editor/contrib/smartSelect/common/smartSelect": [
		"Auswahl erweitern",
		"Auswahl verkleinern",
	],
	"vs/editor/contrib/suggest/browser/suggestController": [
		"Accepting \'{0}\' did insert the following text: {1}",
		"Vorschlag auslösen",
	],
	"vs/editor/contrib/suggest/browser/suggestWidget": [
		"Background color of the suggest widget.",
		"Border color of the suggest widget.",
		"Color of the match highlight in the suggest widget.",
		"Mehr anzeigen...{0}",
		"{0}, Vorschlag, hat Details",
		"{0}, Vorschlag",
		"Zurück",
		"Wird geladen...",
		"Keine Vorschläge.",
		"{0}, angenommen",
		"{0}, Vorschlag, hat Details",
		"{0}, Vorschlag",
	],
	"vs/editor/contrib/toggleTabFocusMode/common/toggleTabFocusMode": [
		"TAB-Umschalttaste verschiebt Fokus",
	],
	"vs/editor/contrib/wordHighlighter/common/wordHighlighter": [
		"Hintergrundfarbe eines Symbols beim Lesezugriff (beispielsweise beim Lesen einer Variablen).",
		"Hintergrundfarbe eines Symbols beim Schreibzugriff (beispielsweise beim Schreiben in eine Variable).",
	],
	"vs/editor/contrib/zoneWidget/browser/peekViewWidget": [
		"Schließen",
	],
	"vs/platform/configuration/common/configurationRegistry": [
		"Standard-Konfiguration überschreibt",
		"Zu überschreibende Einstellungen für Sprache {0} konfigurieren.",
		"Zu überschreibende Editor-Einstellungen für eine Sprache konfigurieren.",
		"Trägt Konfigurationseigenschaften bei.",
		"Eine Zusammenfassung der Einstellungen. Diese Bezeichnung wird in der Einstellungsdatei als trennender Kommentar verwendet.",
		"Die Beschreibung der Konfigurationseigenschaften.",
		"\"{0}\" kann nicht registriert werden. Die Eigenschaft stimmt mit dem Eigenschaftsmuster \'\\[.*\\]$\' zum Beschreiben sprachspezifischer Editor-Einstellungen überein. Verwenden Sie den Beitrag \"configurationDefaults\".",
		"\"{0}\" kann nicht registriert werden. Diese Eigenschaft ist bereits registriert.",
		"\"configuration.properties\" muss ein Objekt sein.",
		"Wenn eine Festlegung erfolgt, muss \"configuration.type\" auf \"object\" festgelegt werden.",
		"configuration.title muss eine Zeichenfolge sein.",
		"Trägt zu Konfigurationeinstellungen des Standard-Editors für die jeweilige Sprache bei.",
	],
	"vs/platform/extensions/common/extensionsRegistry": [
		"Gibt für VS Code-Erweiterungen die VS Code-Version an, mit der die Erweiterung kompatibel ist. Darf nicht \"*\" sein. Beispiel: ^0.10.5 gibt die Kompatibilität mit mindestens VS Code-Version 0.10.5 an.",
		"Der Herausgeber der VS Code-Extension.",
		"Der Anzeigename für die Extension, der im VS Code-Katalog verwendet wird.",
		"Die vom VS Code-Katalog zum Kategorisieren der Extension verwendeten Kategorien.",
		"Das in VS Code Marketplace verwendete Banner.",
		"Die Bannerfarbe für die Kopfzeile der VS Code Marketplace-Seite.",
		"Das Farbdesign für die Schriftart, die im Banner verwendet wird.",
		"Alle Beiträge der VS Code-Extension, die durch dieses Paket dargestellt werden.",
		"Legt die Erweiterung fest, die im Marketplace als Vorschau gekennzeichnet werden soll.",
		"Aktivierungsereignisse für die VS Code-Extension.",
		"Array aus Badges, die im Marketplace in der Seitenleiste auf der Seite mit den Erweiterungen angezeigt werden.",
		"Die Bild-URL für den Badge.",
		"Der Link für den Badge.",
		"Eine Beschreibung für den Badge.",
		"Abhängigkeiten von anderen Erweiterungen. Der Bezeichner einer Erweiterung ist immer ${publisher}.${name}, beispielsweise \"vscode.csharp\".",
		"Ein Skript, das ausgeführt wird, bevor das Paket als VS Code-Extension veröffentlicht wird.",
		"Der Pfad zu einem 128x128-Pixel-Symbol.",
	],
	"vs/platform/keybinding/common/abstractKeybindingService": [
		"({0}) wurde gedrückt. Es wird auf die zweite Taste der Kombination gewartet...",
		"Die Tastenkombination ({0}, {1}) ist kein Befehl.",
	],
	"vs/platform/keybinding/common/keybindingLabels": [
		"STRG",
		"UMSCHALTTASTE",
		"ALT",
		"Windows",
		"STRG",
		"UMSCHALTTASTE",
		"ALT",
		"Befehlstaste",
		"STRG",
		"UMSCHALTTASTE",
		"ALT",
		"Windows",
	],
	"vs/platform/message/common/message": [
		"Schließen",
		"Später",
		"Abbrechen",
	],
	"vs/platform/theme/common/colorRegistry": [
		"Ungültiges Farbformat. Verwenden Sie #RRGGBB oder #RRGGBBAA.",
		"In der Workbench verwendete Farben.",
		"Allgemeine Vordergrundfarbe. Diese Farbe wird nur verwendet, wenn sie nicht durch eine Komponente überschrieben wird.",
		"Allgemeine Kontur-/Rahmenfarbe für fokussierte Elemente. Diese Farbe wird nur verwendet, wenn sie nicht durch eine Komponente überschrieben wird.",
		"Rahmenfarbe zum Trennen von Komponenten, wenn ein Design mit hohem Kontrast aktiviert wurde.",
		"Konturfarbe für aktive Komponenten, wenn ein Design mit hohem Kontrast aktiviert wurde.",
		"Hintergrund für Eingabefeld.",
		"Vordergrund für Eingabefeld.",
		"Rahmen für Eingabefeld.",
		"Rahmenfarbe für aktivierte Optionen in Eingabefeldern.",
		"Hintergrund für Dropdown.",
		"Vordergrund für Dropdown.",
		"Rahmen für Dropdown.",
		"List/Tree focus background when active.",
		"List/Tree focus background when inactive.",
		"List/Tree selection background when active.",
		"List/Tree selection foreground when active.",
		"List/Tree focus and selection background.",
		"List/Tree focus and selection foreground.",
		"List/Tree selection background when inactive.",
		"List/Tree hover background.",
		"List/Tree drag and drop background.",
		"List/Tree focus outline color when active.",
		"List/Tree focus outline color when inactive.",
		"List/Tree selection outline color.",
		"List/Tree hover outline color.",
		"Hintergrundfarbe des Editors.",
		"Standardvordergrundfarbe des Editors.",
		"Farbe der Editor-Auswahl.",
		"Farbe der Auswahl in einem inaktiven Editor.",
		"Farbe für Bereiche, deren Inhalt der Auswahl entspricht.",
		"Farbe des aktuellen Suchergebnisses.",
		"Farbe der anderen Suchtreffer.",
		"Farbe des Bereichs zur Einschränkung der Suche.",
		"Farbe der aktiven Links.",
		"Farbe der Links.",
		"Background color of editor widgets, such as find/replace.",
		"Shadow color of editor widgets such as find/replace.",
	]
});