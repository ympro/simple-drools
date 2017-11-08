package com.simpleDrools.client;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RuleBaseFacatory 单实例RuleBase生成工具
 *
 * @author quzishen
 */
@Configuration
public class RuleBaseFacatory {

    @Bean
    public RuleBase getRuleBase() {
        return RuleBaseFactory.newRuleBase();
    }
}
