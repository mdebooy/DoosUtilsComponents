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

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class MessageTest {
  private static final  String ATTR = "attr";
  private static final  String MESS = "mess";
  private static final  String PAR1 = "param1";
  private static final  String PAR2 = "param2";

  @Test
  public void testBuildAttribute() {
    Message message = new Message.Builder()
                                 .setAttribute(ATTR).build();

    assertEquals(ATTR, message.getAttribute());
  }

  @Test
  public void testBuildMessage() {
    Message message = new Message.Builder()
                                 .setMessage(MESS).build();

    assertEquals(MESS, message.getMessage());
  }

  @Test
  public void testBuildParams() {
    Object[]  params  = new Object[]{PAR1, PAR2};
    Message   message = new Message.Builder()
                                   .setParams(params).build();

    Assert.assertArrayEquals(params, message.getParams());

    params[0] = PAR1.toUpperCase();
    Assert.assertThat(params, IsNot.not(IsEqual.equalTo(message.getParams())));

    Object[]  paramsc = message.getParams();
    Assert.assertArrayEquals(paramsc, message.getParams());
  }

  @Test
  public void testBuildSeverity() {
    Message message = new Message.Builder()
                                 .setSeverity(Message.INFO).build();

    assertEquals(Message.INFO, message.getSeverity());
  }
}
