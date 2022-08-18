/*
 *    Copyright 2006-2022 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试生成XML
 */
class XmlCodeGenerationTest {

    private static final Log logger = LogFactory.getLog(XmlCodeGenerationTest.class);

    @ParameterizedTest
    @MethodSource("xmlFileGenerator")
    void testXmlParse(GeneratedXmlFile generatedXmlFile) {
        ByteArrayInputStream is = new ByteArrayInputStream(
                generatedXmlFile.getFormattedContent().getBytes());

        try {
            FileUtils.copyInputStreamToFile(is, new File("/1.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            factory.setValidating(true);
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            builder.setEntityResolver(new TestEntityResolver());
//            builder.setErrorHandler(new TestErrorHandler());
//            builder.parse(is);
//        } catch (Exception e) {
//            fail("Generated XML File " + generatedXmlFile.getFileName() + " will not parse");
//        }
    }

    static List<GeneratedXmlFile> xmlFileGenerator() throws Exception {
        return new ArrayList<>(generateXmlFilesMybatis());
    }

    static List<GeneratedXmlFile> generateXmlFilesMybatis() throws Exception {
        JavaCodeGenerationTest.createDatabase();
        return generateXmlFiles("/scripts/generatorConfig-local.xml");
    }

    static List<GeneratedXmlFile> generateXmlFiles(String configFile) throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(JavaCodeGenerationTest.class.getResourceAsStream(configFile));
        List<Context> contexts = config.getContexts();
        List<Context> list = new ArrayList<>();
        list.add(contexts.get(0));
        config.setContexts(list);
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
        myBatisGenerator.generate(null, null, null, false);
        return myBatisGenerator.getGeneratedXmlFiles();
    }

    static class TestEntityResolver implements EntityResolver {

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            // just return an empty string.  this should stop the parser from trying to access the network
            return new InputSource(new ByteArrayInputStream("".getBytes()));
        }
    }

    static class TestErrorHandler implements ErrorHandler {

        private final List<String> errors = new ArrayList<>();
        private final List<String> warnings = new ArrayList<>();

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            warnings.add(exception.getMessage());
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            errors.add(exception.getMessage());
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            errors.add(exception.getMessage());
        }

        public List<String> getErrors() {
            return errors;
        }

        public List<String> getWarnings() {
            return warnings;
        }
    }
}
