package mx.infotec.dads.kukulkan.shell.config;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.ProjectContext;
import mx.infotec.dads.kukulkan.shell.services.NativeCommandProvided;
import mx.infotec.dads.kukulkan.shell.services.NativeCommandService;

@Configuration
public class ProjectContextConfiguration {

    @Bean
    public ProjectContext configProjectContext(List<NativeCommandProvided> nativeCommands,
            NativeCommandService commandService) {
        ProjectContext context = new ProjectContext();
        context.setAvailableCommands(mapDefaultNativeCommands(nativeCommands, commandService));
        return context;
    }

    private SortedMap<String, NativeCommand> mapDefaultNativeCommands(List<NativeCommandProvided> nativeCommands,
            NativeCommandService commandService) {
        SortedMap<String, NativeCommand> cmdSet = new TreeMap<>();
        nativeCommands.forEach(opt -> opt.getNativeCommand().ifPresent(cmd -> {
            commandService.isPresent(cmd);
            cmdSet.put(cmd.getCommand(), cmd);
        }));
        return cmdSet;
    }
}
