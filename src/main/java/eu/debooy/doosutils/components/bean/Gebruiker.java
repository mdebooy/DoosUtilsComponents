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
package eu.debooy.doosutils.components.bean;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.jaas.UserPrincipal;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.security.Principal;
import java.util.Locale;
import java.util.TimeZone;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Deze class wordt gebruikt om de versie informatie van een applicatie te laten
 * zien. Tevens bevat het de locale, de user-id en de volledige naam van de
 * gebruiker zodat niet steeds deze informatie uit de request informatie gehaald
 * moet worden.
 * 
 * @author Marco de Booij
 */
@Named
@SessionScoped
public class Gebruiker implements Serializable {
  private static final  long      serialVersionUID      = 1L;

  private static final  Logger    LOGGER                =
      LoggerFactory.getLogger(Gebruiker.class);
  private static final  String    DATE_UNKNOWN      = "UNKNOWN";
  private static final  String    MANIFEST          = "/META-INF/MANIFEST.MF";
  private static final  String    VERSION_UNSTABLE  = "UNSTABLE";
  private static final  String    WEB_INF           = "/WEB-INF/lib";

  private static  String  versie;
  private static  String  bouwdatum;

  private String  email     = null;      
  private Locale  locale    = null;
  private String  userId    = null;
  private String  userName  = null;

  static {
    buildDetails();
  }

  public Gebruiker() {
    LOGGER.trace("Gebruiker gemaakt.");
  }

  private static void buildDetails() {
    try {
      URL       manifestUrl     = null;
      Manifest  manifest        = null;
      String    classContainer  = Gebruiker.class.getProtectionDomain()
                                           .getCodeSource()
                                           .getLocation().toString();

      if (classContainer.indexOf(WEB_INF) >= 0) {
        classContainer  =
          classContainer.substring(0, classContainer.indexOf(WEB_INF));
        manifestUrl     =
          new URL((new StringBuilder()).append(classContainer)
                                       .append(MANIFEST)
                                       .toString());

      } else {
        manifestUrl     =
          new URL((new StringBuilder()).append("jar:")
                                       .append(classContainer)
                                       .append("!").append(MANIFEST)
                                       .toString());

      }
      manifest        = new Manifest(manifestUrl.openStream());

      Attributes  attr  = manifest.getMainAttributes();
      versie    = attr.getValue("Implementation-Version");
      bouwdatum = attr.getValue("Build-Time");
    } catch (IOException e) {
      versie    = VERSION_UNSTABLE;
      bouwdatum = DATE_UNKNOWN;
    }
  }

  public String getBouwdatum() {
    return bouwdatum;
  }

  public String getVersie() {
    return versie;
  }

  public String getEmail() {
    if (null == email) {
      Principal principal = FacesContext.getCurrentInstance()
                                        .getExternalContext()
                                        .getUserPrincipal();
      if (null != principal && principal instanceof UserPrincipal) {
        email  = DoosUtils.nullToEmpty(((UserPrincipal) principal).getEmail());
      }
    }

    return email;
  }

  public Locale getLocale() {
    if (null == locale) {
      locale  = FacesContext.getCurrentInstance()
                            .getExternalContext().getRequestLocale();
    }

    return locale;
  }

  // TODO Zorg voor de TimeZone van de gebruiker.
  public TimeZone getTimeZone() {
    return TimeZone.getDefault();
  }

  public String getUserId() {
    if (null == userId) {
      userId  = DoosUtils.nullToEmpty(FacesContext.getCurrentInstance()
                                                  .getExternalContext()
                                                  .getRemoteUser());
    }

    return userId;
  }

  public String getUserName() {
    if (null == userName) {
      Principal principal = FacesContext.getCurrentInstance()
                                        .getExternalContext()
                                        .getUserPrincipal();
      if (null != principal && principal instanceof UserPrincipal) {
        userName  = DoosUtils
            .nullToValue(((UserPrincipal) principal).getVolledigeNaam(),
                         getUserId());
      }
    }

    return userName;
  }

  public void setLocale(Locale locale) {
    this.locale   = locale;
  }

  public void setUserId(String userId) {
    this.userId   = userId;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
