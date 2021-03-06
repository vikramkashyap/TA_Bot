package ta_bot;

/*
 * Purpose: This represents a member to be evolved
 * 
 * @author Carson Cummins
 * @version 0.0
 */
public interface Member<T extends Member<T>> {
//should return 2 kids
public T[] breed(T m);
public double score(boolean passed);
public T random();
public void mutate();
public void print();
public T clone();
public void serialize(String s);
}
