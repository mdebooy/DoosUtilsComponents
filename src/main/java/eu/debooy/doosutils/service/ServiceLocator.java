/**
 * Copyright (c) 2009 Marco de Booij
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

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
public final class ServiceLocator {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(ServiceLocator.class);

  private static final  String          CTX_ERROR = "Error in CTX lookup";

  private static final  ServiceLocator  locator   = new ServiceLocator();

  private Context context;

  private ServiceLocator() {
    var env = new Properties();
    env.put(Context.INITIAL_CONTEXT_FACTORY,
            "org.apache.openejb.client.RemoteInitialContextFactory");
    env.put(Context.PROVIDER_URL, "http://127.0.0.1:8080/tomee/ejb");
    try {
      context = new InitialContext(env);
    } catch (NamingException e) {
      LOGGER.error(CTX_ERROR, e);
    }
  }

  public DataSource getDataSource(String jndi) {
    if (jndi == null) {
      throw new IllegalArgumentException(
          "getDataSource: JNDI mag niet null zijn.");
    }
    LOGGER.debug("getDataSource: Zoek JNDI {}", jndi);

    return (DataSource) lookup(jndi);
  }

  public static ServiceLocator getInstance() {
    return locator;
  }

  private void listContext(String string, Context context)
      throws NamingException {
    var pairs = context.list("");
    while (pairs.hasMoreElements()) {
      var pair  = pairs.next();
      LOGGER.debug("{}/{} {}", string, pair.getName(), pair.getClassName());
      var obj  = context.lookup(pair.getName());
      if (obj instanceof Context) {
        var child = (Context) obj;
        listContext(string + "/" + pair.getName(), child);
      }
    }
  }

  /**
   * Deze in method gebruikte method werkt niet meer in Java 11.
   * Dit als gevolg van "JEP 320: Remove the Java EE and CORBA Modules".
   *
   * @param <T>
   * @param clazz
   * @param jndiName
   * @return
   * @deprecated
   */
  @Deprecated(since = "2.2.5", forRemoval = false)
  public <T> T lookup(Class<T> clazz, String jndiName) {
    var bean  = lookup(jndiName);

    return clazz.cast(PortableRemoteObject.narrow(bean, clazz));
  }

  public Object lookup(String jndi) {
    LOGGER.debug("Zoek JNDI: {}", jndi);

    if (LOGGER.isDebugEnabled()) {
      try {
        listContext("", context);
      } catch (NamingException e) {
        LOGGER.error("JNDI: {} [{}]", jndi, e.getLocalizedMessage());
      }
    }

    try {
      var object  = context.lookup(jndi);
      LOGGER.debug("Object: {}", object.getClass().getCanonicalName());
      return object;
    } catch (NamingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
