package mx.infotec.dads.kukulkan.shell.commands.documentation;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class TemplateDocs {

	public static final List<String> DOCS_TEMPLATE_LIST;

	public static final String DOCS_TEMPLATE = "docs";

	static {
		DOCS_TEMPLATE_LIST = ImmutableList.copyOf(getDocsTemplates());
	}

	private TemplateDocs() {

	}

	public static List<String> getDocsTemplates() {
		List<String> templates = new ArrayList<>();
		templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/css/badge_only.css");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/css/badge_only.css.map");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/css/theme.css");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/css/theme.css.map");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/js/modernizr.min.js");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/js/theme.js");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/Inconsolata-Bold.ttf");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/Lato-Bold.ttf");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/RobotoSlab-Bold.ttf");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/Inconsolata-Regular.ttf");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/fontawesome-webfont.woff2");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/fontawesome-webfont.woff");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/fontawesome-webfont.eot");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/RobotoSlab-Regular.ttf");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/fontawesome-webfont.ttf");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/FontAwesome.otf");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/fontawesome-webfont.svg");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/static/fonts/Lato-Regular.ttf");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/__init__.py");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/layout.html");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/footer.html");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/versions.html");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/searchbox.html");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/theme.conf");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/breadcrumbs.html");
				templates.add(DOCS_TEMPLATE + "/_themes/sphinx_rtd_theme/search.html");
				templates.add(DOCS_TEMPLATE + "/template-DADS/conf.py_t");
				templates.add(DOCS_TEMPLATE + "/template-DADS/master_doc.rst_t");
				templates.add(DOCS_TEMPLATE + "/template-DADS/Makefile.new_t");
				templates.add(DOCS_TEMPLATE + "/template-DADS/make.bat.new_t");
				templates.add(DOCS_TEMPLATE + "/template-DADS/make.bat_t");
				templates.add(DOCS_TEMPLATE + "/source/getting-started.md");
				templates.add(DOCS_TEMPLATE + "/source/contributing.md");
				templates.add(DOCS_TEMPLATE + "/source/license.md");
				templates.add(DOCS_TEMPLATE + "/source/usage.md");
				templates.add(DOCS_TEMPLATE + "/source/FAQs.md");
				templates.add(DOCS_TEMPLATE + "/source/description.md");
				templates.add(DOCS_TEMPLATE + "/source/about-developer.md");
				templates.add(DOCS_TEMPLATE + "/source/known-issues.md");
		return templates;
	}

}
