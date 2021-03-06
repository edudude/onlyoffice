
/*
 * Copyright (C) 2003-2016 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.onlyoffice.webui;

import org.exoplatform.ecm.webui.component.explorer.UIJCRExplorer;
import org.exoplatform.ecm.webui.component.explorer.UIJcrExplorerContainer;
import org.exoplatform.ecm.webui.utils.Utils;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.core.UIApplication;

import java.util.Map;

import javax.jcr.Node;

/**
 * Created by The eXo Platform SAS
 * 
 * @author <a href="mailto:pnedonosko@exoplatform.com">Peter Nedonosko</a>
 * @version $Id: FileFilter.java 00000 Mar 2, 2016 pnedonosko $
 * 
 */
public class FileFilter extends org.exoplatform.webui.ext.filter.impl.FileFilter {

  /**
   * 
   */
  public FileFilter() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(Map<String, Object> context) throws Exception {
    if (context == null) {
      return true;
    }
    
    if (mimeTypes == null || mimeTypes.isEmpty()) {
      return true;
    }
    
    Node contextNode = (Node) context.get(Node.class.getName());
    if (contextNode == null) {
      UIJCRExplorer uiExplorer = (UIJCRExplorer) context.get(UIJCRExplorer.class.getName());
      if (uiExplorer != null) {
        contextNode = uiExplorer.getCurrentNode();
      }
      if (contextNode == null) {
        WebuiRequestContext reqContext = WebuiRequestContext.getCurrentInstance();
        UIApplication uiApp = reqContext.getUIApplication();
        UIJcrExplorerContainer jcrExplorerContainer = uiApp.getChild(UIJcrExplorerContainer.class);
        if (jcrExplorerContainer != null) {
          UIJCRExplorer jcrExplorer = jcrExplorerContainer.getChild(UIJCRExplorer.class);
          contextNode = jcrExplorer.getCurrentNode();
        }
      }
    }

    if (contextNode != null && contextNode.isNodeType(Utils.NT_FILE)) {
      String mimeType = contextNode.getNode(Utils.JCR_CONTENT).getProperty(Utils.JCR_MIMETYPE).getString();
      context.put(Utils.MIME_TYPE, mimeType);
      return super.accept(context);
    }
    return false;
  }

}
