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
import mx.infotec.dads.kukulkan.shell.services.WriterService;

/**
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
@GeneratorComponent
public class DslProjectGenerator implements Generator {

    @Autowired
    private WriterService writer;

    private static final Logger LOGGER = LoggerFactory.getLogger(DslProjectGenerator.class);

    private static final String DIRECTORY = "archetypes/dsl-project/";

    @Override
    public String getName() {
        return "dsl-generator";
    }

    @Override
    public void process(GeneratorContext context) {
        DslProjectContext dslContext = requiredNotEmpty(context.get(DslProjectContext.class));
        Map<String, Object> model = new HashMap<>();
        model.put("project", dslContext);

        LOGGER.info("Generating {} DSL project", dslContext.getName());

        // Wirting Theia extension
        writer.copyTemplate(DIRECTORY + "name-extension/package.json.ftl", "${project.name}-extension/package.json",
                model);
        writer.copy(DIRECTORY + "name-extension/tsconfig.json", "${project.name}-extension/tsconfig.json", model);
        writer.copyTemplate(DIRECTORY + "name-extension/src/browser/language-contribution.ts.ftl",
                "${project.name}-extension/src/browser/language-contribution.ts", model);
        writer.copyTemplate(DIRECTORY + "name-extension/src/browser/monaco.d.ts.ftl",
                "${project.name}-extension/src/browser/monaco.d.ts", model);
        writer.copyTemplate(DIRECTORY + "name-extension/src/browser/name-frontend-module.ts.ftl",
                "${project.name}-extension/src/browser/${project.name}-frontend-module.ts", model);
        writer.copyTemplate(DIRECTORY + "name-extension/src/node/name-backend-module.ts.ftl",
                "${project.name}-extension/src/node/${project.name}-backend-module.ts", model);

        // Writing root project
        writer.copyTemplate(DIRECTORY + "electron-builder.yml.ftl", "electron-builder.yml", model);
        writer.copyTemplate(DIRECTORY + "package.json.ftl", "package.json", model);
        writer.copyTemplate(DIRECTORY + "README.md.ftl", "README.md", model);

        // Copying resources
        writer.copyDir(DslProjectGenerator.class, "resources", "resources", model);

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