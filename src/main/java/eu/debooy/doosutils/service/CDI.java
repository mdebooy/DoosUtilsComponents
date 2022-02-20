/**
 * Copyright 2013 Marco de Booij
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
package eu.debooy.doosutils.service;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
public final class CDI {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(CDI.class.getName());

  private CDI() {}

  public static BeanManager getBeanManager() {
    BeanManager beanManager = null;

    try {
      final var initialContext = new InitialContext();
      beanManager =
          (BeanManager) initialContext.lookup("java:comp/BeanManager");
    } catch(NamingException e) {
      LOGGER.error("BeanManager niet gevonden ({}).", e.getLocalizedMessage());
    }

    return beanManager;
  }

  public static Set<Bean<?>> getBeans() {
    var beanManager = getBeanManager();

    if (null == beanManager) {
      return new HashSet<>();
    }

    return beanManager.getBeans(Object.class, new AnnotationLiteral<Any>() {});
  }

  public static <T> T getBean(Class<T> type) {
    var beanManager = getBeanManager();
    if (null == beanManager) {
      return null;
    }

    Bean<T>               bean              =
        (Bean<T>) beanManager.resolve(beanManager.getBeans(type));
    CreationalContext<T>  creationalContext =
        beanManager.createCreationalContext(bean);

    return (T) beanManager.getReference(bean, type, creationalContext);
  }

  public static <T> T getBean(String naam) {
    var beanManager = getBeanManager();
    if (null == beanManager) {
      return null;
    }

    Bean<T> bean  = (Bean<T>) beanManager.resolve(beanManager.getBeans(naam));
    CreationalContext<T>  creationalContext =
        beanManager.createCreationalContext(bean);

    return (T) beanManager.getReference(bean, (Type) bean.getBeanClass(),
                                        creationalContext);
  }
}
