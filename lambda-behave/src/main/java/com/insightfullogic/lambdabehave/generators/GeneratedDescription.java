package com.insightfullogic.lambdabehave.generators;

import com.insightfullogic.lambdabehave.impl.specifications.gen.RenameColumns;

/**
 * A fluent builder interface for describing how test cases get generated.
 */
public interface GeneratedDescription {

    /**
     * Set a new source generator.
     *
     * The source generator is the component which provides a source of numbers, upon which random test
     * case generation is performed.
     *
     * If not set the default will generate random numbers.
     *
     * @param sourceGenerator the new source generator
     * @return this
     */
    GeneratedDescription withSource(SourceGenerator sourceGenerator);

    /**
     * Use this generator to produce a single column of example testcases.
     *
     * @param generator the generator to use to produce the test case values
     * @return this
     */
    <F1> RenameColumns<F1> example(Generator<F1> generator);
}
