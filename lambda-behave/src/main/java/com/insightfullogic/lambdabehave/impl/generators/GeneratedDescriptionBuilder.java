package com.insightfullogic.lambdabehave.impl.generators;

import com.insightfullogic.lambdabehave.generators.GeneratedDescription;
import com.insightfullogic.lambdabehave.generators.Generator;
import com.insightfullogic.lambdabehave.generators.SourceGenerator;
import com.insightfullogic.lambdabehave.impl.Specifier;
import com.insightfullogic.lambdabehave.impl.specifications.gen.RenameBuilder;
import com.insightfullogic.lambdabehave.impl.specifications.gen.RenameColumns;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class GeneratedDescriptionBuilder implements GeneratedDescription {

    private final int numberOfInstances;
    private final Specifier specifier;

    private SourceGenerator sourceGenerator;

    public GeneratedDescriptionBuilder(final SourceGenerator sourceGenerator, final int numberOfInstances, final Specifier specifier) {
        this.sourceGenerator = sourceGenerator;
        this.numberOfInstances = numberOfInstances;
        this.specifier = specifier;
    }

    @Override
    public GeneratedDescription withSource(SourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
        return this;
    }

    @Override
    public <F1> RenameColumns<F1> example(
            Generator<F1> firstGenerator) {

        return new RenameBuilder<F1>(specifier,
                generateValues(firstGenerator)
                );
    }

    private <T> List<T> generateValues(Generator<T> generator) {
        return Stream.generate(() -> generator.generate(sourceGenerator))
                     .limit(numberOfInstances)
                     .collect(toList());
    }

}
