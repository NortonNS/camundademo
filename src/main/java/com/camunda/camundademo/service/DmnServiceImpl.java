package com.camunda.camundademo.service;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;

@Service
public class DmnServiceImpl implements DmnService {

    private DmnEngine dmnEngine;
    private DmnDecision decision;

    @Override
//    @PostConstruct
    public void initDmnEngine() {
        dmnEngine = DmnEngineConfiguration
                .createDefaultDmnEngineConfiguration().buildEngine();

        DmnModelInstance dmnModelInstance = Dmn.readModelFromFile(Paths.get("rules/beverage.dmn").toFile());
        decision = dmnEngine.parseDecision("DrinkDecision", dmnModelInstance);
    }

    @Override
    public String decideDrink(String name, String timeOfDay) {
        this.initDmnEngine();
        VariableMap variables = Variables.createVariables()
                .putValue("name", name.replace("%20"," "))
                .putValue("timeOfDay", timeOfDay);
        return dmnEngine.evaluateDecision(decision, variables).getSingleResult().getEntry("drink");
    }
}
