package com.insightfullogic.lambdabehave.impl.specifications.gen;

public class Describer {

    private final String descriptionFormat;

    public Describer(String descriptionFormat) {
        this.descriptionFormat = descriptionFormat;
    }

    public String describe(Object... vals) {
        String description = String.format(descriptionFormat, vals);
        if (!description.equals(descriptionFormat)) {
            return description;
        }

        description += ": ";
        for (int i = 0; i < vals.length; i++) {
            Object o = vals[i];
            description += o;
            if (i != vals.length - 1) {
                description += ", ";
            }
        }
        return description;
    }
}
