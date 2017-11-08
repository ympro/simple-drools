package com.simpleDrools.server.rule.rule1;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * 使用kmodule的方式调用drools
 * /resources/META-INF/kmodule.xml
 */
public class rule1 {
    public static void main(final String[] args) {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        System.out.println(kc.verify().getMessages().toString());
        execute(kc);
    }

    private static void execute(KieContainer kc) {
        KieSession ksession = kc.newKieSession("rule1KS");


        ksession.fireAllRules();

        ksession.dispose();
    }
}
