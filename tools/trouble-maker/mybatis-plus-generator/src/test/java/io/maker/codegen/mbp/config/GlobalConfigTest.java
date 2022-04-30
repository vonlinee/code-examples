package io.maker.codegen.mbp.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.maker.codegen.mbp.config.GlobalConfig;
import io.maker.codegen.mbp.config.builder.GeneratorBuilder;
import io.maker.codegen.mbp.config.rules.DateType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author nieqiurong 2020/10/12.
 */
public class GlobalConfigTest {

    private void buildAssert(GlobalConfig globalConfig) {
        Assertions.assertTrue(globalConfig.isKotlin());
        Assertions.assertTrue(globalConfig.isSwagger());
        Assertions.assertTrue(globalConfig.isOpen());
        Assertions.assertEquals(globalConfig.getAuthor(), "mp");
        Assertions.assertEquals(globalConfig.getOutputDir(), "/temp/code");
        Assertions.assertEquals(globalConfig.getDateType(), DateType.SQL_PACK);
    }

    @Test
    void builderTest() {
        GlobalConfig globalConfig = GeneratorBuilder.globalConfigBuilder().author("mp").enableKotlin()
            .enableSwagger().dateType(DateType.SQL_PACK).outputDir("/temp/code").build();
        buildAssert(globalConfig);
    }

    @Test
    void commentDateTest() {
        String defaultDate = GeneratorBuilder.globalConfig().getCommentDate();
        String commentDate = GeneratorBuilder.globalConfigBuilder().commentDate("yyyy-MM-dd").build().getCommentDate();
        Assertions.assertEquals(defaultDate, commentDate);
        Assertions.assertEquals("2200年11月10日", GeneratorBuilder.globalConfigBuilder().commentDate(() -> "2200年11月10日").build().getCommentDate());
        Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ISO_DATE), GeneratorBuilder.globalConfigBuilder().commentDate(() -> LocalDate.now().format(DateTimeFormatter.ISO_DATE)).build().getCommentDate());
    }
}
