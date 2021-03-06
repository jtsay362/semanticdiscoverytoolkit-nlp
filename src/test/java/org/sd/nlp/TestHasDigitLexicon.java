/*
    Copyright 2009 Semantic Discovery, Inc. (www.semanticdiscovery.com)

    This file is part of the Semantic Discovery Toolkit.

    The Semantic Discovery Toolkit is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    The Semantic Discovery Toolkit is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with The Semantic Discovery Toolkit.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.sd.nlp;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit Tests for the HasDigitLexicon class.
 * <p>
 * @author Spence Koehler
 */
public class TestHasDigitLexicon extends TestCase {

  public TestHasDigitLexicon(String name) {
    super(name);
  }
  
  private final void verify(HasDigitLexicon lexicon, String string, boolean isHasDigits) {
    final StringWrapper.SubString subString = new StringWrapper(string).getSubString(0);
    lexicon.define(subString, lexicon.getNormalizer());
    final Categories def = subString.getCategories();
    if (isHasDigits) {
      assertTrue(def.hasType(lexicon.getCategory()));
    }
    else {
      assertTrue(def == null || !def.hasType(lexicon.getCategory()));
    }
  }

  public void testHasDigits() {
    final CategoryFactory cfactory = new CategoryFactory();
    cfactory.defineCategory(HasDigitLexicon.CATEGORY_NAME, HasDigitLexicon.CAN_GUESS);
    final HasDigitLexicon lexicon = HasDigitLexicon.getInstance(cfactory);

    verify(lexicon, "00000", true);
    verify(lexicon, "123foo456", true);
    verify(lexicon, "0000a", true);
    verify(lexicon, "3141592653589793238462643383279502884197169399375105820974944592307816406206208", true);
    verify(lexicon, "1 2", false);
    verify(lexicon, "1-2", true);
    verify(lexicon, "b32", true);
    verify(lexicon, "foo", false);
    verify(lexicon, "12-5", true);
    verify(lexicon, "20/A1", true);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(TestHasDigitLexicon.class);
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
