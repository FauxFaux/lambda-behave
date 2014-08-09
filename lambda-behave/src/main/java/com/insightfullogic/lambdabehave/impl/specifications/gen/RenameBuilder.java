package com.insightfullogic.lambdabehave.impl.specifications.gen;

import com.insightfullogic.lambdabehave.impl.Specifier;

import java.util.ArrayList;
import java.util.List;

public final class RenameBuilder<P1> implements RenameColumns<P1> {

    private final List<Row> values = new ArrayList<>();
    private final Specifier specifier;

    private class Row {
        @LotsOfFields
        private final P1 f1;

        @LotsOfConstructor
        @LotsOfParams
        private Row(P1 f1) {
            this.f1 = f1;
        }
    }

    @LotsOfParams
    public RenameBuilder(Specifier specifier, P1 f1) {
        this.specifier = specifier;

        @LotsOfArgs
        Object ignored = and(f1);
    }

    public RenameBuilder(Specifier specifier, @LotsOfLists List<P1> f1) {
        this.specifier = specifier;
//        final int size = first.size();
//        if (size != second.size() || size != third.size()) {
//            throw new IllegalArgumentException("Lengths not identical: " + size + ", " + second.size() + ", " + third.size());
//        }

        for (int i = 0; i < f1.size(); i++) {
            @LotsOfArgs
            final Row row = new Row(f1.get(i));
            values.add(row);
        }
    }

    @Override
    @LotsOfParams
    public RenameBuilder<P1> and(P1 f1) {
        @LotsOfArgs
        final Row row = new Row(f1);
        values.add(row);
        return this;
    }

    @Override
    public RenameColumns<P1> toShow(String descriptionFormat, RenameColumnDataSpecification<P1> specification) {
        final Describer describer = new Describer(descriptionFormat);
        for (int i = 0; i < values.size(); i++) {
            Row row = values.get(i);
            @LotsOfFieldAccess
            String description = describer.describe(row.f1);
            @LotsOfFieldAccess
            final Specifier ignored = specifier.specifyBehaviour(String.valueOf(i) + ": " + description, specification, row.f1);
        }
        return this;
    }
}
