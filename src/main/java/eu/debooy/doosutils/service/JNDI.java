/**
 * Copyright 2012 Marco de Booij
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

import eu.debooy.doosutils.DoosUtils;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
public final class JNDI {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(JNDI.class);

  private JNDI() {}

  public static class JNDINaam {
    private static final  String INTERFACE_SEPARATOR  = "!";
    private static final  String PFIX                 = "java:global";
    private static final  String SEP                  = "/";

    private       String        appNaam;
    private       String        beanNaam;
    private       String        interfaceNaam;
    private       String        interfaceSeparator;
    private final StringBuilder jndi;
    private       String        moduleNaam;
    private       String        separator;
    private       String        prefix;

    public JNDINaam() {
      appNaam             = (String) ServiceLocator.getInstance()
                                                   .lookup("java:app/AppName");
      interfaceSeparator  = INTERFACE_SEPARATOR;
      jndi                = new StringBuilder();
      prefix              = PFIX;
      separator           = SEP;
    }

    /**
     * Method alleen maakt gebruik van deprecated method.
     * 
     * @param <T>
     * @param clazz
     * @return
     * @deprecated
     */
    @Deprecated (since = "2.2.5", forRemoval = false)
    public <T> T locate(Class<T> clazz) {
      return ServiceLocator.getInstance().lookup(clazz, asString());
    }

    public Object locate() {
      return ServiceLocator.getInstance().lookup(asString());
    }

    public <T> T getContextualInstance(final BeanManager manager,
                                              final Class<T> type) {
      T       result  = null;
      Bean<T> bean    = (Bean<T>) manager.resolve(manager.getBeans(type));
      if (bean != null) {
        CreationalContext<T> context = manager.createCreationalContext(bean);
        if (context != null) {
          result  = (T) manager.getReference(bean, type, context);
        }
      }
      return result;
    }

    public JNDINaam metAppNaam(String appNaam) {
      this.appNaam            = appNaam;

      return this;
    }

    public JNDINaam metBean(Class<?> beanClass) {
      return metBeanNaam(computeBeanNaam(beanClass));
    }

    public JNDINaam metBeanNaam(String beanNaam) {
      this.beanNaam           = beanNaam;

      return this;
    }

    public JNDINaam metInterface(Class<?> interfaceClass) {
      return metInterfaceNaam(interfaceClass.getName());
    }

    public JNDINaam metInterfaceNaam(String interfaceNaam) {
      this.interfaceNaam      = interfaceNaam;

      return this;
    }

    public JNDINaam metInterfaceSeparator(String interfaceSeparator) {
      this.interfaceSeparator = interfaceSeparator;

      return this;
    }

    public JNDINaam metModuleNaam(String moduleNaam) {
      this.moduleNaam         = moduleNaam;

      return this;
    }

    public JNDINaam metPrefix(String prefix) {
      this.prefix             = prefix;

      return this;
    }

    public JNDINaam metSeparator(String separator) {
      this.separator          = separator;

      return this;
    }

    String computeBeanNaam(Class<?> beanClass) {
      var stateless = beanClass.getAnnotation(Stateless.class);
      if (null != stateless
          && DoosUtils.isNotBlankOrNull(stateless.name())) {
        return stateless.name();
      }

      var stateful  = beanClass.getAnnotation(Stateful.class);
      if (null != stateful
          && DoosUtils.isNotBlankOrNull(stateful.name())) {
        return stateful.name();
      }

      var singleton = beanClass.getAnnotation(Singleton.class);
      if (null != singleton
          && DoosUtils.isNotBlankOrNull(singleton.name())) {
        return singleton.name();
      }

      return beanClass.getSimpleName();
    }

    public String asString() {
      jndi.append(prefix).append(separator);
      if (DoosUtils.isNotBlankOrNull(appNaam)) {
        jndi.append(appNaam).append(separator);
      }
      if (DoosUtils.isNotBlankOrNull(moduleNaam)) {
        jndi.append(moduleNaam).append(separator);
      }
      if (DoosUtils.isNotBlankOrNull(beanNaam)) {
        jndi.append(beanNaam);
      }
      if (DoosUtils.isNotBlankOrNull(interfaceNaam)) {
        jndi.append(interfaceSeparator).append(interfaceNaam);
      }
      LOGGER.debug("JNDI: {}", jndi);

      return jndi.toString();
    }
  }
}
