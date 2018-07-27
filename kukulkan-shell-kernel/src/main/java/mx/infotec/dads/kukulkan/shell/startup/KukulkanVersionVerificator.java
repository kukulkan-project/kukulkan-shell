package mx.infotec.dads.kukulkan.shell.startup;

import org.jline.utils.AttributedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import mx.infotec.dads.kukulkan.shell.services.PrintService;

@Component
public class KukulkanVersionVerificator {

    @Autowired
    private PrintService printService;
    @Autowired
    RestTemplate template;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        printService.print(new AttributedString("You are running latest version"));
        // String fooResourceUrl = "https://httpbin.org/get";
        // ResponseEntity<String> response =
        // template.getForEntity(fooResourceUrl , String.class);
        // printService.print("Init", response.toString());
    }
}