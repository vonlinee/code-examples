package org.fxmisc.richtext.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.reactfx.util.Tuple2;
import static org.reactfx.util.Tuples.*;

/**
 * This class serves as the entry point for launching the various demos.
 * It is primarily used when creating a FAT jar (or executable jar) for the
 * demos.
 */
public class DemoLauncher {

    @SuppressWarnings("serial")
	private static final Map<String, Tuple2<String, Consumer<String[]>>> demoMap = new HashMap<>() {{
	    /**
	     * When launched from the JVM launcher directly, all the Demo classes would not necessarily require a 
	     * main() method - the JVM launcher would take care of it. However, since we use our own launcher
	     * class here, the main() method in each individual class *is* required - simply calling the
	     * launch() method does NOT work!
	     */
	    put("JavaKeywordsDemo",       t("A CodeArea with Java syntax highlighting that is computed on the JavaFX Application Thread", 
	                                    JavaKeywordsDemo::main));
        put("JavaKeywordsAsyncDemo",  t("A CodeArea with Java syntax highlighting that is computed on a background thread", 
                                        JavaKeywordsAsyncDemo::main));
        put("XMLEditorDemo",          t("An area with XML syntax highlighting", 
                                        XMLEditorDemo::main));
        put("ManualHighlightingDemo", t("Manually highlight various parts of the text in an area via buttons", 
                                        ManualHighlightingDemo::main));
        put("RichTextDemo",           t("An area showing a large number of RichTextFX's features: inlined images, rich text (e.g. text alignment and background colors, etc.), and save/load capabilities",
                                        org.fxmisc.richtext.demo.richtext.RichTextDemo::main));
        put("PopupDemo",              t("A popup that follows the caret and selection when they move",
                                        PopupDemo::main));
        put("TooltipDemo",            t("Tells you the letter over which the mouse is hovering", 
                                        TooltipDemo::main));
        put("HyperlinkAreaDemo",      t("An area with hyperlinks that open to their corresponding link", 
                                        org.fxmisc.richtext.demo.hyperlink.HyperlinkDemo::main));
        put("LineIndicatorDemo",      t("Line numbers appear to left of each paragraph and a triangle appears on the same paragraph as the caret", 
                                        org.fxmisc.richtext.demo.lineindicator.LineIndicatorDemo::main));
        put("CloneDemo",              t("Two areas that can modify and show the same underlying document", 
                                        CloneDemo::main));
        put("FontSizeSwitcherDemo",   t("Change the font size of the entire area.", 
                                        FontSizeSwitcherDemo::main));
        put("MultiCaretAndSelectionNameDemo", t("Add and display multiple carets and selections with different style classes in the same area", 
                                        MultiCaretAndSelectionDemo::main));
        put("OverrideBehaviorDemo",   t("Overrides the area's default behavior and demonstrates some things of which to be aware", 
                                        OverrideBehaviorDemo::main));
        put("ShowLineDemo",           t("Force a specific part of the underlying document to be rendered to the screen.", 
                                        ShowLineDemo::main));
        put("SpellCheckingDemo",      t("Shows how to add a red squiggle underneath misspelled words", 
                                        SpellCheckingDemo::main));
        put("BracketHighlighterDemo", t("Shows how to highlight matching brackets", 
                                        org.fxmisc.richtext.demo.brackethighlighter.BracketHighlighterDemo::main));
	}};

    private final static String[] noArgs = new String[0];

    public static void main(String[] args) {
        if (args.length == 0) {
            demoMap.entrySet().forEach(e -> System.out.printf("%s - %s\n", e.getKey(), e.getValue().get1()));
        } else {
            final Tuple2<String, Consumer<String[]>> demoData = demoMap.get(args[0]);
        	if (demoData == null) {
        		System.err.printf("No such demo: %s", args[0]);
        	} else {
        	    Consumer<String[]> mainMethod = demoData.get2();
        		mainMethod.accept(noArgs);
        	}
        }
    }
}
