/*
 * Copyright (c) 2023 Marco de Booij
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

package eu.debooy.doosutils;

import eu.debooy.doosutils.components.Message;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco de Booij
 */
public final class ComponentsUtils {
  private ComponentsUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Message> objectIsNull(String obj) {
    List<Message> fouten  = new ArrayList<>();

    fouten.add(new Message.Builder()
                          .setAttribute(obj)
                          .setSeverity(Message.ERROR)
                          .setMessage(PersistenceConstants.NULL)
                          .setParams(new Object[]{obj})
                          .build());

    return fouten;
  }
}
