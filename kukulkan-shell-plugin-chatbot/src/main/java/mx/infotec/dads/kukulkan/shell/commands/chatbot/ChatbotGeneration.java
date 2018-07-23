/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
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
package mx.infotec.dads.kukulkan.shell.commands.chatbot;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;
import mx.infotec.dads.kukulkan.shell.commands.navigation.FileNavigationCommands;
import mx.infotec.dads.kukulkan.shell.commands.util.Mapper;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.generator.ChatbotClientGenerator;
import mx.infotec.dads.kukulkan.shell.generator.ChatbotContext;
import mx.infotec.dads.kukulkan.shell.generator.ChatbotGenerator;
import mx.infotec.dads.kukulkan.shell.services.PrintService;
import mx.infotec.dads.kukulkan.shell.util.ProjectUtil;

/**
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
@ShellComponent
public class ChatbotGeneration extends AbstractCommand {

    @Autowired
    private ChatbotGenerator generator;

    @Autowired
    private ChatbotClientGenerator clientGenerator;

    @Autowired
    private FileNavigationCommands navCmds;

    @Autowired
    private PrintService printService;

    @ShellMethod("Generate a chatbot broker")
    public void chatbotProject(String name) {
        ChatbotContext chatbotContext = Mapper.toContext(name);
        chatbotContext.setOutputDir(navigator.getCurrentPath());
        GeneratorContext genContext = new GeneratorContext();
        genContext.put(ChatbotContext.class, chatbotContext);
        generator.process(genContext);
        navCmds.cd(navigator.getCurrentPath().resolve(name).toString());
        printService.info("Running 'yarn install'");
        boolean yarnSucceded = commandService.execToConsole(new ShellCommand("yarn", "install"));
        if (!yarnSucceded) {
            printService.info("Configure your '.env' and then run 'yarn start:dev to run the project'");
        } else {
            printService.error("Failed to execute 'yarn install");
            printService.error("Run yarn install manually");
        }
    }

    @ShellMethod("Add chatbot client to Kukulkan project")
    public void addChatbot() {
        Optional<ProjectConfiguration> projectConfig = ProjectUtil.readKukulkanFile(navigator.getCurrentPath());
        if (!projectConfig.isPresent()) {
            printService.error("This path does not contains a Kukulkan project: " + navigator.getCurrentPath());
        } else {
            GeneratorContext genContext = new GeneratorContext();
            genContext.put(ProjectConfiguration.class, projectConfig.get());
            clientGenerator.process(genContext);
        }
    }

}
