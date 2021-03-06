package com.insightfullogic.lambdabehave.impl;

import com.insightfullogic.lambdabehave.BehaveRunner;
import com.insightfullogic.lambdabehave.SpecificationError;
import com.insightfullogic.lambdabehave.TestFailure;
import com.insightfullogic.lambdabehave.impl.CompleteBehaviour;
import com.insightfullogic.lambdabehave.impl.Specifier;
import com.insightfullogic.lambdabehave.impl.reports.SpecificationReport;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class AbstractSuiteRunner extends ParentRunner<CompleteBehaviour> {

	protected static final Logger log = LoggerFactory.getLogger(AbstractSuiteRunner.class);

    protected final List<CompleteBehaviour> children;
    protected final String name;

    public AbstractSuiteRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        Specifier specifier = BehaveRunner.declareOnly(testClass);
        name = specifier.getSuiteName();
        children = specifier.completeBehaviours().collect(toList());
    }

    @Override
    protected String getName() {
        return name;
    }

    @Override
    protected List<CompleteBehaviour> getChildren() {
        return children;
    }

    @Override
    protected Description describeChild(CompleteBehaviour child) {
        return Description.createTestDescription(getName(), child.getDescription());
    }

    @Override
    protected void runChild(CompleteBehaviour child, RunNotifier notifier) {
        try {
            SpecificationReport report = child.checkCompleteBehaviour();
            reportResults(notifier, report, describeChild(child));
        } catch (Exception e) {
            notifier.fireTestFailure(new Failure(getDescription(), e));
            log.error(e.getMessage(), e);
        }
    }

    protected void reportResults(RunNotifier notifier, SpecificationReport spec, Description test) {
        notifier.fireTestStarted(test);
        switch (spec.getResult()) {
            case SUCCESS:
                notifier.fireTestFinished(test);
                return;
            case FAILURE:
                notifier.fireTestFailure(new Failure(test, new TestFailure(spec.getMessage())));
                return;
            case ERROR:
            default:
                throw new SpecificationError(spec.getMessage(), spec.getCause());
        }
    }
}
