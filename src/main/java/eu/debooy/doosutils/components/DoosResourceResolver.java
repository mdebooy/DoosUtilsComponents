/*
 * Copyright (c) 2023 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
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

import java.net.URL;
import javax.faces.view.facelets.ResourceResolver;


/**
 * @author Marco de Booij
 */
public class DoosResourceResolver extends ResourceResolver {
  private final String            basePath;
  private final ResourceResolver  parent;

  public DoosResourceResolver(ResourceResolver parent) {
    this.parent   = parent;
    this.basePath = "/META-INF/resources";
  }

  @Override
  public URL resolveUrl(String path) {
    URL url = parent.resolveUrl(path);

    if (null == url) {
      url = getClass().getResource(basePath + path);
    }

    return url;
  }
}
