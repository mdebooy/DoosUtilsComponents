/*
 * Copyright (c) 2021 Marco de Booij
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

import java.util.Arrays;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class MessageTest {
  private static final  String ATTR = "attr";
  private static final  String MESS = "mess";
  private static final  String PAR1 = "param1";
  private static final  String PAR2 = "param2";

  @Test
  public void testBuildAttribute() {
    var message = new Message.Builder().setAttribute(ATTR).build();

    assertEquals(ATTR, message.getAttribute());
    assertNull(message.getMessage());
    assertEquals(0, message.getParams().length);
    assertNull(message.getSeverity());
  }

  @Test
  public void testBuildMessage() {
    var message = new Message.Builder().setMessage(MESS).build();

    assertNull(message.getAttribute());
    assertEquals(MESS, message.getMessage());
    assertEquals(0, message.getParams().length);
    assertNull(message.getSeverity());
  }

  @Test
  public void testBuildParams() {
    var params  = new Object[]{PAR1, PAR2};
    var message = new Message.Builder().setParams(params).build();

    assertNull(message.getAttribute());
    assertNull(message.getMessage());
    assertArrayEquals(params, message.getParams());
    assertNull(message.getSeverity());

    params[0] = PAR1.toUpperCase();
    assertNotEquals(Arrays.asList(params), Arrays.asList(message.getParams()));
  }

  @Test
  public void testBuildSeverity() {
    var message = new Message.Builder().setSeverity(Message.INFO).build();

    assertNull(message.getAttribute());
    assertNull(message.getMessage());
    assertEquals(0, message.getParams().length);
    assertEquals(Message.INFO, message.getSeverity());
  }

  @Test
  public void testToString() {
    var params  = new Object[]{PAR1, PAR2};
    var message = new Message.Builder()
                             .setAttribute(ATTR)
                             .setMessage(MESS)
                             .setParams(params)
                             .setSeverity(Message.INFO).build();

    assertEquals("Message (attribute=attr, message=mess, params=[param1, "
                  + "param2], severity=info)",
                 message.toString());
  }
}
