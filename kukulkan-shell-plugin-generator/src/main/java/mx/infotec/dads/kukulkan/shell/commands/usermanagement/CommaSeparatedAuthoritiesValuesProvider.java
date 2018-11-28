package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.input.AbstractListValueProvider;

@Component
public class CommaSeparatedAuthoritiesValuesProvider extends AbstractListValueProvider {

    @Override
    public List<String> getInputValues() {
        return Arrays.asList("ROLE_ADMIN", "ROLE_USER");
    }

}
