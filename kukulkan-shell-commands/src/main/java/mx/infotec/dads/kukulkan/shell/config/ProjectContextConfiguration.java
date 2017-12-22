package mx.infotec.dads.kukulkan.shell.config;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommandContext;
import mx.infotec.dads.kukulkan.shell.services.NativeCommandProvided;
import mx.infotec.dads.kukulkan.shell.services.NativeCommandService;

@Configuration
public class ProjectContextConfiguration {

    @Bean
    public NativeCommandContext configProjectContext(List<NativeCommandProvided> nativeCommands,
            NativeCommandService commandService) {
        NativeCommandContext context = new NativeCommandContext();
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
