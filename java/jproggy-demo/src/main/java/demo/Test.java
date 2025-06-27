package demo;

import org.jproggy.snippetory.Encodings;
import org.jproggy.snippetory.Repo;
import org.jproggy.snippetory.Syntaxes;
import org.jproggy.snippetory.Template;

import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) throws IOException {


        StringReader reader = new StringReader("""
                $platform is
                <ul>
                $traits{
                  <li>$trait</li>
                }$
                </ul>
                """);

        Template tpl = Repo.readReader(reader)
                .encoding(Encodings.html)
                .syntax(Syntaxes.FLUYT)
                .parse()
                .set("platform", "Snippetory");
        Stream.of("visionary", "open", "source").forEach(
                t -> tpl.get("traits").set("trait", t).render()
        );
        tpl.render(System.out);

    }
}
