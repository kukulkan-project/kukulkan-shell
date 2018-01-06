/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2016 Daniel Cortes Pichardo
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
 * Rules Configuration.
 *
 * @author Daniel Cortes Pichardo
 */
@Configuration
public class RulesConfiguration {

    /**
     * Rules context configuration.
     *
     * @param ruleRepository the rule repository
     * @param ruleTypeRepository the rule type repository
     * @return the list
     */
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