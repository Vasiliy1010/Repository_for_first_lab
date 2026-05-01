package ru.labs.hm1.output;

import ru.labs.hm1.model.Mission;

public abstract class BaseDecoratorSummary implements InterfaceSummary {
    protected InterfaceSummary wrapper;
    protected Mission mission;

    public BaseDecoratorSummary(InterfaceSummary wrapper, Mission mission){
        this.wrapper = wrapper;
        this.mission = mission;
    }

    @Override
    public String getSummary() {
        return wrapper.getSummary();
    }
}