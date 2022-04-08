package org.setamv.shardingsphere.sample.dynamic.spel;

import lombok.Builder;
import lombok.Data;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring EL表达式使用Starter
 *
 * @author setamv
 * @date 2021-04-19
 */
public class SpringExpressionStarter {

    @Builder
    @Data
    static class Person {
        private String name;
        private String alias;
        private Float height;
        private Map<String, String> hobbies;
    }

    @Builder
    @Data
    static class Family {
        private String name;
        private Person papa;
        private Person mama;
        private Collection<Person> children;
    }

    private static Family buildFamily() {
        Map<String, String> papaHobbies = new HashMap<>(2, 1.0F);
        papaHobbies.put("fitness", "to be strong.");
        papaHobbies.put("movie", "wating movie");

        Map<String, String> mamaHobbies = new HashMap<>(2, 1.0F);
        mamaHobbies.put("video", "watching video");
        mamaHobbies.put("makeup", "to be beautiful");

        Map<String, String> childHobbies = new HashMap<>();
        childHobbies.put("play", "play games");
        childHobbies.put("cartoon", "watch cartoon");

        return Family.builder()
                .name("susie's family")
                .papa(Person.builder().name("will").alias("vivi").height(174.5f).hobbies(papaHobbies).build())
                .mama(Person.builder().name("susie").alias("xiuxiu").height(168.0f).hobbies(mamaHobbies).build())
                .children(Arrays.asList(
                        Person.builder().name("candy").alias("xinxin").height(1.3f).hobbies(childHobbies).build(),
                        Person.builder().name("andy").alias("anan").height(1.0f).hobbies(childHobbies).build()
                ))
                .build();
    }

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'hello world'");
        String str = (String) exp.getValue();
        System.out.println(str);

        Family family = buildFamily();
        exp = parser.parseExpression("name");
        System.out.println(exp.getValue(family, String.class));

        exp = parser.parseExpression("getName()");
        System.out.println(exp.getValue(family, String.class));

        exp = parser.parseExpression("papa.getAlias()");
        System.out.println(exp.getValue(family, String.class));


        exp = parser.parseExpression("papa.height + 1");
        System.out.println(exp.getValue(family, String.class));

        exp = parser.parseExpression("papa.hobbies['fitness']");
        System.out.println(exp.getValue(family, String.class));

        exp = parser.parseExpression("children[1].hobbies['cartoon']");
        System.out.println(exp.getValue(family, String.class));

        exp.setValue(family, "like watch cartoon");
        System.out.println(exp.getValue(family, String.class));

//        Object[] methodArgs = new Object[2];
//        methodArgs[0] = family.getPapa();
//        methodArgs[1] = family.getChildren().iterator().next();
//        DynamicRouteEvaluationContext rootObject = new DynamicRouteEvaluationContext(null, null, methodArgs, family, family.getClass());
//        exp = parser.parseExpression("#root.args[0].height");
//        System.out.println(exp.getValue(rootObject, String.class));

    }
}
