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

import eu.debooy.doosutils.errorhandling.exception.base.DoosError;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
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

  private static final String CTX_ERROR = "Error in CTX lookup";

  private static  Context         context;
//  private static  List<Context>   contexts  = new ArrayList<Context>();
  private static  ServiceLocator  locator = new ServiceLocator();

  private ServiceLocator() {
    Properties  env = new Properties();
    env.put(Context.INITIAL_CONTEXT_FACTORY,
            "org.apache.openejb.client.RemoteInitialContextFactory");
    env.put(Context.PROVIDER_URL, "http://127.0.0.1:8080/tomee/ejb");
//    env.put(Context.PROVIDER_URL, "ejbd://localhost:4201/");
    try {
      context = new InitialContext(env);
//      contexts.add(context);
    } catch (NamingException e) {
      LOGGER.error(CTX_ERROR, e);
    }
  }

//  public ServiceLocator addContext(Properties env) {
//    if ((env == null) || (!(env.containsKey(Context.PROVIDER_URL)))) {
//      throw new IllegalArgumentException(
//          "addContext: Context environment mag niet null zijn en moet "
//          + "minstens 1 'provider URL' hebben.");
//    }
//    try {
//      Context context = new InitialContext(env);
//      contexts.add(context);
//    } catch (NamingException ne) {
//      LOGGER.error(CTX_ERROR, ne);
//    }
//
//    return locator;
//  }

//  public ServiceLocator forceInstance(Properties env) {
//    LOGGER.warn("Default InitialContext wordt overschreven.");
//    if ((env == null) || (!(env.containsKey(Context.PROVIDER_URL)))) {
//      throw new IllegalArgumentException(
//          "forceInstance: Context environment mag niet null zijn en moet "
//          + "minstens 1 'provider URL' hebben.");
//    }
//    try {
//      Context initialContext = new InitialContext(env);
//      if (contexts.size() > 0) {
//        contexts.set(0, initialContext);
//      } else {
//        contexts.add(initialContext);
//      }
//    } catch (NamingException ne) {
//      LOGGER.error(CTX_ERROR, ne);
//    }
//
//    return locator;
//  }

  public DataSource getDataSource(String jndi) {
    if (jndi == null) {
      throw new IllegalArgumentException(
          "getDataSource: JNDI mag niet null zijn.");
    }
    LOGGER.debug("getDataSource: Zoek JNDI " + jndi);
    DataSource datasource = (DataSource) lookup(jndi);
    if (datasource == null) {
      LOGGER.error("getDataSource: Kan geen datasource vinden met JNDI="
                   + jndi + " in geen enkele context.");
      throw new ServiceLocatorException(DoosError.OBJECT_NOT_FOUND,
                  DoosLayer.BUSINESS, "Kan geen datasource vinden met JNDI="
                  + jndi + " in geen enkele context.");
    }
    LOGGER.debug("getDataSource: Gevonden.");

    return datasource;
  }

  public static ServiceLocator getInstance() {
    return locator;
  }

  private void listContext(String string, Context context)
      throws NamingException {
    NamingEnumeration<NameClassPair>  pairs = context.list("");
    for (; pairs.hasMoreElements();) {
      NameClassPair pair  = pairs.next();
      LOGGER.debug(string + "/" + pair.getName() + " " + pair.getClassName());
      Object obj  = context.lookup(pair.getName());
      if (obj instanceof Context) {
        Context child = (Context) obj;
        listContext(string + "/" + pair.getName(), child);
      }
    }
  }

  public <T> T lookup(Class<T> clazz, String jndiName) {
    Object  bean  = lookup(jndiName);

    // TODO Zoek een betere oplossing. Bestaat niet meer in Java 11.
    return clazz.cast(PortableRemoteObject.narrow(bean, clazz));
  }

  public Object lookup(String jndi) {
    LOGGER.debug("Zoek JNDI: " + jndi);
//    for (Context context : contexts) {
      if (LOGGER.isDebugEnabled()) {
        try {
          listContext("", context);
        } catch (NamingException e) {
          LOGGER.error("JNDI: " + jndi + " [" + e.getMessage() + "]");
        }
      }
      try {
        Object object = context.lookup(jndi);
        LOGGER.debug("Object: " + object.getClass().getCanonicalName());
        return object;
      } catch (NamingException e) {
        LOGGER.error("JNDI: " + jndi + " [" + e.getMessage() + "]");
        throw new IllegalArgumentException(e);
      }
//    }

//    return null;
  }

//  public void reset() {
//    contexts  = new ArrayList<Context>();
//    try {
//      contexts.add(new InitialContext());
//    } catch (NamingException e) {
//      LOGGER.error(CTX_ERROR, e);
//    }
//    locator   = new ServiceLocator();
//  }
}
