/**
 * Copyright 2017 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
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

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELResolver;


/**
 * @author Marco de Booij
 */
public class EmptyToNullStringELResolver extends ELResolver {
  @Override
  public Class<?> getCommonPropertyType(ELContext context, Object base) {
    return String.class;
  }

  @Override
  public Object convertToType(ELContext context, Object value,
                              Class<?> targetType) {
    if (value == null && targetType == String.class) {
      context.setPropertyResolved(true);
    }

    return value;
  }

  @Override
  public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context,
                                                           Object base) {
    return null;
  }

  @Override
  public Class<?> getType(ELContext context, Object base, Object property) {
    return null;
  }

  @Override
  public Object getValue(ELContext context, Object base, Object property) {
    return null;
  }

  @Override
  public boolean isReadOnly(ELContext context, Object base, Object property) {
    return true;
  }

  @Override
  public void setValue(ELContext context, Object base, Object property,
                       Object value) {
    // Geen implementatie nodig.
  }
}
