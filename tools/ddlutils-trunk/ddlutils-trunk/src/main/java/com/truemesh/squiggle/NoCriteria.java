package com.truemesh.squiggle;

import com.truemesh.squiggle.Criteria;
import com.truemesh.squiggle.output.Output;

/**
 * Class NoCriteria is a Criteria that represents an absent operand in an SQL
 * predicate expression so that one may represent a unary operator (for example,
 * {@link NOT}) using a binary operator derived from a {@link BaseLogicGroup}).
 * 
 * @author <a href="mailto:derek@derekmahar.ca">Derek Mahar</a>
 */
public class NoCriteria extends Criteria {
  /**
   * Writes an empty criteria (single space) to the given output stream.
   * 
   * @see Criteria#write(Output)
   */
  public void write(Output out) {
    out.print(' ');
  }
}
