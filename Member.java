package org.bitenet.brains.genetic;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface Member<T extends Member<?>> {
public T breed(T m);
public int score(ArrayList<ArrayList<Float>> in, ArrayList<ArrayList<Float>> goal);
public T random(int inputSize, int outputSize);
}
