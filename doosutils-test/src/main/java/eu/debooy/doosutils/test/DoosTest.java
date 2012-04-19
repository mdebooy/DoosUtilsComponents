/**
 * Copyright 2010 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package eu.debooy.doosutils.test;

import junit.framework.TestCase;


/**
 * @author Marco de Booij
 */
public class DoosTest extends TestCase
{
  protected Boolean isInitialized = Boolean.FALSE;

  public DoosTest(String testName) {
    super(testName);
  }

  @Override
  public void setUp() {
    System.out.println("In setUp method");
    if (Boolean.TRUE == this.isInitialized) {
      return;
    }

    this.isInitialized = Boolean.TRUE;
  }

  @Override
  protected void tearDown() throws Exception
  {
    System.out.println("In tearDown method");
    super.tearDown();
  }
}
