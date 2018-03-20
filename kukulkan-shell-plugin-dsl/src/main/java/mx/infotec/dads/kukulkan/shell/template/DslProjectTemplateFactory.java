/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez <robertovillarejom@gmail.com>
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

package mx.infotec.dads.kukulkan.shell.template;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * 
 * @author Roberto Villarejo Martínez <robertovillarejom@gmail.com>
 *
 */
public class DslProjectTemplateFactory {

	public static final List<String> DSL_TEMPLATE_LIST;

	public static final String DSL_TEMPLATE = "archetypes/dsl-project";

	static {
		DSL_TEMPLATE_LIST = ImmutableList.copyOf(getDslProjectTemplates());
	}

	private DslProjectTemplateFactory() {
	}

	public static List<String> getDslProjectTemplates() {
		List<String> templates = new ArrayList<>();
		templates.addAll(getRootTemplates());
		templates.addAll(getExtensionTemplates());
		return templates;
	}

	public static List<String> getRootTemplates() {
		List<String> templates = new ArrayList<>();
		templates.add(DSL_TEMPLATE + "/package.json.ftl");
		templates.add(DSL_TEMPLATE + "/electron-builder.yml.ftl");
		templates.add(DSL_TEMPLATE + "/.gitignore.ftl");
		templates.add(DSL_TEMPLATE + "/README.md.ftl");
		return templates;
	}

	public static List<String> getExtensionTemplates() {
		List<String> templates = new ArrayList<>();
		templates.add(DSL_TEMPLATE + "/name-extension/src/node/name-backend-module.ts.ftl");
		templates.add(DSL_TEMPLATE + "/name-extension/package.json.ftl");
		templates.add(DSL_TEMPLATE + "/name-extension/src/browser/name-frontend-module.ts.ftl");
		templates.add(DSL_TEMPLATE + "/name-extension/src/browser/language-contribution.ts.ftl");
		templates.add(DSL_TEMPLATE + "/name-extension/src/browser/monaco.d.ts.ftl");
		templates.add(DSL_TEMPLATE + "/name-extension/tsconfig.json");
		return templates;
	}

	public static List<String> getDslProjectResources() {
		List<String> resources = new ArrayList<>();
		resources.add("templates/" + DSL_TEMPLATE + "/resources/icons/512x512.png");
		resources.add("templates/" + DSL_TEMPLATE + "/resources/dmgInstaller.tiff");
		resources.add("templates/" + DSL_TEMPLATE + "/resources/icon.icns");
		resources.add("templates/" + DSL_TEMPLATE + "/resources/icon.ico");
		resources.add("templates/" + DSL_TEMPLATE + "/resources/Icon.png");
		resources.add("templates/" + DSL_TEMPLATE + "/resources/installerSidebar.bmp");
		return resources;
	}
	
	public static List<String> getXTextProjectTemplates() {
		List<String> templates = new ArrayList<>();
		templates.add(DSL_TEMPLATE + "/package.name.parent/build.gradle.ftl");
		templates.add(DSL_TEMPLATE + "/package.name.parent/settings.gradle.ftl");
		templates.add(DSL_TEMPLATE + "/package.name.parent/package.name/build.gradle.ftl");
		templates.add(DSL_TEMPLATE + "/package.name.parent/package.name/src/main/java/qualified/GenerateName.mwe2.ftl");
		templates.add(DSL_TEMPLATE + "/package.name.parent/package.name/src/main/java/qualified/Name.xtext.ftl");
		templates.add(DSL_TEMPLATE + "/package.name.parent/package.name.ide/build.gradle.ftl");
		return templates;
	}
	
	public static List<String> getXTextProjectResources() {
		List<String> resources = new ArrayList<>();
		resources.add("templates/" + DSL_TEMPLATE + "/package.name.parent/gradle/maven-deployment.gradle");
		resources.add("templates/" + DSL_TEMPLATE + "/package.name.parent/gradle/source-layout.gradle");
		resources.add("templates/" + DSL_TEMPLATE + "/package.name.parent/gradle/wrapper/gradle-wrapper.jar");
		resources.add("templates/" + DSL_TEMPLATE + "/package.name.parent/gradle/wrapper/gradle-wrapper.properties");
		resources.add("templates/" + DSL_TEMPLATE + "/package.name.parent/gradlew");
		resources.add("templates/" + DSL_TEMPLATE + "/package.name.parent/gradlew.bat");
		return resources;
	}

}
