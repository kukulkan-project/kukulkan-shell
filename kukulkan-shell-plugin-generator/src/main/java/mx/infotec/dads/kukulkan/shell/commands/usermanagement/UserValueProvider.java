package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.domain.ShellCompletionProposal;

@Component
public class UserValueProvider extends ValueProviderSupport {

	@Autowired
	private UserAuthorityManagementCommands usersMgmt;

	@Override
	public List<CompletionProposal> complete(MethodParameter arg0, CompletionContext arg1, String[] arg2) {
		UserAuthorityRepository repo = usersMgmt.createRepo();
		return repo.findAll().stream()
				.map(user -> new ShellCompletionProposal(user.getId().toString(), user.getLogin()))
				.collect(Collectors.toList());
	}

}
