package mx.infotec.dads.kukulkan.shell.startup;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jline.utils.AttributedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommandContext;
import mx.infotec.dads.kukulkan.shell.services.NativeCommandProvided;
import mx.infotec.dads.kukulkan.shell.services.NativeCommandService;
import mx.infotec.dads.kukulkan.shell.services.PrintService;

@Component
public class ShellStarter {

    @Autowired
    private PrintService printService;

    @Autowired
    private RestTemplate template;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private List<NativeCommandProvided> nativeCommands;

    @Autowired
    private NativeCommandContext nativeContext;

    @Autowired
    private NativeCommandService commandService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        printService.print(new AttributedString("You are running latest version"));
        executor.execute(
                () -> nativeContext.setAvailableCommands(mapDefaultNativeCommands(nativeCommands, commandService)));
    }

    /**
     * Map default native commands.
     *
     * @param nativeCommands
     *            the native commands
     * @param commandService
     *            the command service
     * @return the sorted map
     */
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