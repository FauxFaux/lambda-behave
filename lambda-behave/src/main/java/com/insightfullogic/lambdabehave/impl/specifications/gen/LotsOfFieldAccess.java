package com.insightfullogic.lambdabehave.impl.specifications.gen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.LOCAL_VARIABLE)
public @interface LotsOfFieldAccess {
}