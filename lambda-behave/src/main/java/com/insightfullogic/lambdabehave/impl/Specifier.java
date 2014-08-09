package com.insightfullogic.lambdabehave.impl;

import com.insightfullogic.lambdabehave.Block;
import com.insightfullogic.lambdabehave.Description;
import com.insightfullogic.lambdabehave.SpecificationDeclarationException;
import com.insightfullogic.lambdabehave.generators.GeneratedDescription;
import com.insightfullogic.lambdabehave.generators.SourceGenerator;
import com.insightfullogic.lambdabehave.impl.generators.GeneratedDescriptionBuilder;
import com.insightfullogic.lambdabehave.impl.reports.Report;
import com.insightfullogic.lambdabehave.impl.specifications.TitledTable;
import com.insightfullogic.lambdabehave.impl.specifications.gen.LotsOfParams;
import com.insightfullogic.lambdabehave.impl.specifications.gen.RenameBuilder;
import com.insightfullogic.lambdabehave.impl.specifications.gen.RenameColumnDataSpecification;
import com.insightfullogic.lambdabehave.impl.specifications.gen.RenameColumns;
import com.insightfullogic.lambdabehave.specifications.*;
import org.mockito.Mockito;
import org.mockito.cglib.proxy.Enhancer;
import org.mockito.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

/**
 * A Specifier defines how .
 */
public class Specifier implements Description {

    private final String suiteName;

    private final Blocks initializers;
    private final Blocks prefixes;
    private final List<Behaviour> behaviours;
    private final Blocks postfixes;
    private final Blocks completers;
    private final SourceGenerator sourceGenerator;

    public Specifier(String suite, SourceGenerator sourceGenerator) {
        this.suiteName = suite;
        this.sourceGenerator = sourceGenerator;

        initializers = new Blocks();
        prefixes = new Blocks();
        behaviours = new ArrayList<>();
        postfixes = new Blocks();
        completers = new Blocks();
    }

    @LotsOfParams
    public <F1> Specifier specifyBehaviour(String description, RenameColumnDataSpecification<F1> specification, F1 f1) {
        should(description, expect -> specification.specifyBehaviour(expect, f1));
        return this;
    }

    @Override
    public void should(String description, Specification specification) {
        Objects.nonNull(description);
        Objects.nonNull(specification);

        if (behaviours.removeIf(behaviour -> behaviour.hasDescription(description))) {
            behaviours.add(new Behaviour(description, expect -> {

                throw new SpecificationDeclarationException(
                        "You can't declare multiple specifications with the same name. Name: '" + description + "'");

            }));
        } else {
            behaviours.add(new Behaviour(description, specification));
        }
    }

    @Override
    public <T> RenameColumns<T> uses(T value) {
        return new RenameBuilder<T>(this, value);
    }

    @Override
    public <T> RenameColumns<T> uses(List<T> values) {
        // Additional arraylist required to ensure
        // we can with more values
        return new RenameBuilder<T>(this, new ArrayList<>(values));
    }

    @Override
    public <T> RenameColumns<T> uses(Stream<T> values) {
        return uses(values.collect(toList()));
    }

    public <T, F, S> TitledTable<T, F, S> usesTable(Class<T> clazz, Function<T, F> first, Function<T, S> second) {
        List<Method> m = new ArrayList<>();
        final T mock = (T) Enhancer.create(clazz, (MethodInterceptor) (o, method, objects, methodProxy) -> {
            m.add(method);
            return null;
        });
        first.apply(mock);
        second.apply(mock);
        return new TitledTable<>(m, this, clazz);
    }

    @Override
    public GeneratedDescription requires(int exampleCount) {
        return new GeneratedDescriptionBuilder(sourceGenerator, exampleCount, this);
    }

    @Override
    public void isSetupWith(Block block) {
        prefixes.add(block);
    }

    @Override
    public void initializesWith(Block block) {
        initializers.add(block);
    }

    @Override
    public void isConcludedWith(Block block) {
        postfixes.add(block);
    }

    @Override
    public void completesWith(Block block) {
        completers.add(block);
    }

    @Override
    public <T> T usesMock(Class<T> classToMock) {
        final T mockObject = Mockito.mock(classToMock);
        postfixes.add(() -> {
            Mockito.reset(mockObject);
        });

        return mockObject;
    }

    public void checkSpecifications(Report report) {
        report.onSuiteName(suiteName);
        completeBehaviours().forEach(behaviour -> report.recordSpecification(suiteName, behaviour.checkCompleteBehaviour()));
    }

    public Stream<CompleteBehaviour> completeBehaviours() {
        if (behaviours.isEmpty())
            return Stream.empty();

        return concat(
                concat(initializers.completeFixtures("initializer"),
                       completeSpecifications()),
                       completers.completeFixtures("completer"));
    }

    private Stream<CompleteBehaviour> completeSpecifications() {
        return behaviours.stream()
                         .map(behaviour -> new CompleteSpecification(prefixes, behaviour, postfixes, suiteName));
    }

    public String getSuiteName() {
        return suiteName;
    }

}
