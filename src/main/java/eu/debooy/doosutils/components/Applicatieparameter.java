/**
 * Copyright 2014 Marco de Booij
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
package eu.debooy.doosutils.components;

import java.io.Serializable;


/**
 * @author Marco de Booij
 */
public class Applicatieparameter implements Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  parameter;
  private String  sleutel;
  private String  waarde;


  public Applicatieparameter(Applicatieparameter applicatieparameter) {
    parameter = applicatieparameter.parameter;
    sleutel   = applicatieparameter.sleutel;
    waarde    = applicatieparameter.waarde;
  }
  public Applicatieparameter(String parameter, String sleutel, String waarde) {
    this.parameter  = parameter;
    this.sleutel    = sleutel;
    this.waarde     = waarde;
  }

  @Deprecated
  public Object clone() throws CloneNotSupportedException {
    return (Applicatieparameter) super.clone();
  }

  public String getParameter() {
    return parameter;
  }

  public String getSleutel() {
    return sleutel;
  }

  public String getWaarde() {
    return waarde;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  public void setSleutel(String sleutel) {
    this.sleutel = sleutel;
  }

  public void setWaarde(String waarde) {
    this.waarde = waarde;
  }

  public String toString() {
    return "parameter=" + parameter + ", sleutel=" + sleutel
           + ", waarde=" + waarde;
  }
}
