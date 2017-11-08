package com.simpleDrools.client;

import com.google.common.collect.Lists;

import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.drools.spi.Activation;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import javax.annotation.Resource;

/**
 * 规则接口实现类
 *
 * @author quzishen
 */
@Service
public class RuleEngineImpl<T> implements RuleEngine<T> {

    @Resource
    private RuleBase ruleBase;

    /**
     *  init
     * @see com.simpleDrools.client.RuleEngine#initEngine()
     */
    public void initEngine() throws Exception {
        // 设置时间格式
        PackageBuilder backageBuilder = getPackageBuilderFromDrlFile();
        ruleBase.addPackages(backageBuilder.getPackages());
    }

    /**
     *  refresh EngineRule
     * @see com.simpleDrools.client.RuleEngine#refreshEngineRule()
     */
    public void refreshEngineRule() throws Exception {
        org.drools.rule.Package[] packages = ruleBase.getPackages();
        for (org.drools.rule.Package pg : packages) {
            ruleBase.removePackage(pg.getName());
        }
        initEngine();
    }

    /**
     *  execute rule
     * @see com.simpleDrools.client.RuleEngine#executeRuleEngine(T fact)
     */
    public void executeRuleEngine(final T fact) {
        if (null == ruleBase.getPackages() || 0 == ruleBase.getPackages().length) {
            return;
        }

        StatefulSession statefulSession = ruleBase.newStatefulSession();
        statefulSession.insert(fact);

        // fire
        statefulSession.fireAllRules(new org.drools.spi.AgendaFilter() {
            // required activate rule`name contains factName
            public boolean accept(Activation activation) {
                String factName = fact.getClass().getSimpleName();
                return !activation.getRule().getName().contains(factName);
            }
        });

        statefulSession.dispose();
    }

    /**
     * 从Drl规则String中读取规则
     */
    private PackageBuilder getPackageBuilderFromDrlFile() throws Exception {
        // 获取测试脚本文件
        List<String> drlFilePath = getDrlStrings();
        // 装载测试脚本文件
        List<Reader> readers = readRuleFromDrlString(drlFilePath);

        PackageBuilder backageBuilder = new PackageBuilder();
        for (Reader reader : readers) {
            backageBuilder.addPackageFromDrl(reader);
        }

        // 检查脚本是否有问题
        if (backageBuilder.hasErrors()) {
            throw new Exception(backageBuilder.getErrors().toString());
        }

        return backageBuilder;
    }

    /**
     * @param drlFilePath 规则字符串
     */
    private List<Reader> readRuleFromDrlString(List<String> drlFilePath) throws FileNotFoundException {
        if (null == drlFilePath || 0 == drlFilePath.size()) {
            return null;
        }

        List<Reader> readers = Lists.newArrayList();
        for (String ruleFilePath : drlFilePath) {
            readers.add(new StringReader(ruleFilePath));
        }
        return readers;
    }

    /**
     * 获取测试规则文件
     */
    private List<String> getDrlStrings() {

        return null;
    }
}