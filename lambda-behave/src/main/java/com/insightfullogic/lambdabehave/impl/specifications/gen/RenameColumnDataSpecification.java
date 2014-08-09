package com.insightfullogic.lambdabehave.impl.specifications.gen;

import com.insightfullogic.lambdabehave.expectations.Expect;

/**
 * Implement this interface to specify a behaviour that requires
 * some data values.
 */
@FunctionalInterface
public interface RenameColumnDataSpecification<F1> {

    /**
     * Callback method which specifies the actual behaviour.
     *
     * @param expect the callback object used to describe expectations
     */
    public void specifyBehaviour(Expect expect, F1 f1);
}
