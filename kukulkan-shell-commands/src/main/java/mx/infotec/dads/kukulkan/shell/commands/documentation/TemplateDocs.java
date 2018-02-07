package mx.infotec.dads.kukulkan.shell.commands.documentation;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class TemplateDocs {

	public static final List<String> DOCS_TEMPLATE_LIST;

	public static final String DOCS_TEMPLATE = "";

	static {
		DOCS_TEMPLATE_LIST = ImmutableList.copyOf(getDocsTemplates());
	}

	private TemplateDocs() {

	}

	public static List<String> getDocsTemplates() {
		List<String> templates = new ArrayList<>();
		templates.add("");
		return templates;
	}

}
