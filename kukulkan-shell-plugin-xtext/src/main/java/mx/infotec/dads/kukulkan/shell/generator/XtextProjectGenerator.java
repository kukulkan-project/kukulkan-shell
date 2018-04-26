/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package mx.infotec.dads.kukulkan.shell.generator;

import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.infotec.dads.kukulkan.metamodel.annotation.GeneratorComponent;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.generator.Generator;
import mx.infotec.dads.kukulkan.shell.services.WriterHelper;

/**
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
@GeneratorComponent
public class XtextProjectGenerator implements Generator {

    @Autowired
    private WriterHelper writer;

    private static final Logger LOGGER = LoggerFactory.getLogger(XtextProjectGenerator.class);

    private static final String DIRECTORY = "archetypes/dsl-project/";

    @Override
    public String getName() {
        return "xtext-grammar-generator";
    }

    @Override
    public void process(GeneratorContext context) {
        XtextProjectContext dslContext = requiredNotEmpty(context.get(XtextProjectContext.class));
        Map<String, Object> model = new HashMap<>();
        model.put("project", dslContext);

        LOGGER.info("Generating {} DSL project", dslContext.getName());

        // Writing XText project
        writer.copyTemplate(DIRECTORY + "package.name.parent/build.gradle.ftl",
                "${project.basePackage}.${project.name}.parent/build.gradle", model);
        writer.copyTemplate(DIRECTORY + "package.name.parent/settings.gradle.ftl",
                "${project.basePackage}.${project.name}.parent/settings.gradle", model);
        writer.copy(DIRECTORY + "package.name.parent/gradlew", "${project.basePackage}.${project.name}.parent/gradlew",
                model);
        writer.copy(DIRECTORY + "package.name.parent/gradlew.bat",
                "${project.basePackage}.${project.name}.parent/gradlew.bat", model);

        writer.copy(DIRECTORY + "package.name.parent/gradle/wrapper/gradle-wrapper.properties",
                "${project.basePackage}.${project.name}.parent/gradle/wrapper/gradle-wrapper.properties", model);
        writer.copy(DIRECTORY + "package.name.parent/gradle/maven-deployment.gradle",
                "${project.basePackage}.${project.name}.parent/gradle/maven-deployment.gradle", model);
        writer.copy(DIRECTORY + "package.name.parent/gradle/source-layout.gradle",
                "${project.basePackage}.${project.name}.parent/gradle/source-layout.gradle", model);
        writer.copy(DIRECTORY + "package.name.parent/gradle/wrapper/gradle-wrapper.jar",
                "${project.basePackage}.${project.name}.parent/gradle/wrapper/gradle-wrapper.jar", model);
        writer.copyTemplate(
                DIRECTORY + "package.name.parent/package.name/src/main/java/qualified/GenerateName.mwe2.ftl",
                "${project.basePackage}.${project.name}.parent/${project.basePackage}.${project.name}/src/main/java/${project.basePackage?replace(\".\", \"/\")}/${project.name}/Generate${project.name?cap_first}.mwe2",
                model);
        writer.copyTemplate(DIRECTORY + "package.name.parent/package.name/src/main/java/qualified/Name.xtext.ftl",
                "${project.basePackage}.${project.name}.parent/${project.basePackage}.${project.name}/src/main/java/${project.basePackage?replace(\".\", \"/\")}/${project.name}/${project.name?cap_first}.xtext",
                model);
        writer.copyTemplate(DIRECTORY + "package.name.parent/package.name/build.gradle.ftl",
                "${project.basePackage}.${project.name}.parent/${project.basePackage}.${project.name}/build.gradle",
                model);
        writer.copyTemplate(DIRECTORY + "package.name.parent/package.name.ide/build.gradle.ftl",
                "${project.basePackage}.${project.name}.parent/${project.basePackage}.${project.name}.ide/build.gradle",
                model);
    }

}