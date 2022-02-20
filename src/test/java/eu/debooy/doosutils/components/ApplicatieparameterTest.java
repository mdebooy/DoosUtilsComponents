/*
 * Copyright (c) 2022 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package eu.debooy.doosutils.components;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class ApplicatieparameterTest {
  private static final  String  PARAMETER = "parameter";
  private static final  String  SLEUTEL   = "sleutel";
  private static final  String  WAARDE    = "waarde";
  private static final  String  WAARDE1   = "waarde1";

  @Test
  public void testLegeInit1() {
    var applicatieparameter = new Applicatieparameter(PARAMETER, SLEUTEL,
                                                      WAARDE);

    assertEquals(PARAMETER, applicatieparameter.getParameter());
    assertEquals(SLEUTEL, applicatieparameter.getSleutel());
    assertEquals(WAARDE, applicatieparameter.getWaarde());
  }

  @Test
  public void testLegeInit2() {
    var applicatieparameter = new Applicatieparameter(PARAMETER, SLEUTEL,
                                                      WAARDE);
    var appparam            = new Applicatieparameter(applicatieparameter);

    assertEquals(PARAMETER, appparam.getParameter());
    assertEquals(SLEUTEL, appparam.getSleutel());
    assertEquals(WAARDE, appparam.getWaarde());
    assertEquals(PARAMETER, applicatieparameter.getParameter());
    assertEquals(SLEUTEL, applicatieparameter.getSleutel());
    assertEquals(WAARDE, applicatieparameter.getWaarde());

    appparam.setWaarde(WAARDE1);

    assertEquals(PARAMETER, appparam.getParameter());
    assertEquals(SLEUTEL, appparam.getSleutel());
    assertEquals(WAARDE1, appparam.getWaarde());
    assertEquals(PARAMETER, applicatieparameter.getParameter());
    assertEquals(SLEUTEL, applicatieparameter.getSleutel());
    assertEquals(WAARDE, applicatieparameter.getWaarde());
  }

  @Test
  public void testSetWaarde() {
    var applicatieparameter = new Applicatieparameter(PARAMETER, SLEUTEL,
                                                      WAARDE);

    applicatieparameter.setWaarde(WAARDE1);

    assertEquals(PARAMETER, applicatieparameter.getParameter());
    assertEquals(SLEUTEL, applicatieparameter.getSleutel());
    assertEquals(WAARDE1, applicatieparameter.getWaarde());
  }

  @Test
  public void testToString() {
    var applicatieparameter = new Applicatieparameter(PARAMETER, SLEUTEL,
                                                      WAARDE);

    assertEquals("Applicatieparameter (parameter=parameter, sleutel=sleutel, "
                  + "waarde=waarde)",
                 applicatieparameter.toString());
  }
}
