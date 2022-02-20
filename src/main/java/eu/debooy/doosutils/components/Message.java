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

  private final String    attribute;
  private final String    msg;
  private final Object[]  params;
  private final String    severity;

  private Message(Builder builder) {
    attribute = builder.getAttribute();
    msg       = builder.getMessage();
    params    = builder.getParams();
    severity  = builder.getSeverity();
  }

  public static final class Builder {
    private String    attribute;
    private String    msg;
    private Object[]  params    = new Object[0];
    private String    severity;

    public Message build() {
      return new Message(this);
    }

    public String getAttribute() {
      return attribute;
    }

    public String getMessage() {
      return msg;
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
      msg             = message;
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

  public String getAttribute() {
    return attribute;
  }

  public String getMessage() {
    return msg;
  }

  public Object[] getParams() {
    return Arrays.copyOf(params, params.length);
  }

  public String getSeverity() {
    return severity;
  }

  @Override
  public String toString() {
    var sb  = new StringBuilder();

    sb.append("Message (")
      .append("attribute=").append(getAttribute())
      .append(", message=").append(getMessage())
      .append(", params=").append(getParams())
      .append(", severity=").append(getSeverity())
      .append(")");

    return sb.toString();
  }
}
