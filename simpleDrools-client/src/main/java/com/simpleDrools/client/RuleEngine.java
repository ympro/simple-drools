package com.simpleDrools.client;

/**
 * 规则接口
 *
 * @author quzishen
 */
public interface RuleEngine<T> {

    /**
     * 初始化规则引擎
     */
    void initEngine() throws Exception;

    /**
     * 刷新规则引擎中的规则
     */
    void refreshEngineRule() throws Exception;

    /**
     * 执行规则引擎
     *
     * @param fact 积分Fact
     */
    void executeRuleEngine(final T fact);
}
