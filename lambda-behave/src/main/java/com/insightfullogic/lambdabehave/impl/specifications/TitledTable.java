package com.insightfullogic.lambdabehave.impl.specifications;

import com.insightfullogic.lambdabehave.impl.Specifier;
import org.mockito.cglib.proxy.Enhancer;
import org.mockito.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TitledTable<T, F, S> {
    private final List<Method> methods;
    private final Specifier specifier;
    private final Class clazz;

    private final List<F> firsts = new ArrayList<>();
    private final List<S> seconds = new ArrayList<>();

    public TitledTable(List<Method> methods, Specifier specifier, Class clazz) {
        this.methods = methods;
        this.specifier = specifier;
        this.clazz = clazz;
    }

    public TitledTable<T, F, S> toShow(String description, ColumnDataSpecification1<T> specification) {
        final Iterator<F> fit = firsts.iterator();
        final Iterator<S> sit = seconds.iterator();
        while (fit.hasNext()) {
            Object[] values = new Object[]{fit.next(), sit.next()};
            final T mock = (T) Enhancer.create(clazz, (MethodInterceptor) (o, method, objects, methodProxy) -> values[methods.indexOf(method)]);
            final String describe = String.format(description,
                    values[0], values[1],
                    methods.get(0).getName(), methods.get(1).getName());
            specifier.specifyBehaviour(describe, specification, mock);
        }
        return this;
    }

    public TitledTable<T, F, S> withRow(F first, S second) {
        firsts.add(first);
        seconds.add(second);
        return this;
    }
}
