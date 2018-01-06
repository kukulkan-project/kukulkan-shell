package mx.infotec.dads.kukulkan.shell.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;

import mx.infotec.dads.kukulkan.generator.angularjs.domain.Rule;
import mx.infotec.dads.kukulkan.generator.angularjs.domain.RuleType;
import mx.infotec.dads.kukulkan.generator.angularjs.repository.RuleRepository;
import mx.infotec.dads.kukulkan.generator.angularjs.repository.RuleTypeRepository;
import mx.infotec.dads.kukulkan.metamodel.util.InflectorProcessor;

/**
 * Rules Configuration
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Configuration
public class RulesConfiguration {

    @Bean
    public List<Rule> rulesContextConfiguration(RuleRepository ruleRepository, RuleTypeRepository ruleTypeRepository) {
        Rule rule = new Rule();
        RuleType ruleType = ruleTypeRepository.findAll().get(0);
        ruleType.setName("singular");
        rule.setRuleType(ruleType);
        Example<Rule> ruleExample = Example.of(rule);
        List<Rule> rulesList = ruleRepository.findAll(ruleExample);
        for (Rule item : rulesList) {
            InflectorProcessor.getInstance().addSingularize(item.getExpression(), item.getReplacement());
        }
        return rulesList;
    }
}