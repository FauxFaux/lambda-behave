package com.insightfullogic.lambdabehave;

import com.insightfullogic.lambdabehave.generators.GeneratedDescription;
import com.insightfullogic.lambdabehave.specifications.Specification;

import java.util.List;
import java.util.stream.Stream;

/**
 * <p>
 * A Description is a fluent builder to describe a
 * complete specification.
 * </p>
 *
 * <p>
 * Most specifications will
 * want to use the 'should' method in order to declare
 * a specification.
 * </p>
 *
 * <p>
 * If you want to declare a data-driven specification
 * then start writing your specification with one of
 * the 'uses' methods.
 * </p>
 *
 * @see com.insightfullogic.lambdabehave.specifications.Specification
 */
public interface Description {

    /**
     * Specify a behaviour.
     *
     * @param description a human readable description of the behaviour you're expecting.
     * @param specification a function which describes in code the expected behaviour.
     */
    void should(String description, Specification specification);

    /**
     * Specify a single value data driven behaviour.
     *
     * @param value the only value to parameterise by
     * @return a fluent builder for a column of values
     */
    <T> com.insightfullogic.lambdabehave.impl.specifications.gen.RenameColumns<T> uses(T value);

    /**
     * Specify a single value data driven behaviour.
     *
     * @param values a list of values to wrap as a Column
     * @return a fluent builder for a column of values
     */
    <T> com.insightfullogic.lambdabehave.impl.specifications.gen.RenameColumns<T> uses(List<T> values);

    /**
     * Specify a single value data driven behaviour.
     *
     * @param values a Stream of values to wrap as a Column
     * @return a fluent builder for a column of values
     */
    <T> com.insightfullogic.lambdabehave.impl.specifications.gen.RenameColumns<T> uses(Stream<T> values);

    /**
     * Create a fluent builder to do automatic testcase generation.
     *
     * @param exampleCount the number of example test cases to be generated
     * @return the description builder
     */
    GeneratedDescription requires(int exampleCount);

    /**
     * Run some code before each of the specifications.
     *
     * @param block the code to run.
     */
    void isSetupWith(Block block);

    /**
     * Run some code before all of the specifications.
     *
     * @param block the code to run.
     */
    void initializesWith(Block block);

    /**
     * Run some code after each of the specifications.
     *
     * @param block the code to run.
     */
    void isConcludedWith(Block block);

    /**
     * Run some code after all of the specifications.
     *
     * @param block the code to run.
     */
    void completesWith(Block block);

    /**
     * Creates a mock similar to Mockito.mock which gets reset between tests.
     *
     * @param classToMock the class of the mock object
     * @param <T> the type parameter of the mock object's type
     * @return the mock object
     */
    <T> T usesMock(Class<T> classToMock);

}
