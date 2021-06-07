/**
 * Copyright (c) 2015 Marco de Booij
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

import java.util.Arrays;


/**
 * @author Marco de Booij
 */
public class Message {
  public static final String  ERROR   = "error";
  public static final String  FATAL   = "fatal";
  public static final String  INFO    = "info";
  public static final String  WARNING = "warning";

  private String    attribute;
  private String    message;
  private Object[]  params;
  private String    severity;

  private Message(Builder builder) {
    attribute = builder.getAttribute();
    message   = builder.getMessage();
    params    = builder.getParams();
    severity  = builder.getSeverity();
  }

  public static final class Builder {
    private String    attribute;
    private String    message;
    private Object[]  params    = new Object[0];
    private String    severity;

    public Builder() {}

    public Message build() {
      return new Message(this);
    }

    public String getAttribute() {
      return attribute;
    }

    public String getMessage() {
      return message;
    }

    public Object[] getParams() {
      return Arrays.copyOf(params, params.length);
    }

    public String getSeverity() {
      return severity;
    }

    public Builder setAttribute(String attribute) {
      this.attribute  = attribute;
      return this;
    }

    public Builder setMessage(String message) {
      this.message    = message;
      return this;
    }

    public Builder setParams(Object[] params) {
      this.params     = Arrays.copyOf(params, params.length);
      return this;
    }

    public Builder setSeverity(String severity) {
      this.severity   = severity;
      return this;
    }
  }

  @Deprecated
  public Message(String severity, String message, Object... params) {
    super();
    this.severity = severity;
    this.params   = params.clone();
    this.message  = message;
  }

  public String getAttribute() {
    return attribute;
  }

  public String getMessage() {
    return message;
  }

  public Object[] getParams() {
    return Arrays.copyOf(params, params.length);
  }

  public String getSeverity() {
    return severity;
  }

  @Deprecated
  public void setMessage(String message) {
    this.message  = message;
  }

  @Deprecated
  public void setParams(Object... params) {
    this.params = params.clone();
  }

  @Deprecated
  public void setSeverity(String severity) {
    this.severity = severity;
  }

  @Override
  public String toString() {
    return "attribute="+ attribute + ", message=" + message
           + ", params=" + Arrays.toString(params)
           + ", severity=" + severity;
  }
}
