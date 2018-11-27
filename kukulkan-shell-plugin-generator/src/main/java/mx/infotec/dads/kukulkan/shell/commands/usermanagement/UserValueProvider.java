package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.services.PrintService;

@Component
public class UserValueProvider extends ValueProviderSupport {

    @Autowired
    private UsersManagement usersMgmt;

    @Autowired
    private PrintService printer;

    @Override
    public List<CompletionProposal> complete(MethodParameter arg0, CompletionContext arg1, String[] arg2) {
        try {
            UserRepository repo = usersMgmt.createUserRepository();
            return repo.findAll().stream().map(user -> new CompletionProposal(user.getId().toString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            printer.warning("Can't get users list");
        }
        return Collections.emptyList();
    }

}
