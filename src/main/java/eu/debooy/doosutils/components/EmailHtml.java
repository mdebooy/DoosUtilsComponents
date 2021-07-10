/**
 * Copyright 2018 Marco de Booij
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

import java.util.Map;


/**
 * @author Marco de Booij
 */
public class EmailHtml {
  public static final String  BACKGROUND    = " background:";
  public static final String  FOREGROUND    = " color:";
  public static final String  HX_BACKGROUND = "titel.background";
  public static final String  HX_FOREGROUND = "titel.foreground";
  public static final String  TD_BACKGROUND = "row.background";
  public static final String  TD_FOREGROUND = "row.foreground";
  public static final String  TH_BACKGROUND = "columnheader.background";
  public static final String  TH_FOREGROUND = "columnheader.foreground";
  public static final String  PUNTKOMMA     = ";";
  public static final String  STRING_END    = "\"";
  public static final String  STYLE_START   = " style=\"";
  public static final String  TABLE_START   = "<table border=\"0\">";

  private String  hx  = "";
  private String  td  = "";
  private String  th  = "";

  protected EmailHtml() {}

  public EmailHtml(Map<String, String> tags) {
    if (tags.containsKey(HX_BACKGROUND)) {
      hx  = hx + BACKGROUND + tags.get(HX_BACKGROUND) + PUNTKOMMA;
    }
    if (tags.containsKey(HX_FOREGROUND)) {
      hx  = hx + FOREGROUND + tags.get(HX_FOREGROUND) + PUNTKOMMA;
    }
    if (null != hx) {
      hx  = STYLE_START + hx.trim() + STRING_END;
    }
    if (tags.containsKey(TD_BACKGROUND)) {
      td  = td + BACKGROUND + tags.get(TD_BACKGROUND) + PUNTKOMMA;
    }
    if (tags.containsKey(TD_FOREGROUND)) {
      td  = td + FOREGROUND + tags.get(TD_FOREGROUND) + PUNTKOMMA;
    }
    if (null != hx) {
      td  = STYLE_START + td.trim() + STRING_END;
    }
    if (tags.containsKey(TH_BACKGROUND)) {
      th  = th + BACKGROUND + tags.get(TH_BACKGROUND) + PUNTKOMMA;
    }
    if (tags.containsKey(TH_FOREGROUND)) {
      th  = th + FOREGROUND + tags.get(TH_FOREGROUND) + PUNTKOMMA;
    }
    if (null != hx) {
      th  = STYLE_START + th.trim() + STRING_END;
    }
  }

  public String getH1(String tekst) {
    return "<h1" + hx + ">" + tekst + "</h1>";
  }

  public String getH2(String tekst) {
    return "<h2" + hx + ">" + tekst + "</h2>";
  }

  public String getH3(String tekst) {
    return "<h3" + hx + ">" + tekst + "</h3>";
  }

  public String getTable() {
    return TABLE_START;
  }

  public String getTd(String tekst) {
    return "<td" + td + ">" + tekst + "</td>";
  }

  public String getTh(String tekst) {
    return "<th" + th + ">" + tekst + "</th>";
  }
}
