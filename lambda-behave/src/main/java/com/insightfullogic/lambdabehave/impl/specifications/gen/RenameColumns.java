package com.insightfullogic.lambdabehave.impl.specifications.gen;

public interface RenameColumns<@LotsOfGenerics F1> {

    /**
     * Specify the actual behaviour.
     *
     * @param description a human readable description of the behaviour you're expecting.
     * @param specification a function which describes in code the expected behaviour.
     * @return this
     */
    @LotsOfGenerics
    RenameColumns<F1> toShow(String description, RenameColumnDataSpecification<F1> specification);

    /**
     * Add another bunch of elements to the column.
     * @return this the fluent builder object
     */
    RenameColumns<F1> and(F1 f1);

}
