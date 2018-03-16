import { ContainerModule } from "inversify";
    import { LanguageClientContribution } from '@theia/languages/lib/browser';
    import { DslClientContribution } from "./language-contribution";

export default new ContainerModule(bind => {
    // add your contribution bindings here
    
    bind<LanguageClientContribution>(LanguageClientContribution).to(DslClientContribution).inSingletonScope();

});