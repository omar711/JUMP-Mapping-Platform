/*
 * The Unified Mapping Platform (JUMP) is an extensible, interactive GUI for
 * visualizing and manipulating spatial features with geometry and attributes.
 *
 * Copyright (C) 2003 Vivid Solutions
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * For more information, contact:
 *
 * Vivid Solutions Suite #1A 2328 Government Street Victoria BC V8T 5G5 Canada
 *
 * (250)385-6040 www.vividsolutions.com
 */
package com.vividsolutions.jump.workbench.ui.plugin;
import com.vividsolutions.jump.util.FileUtil;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.GUIUtil;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
public class SaveProjectAsPlugIn extends AbstractSaveProjectPlugIn {
    public static final FileFilter JUMP_PROJECT_FILE_FILTER = GUIUtil
            .createFileFilter("JUMP Task Files", new String[]{"jmp", "jcs"});
    private JFileChooser fileChooser;
    public void initialize(PlugInContext context) throws Exception {
    }
    private void initFileChooser()
    {
      if (fileChooser != null) return;

      //Don't initialize fileChooser in field declaration -- seems too early
      // because
      //we sometimes get a WindowsFileChooserUI NullPointerException [Jon
      // Aquino 12/10/2003]
      fileChooser = GUIUtil.createJFileChooserWithOverwritePrompting();
      fileChooser.setDialogTitle("Save Task");
      GUIUtil.removeChoosableFileFilters(fileChooser);
      fileChooser.addChoosableFileFilter(JUMP_PROJECT_FILE_FILTER);
      fileChooser.addChoosableFileFilter(GUIUtil.ALL_FILES_FILTER);
      fileChooser.setFileFilter(JUMP_PROJECT_FILE_FILTER);
    }
    public String getName() {
        return "Save Task As";
    }
    public boolean execute(PlugInContext context) throws Exception {
      initFileChooser();
        reportNothingToUndoYet(context);
        if (context.getTask().getProjectFile() != null) {
            fileChooser.setSelectedFile(context.getTask().getProjectFile());
        }
        if (JFileChooser.APPROVE_OPTION != fileChooser.showSaveDialog(context
                .getWorkbenchFrame())) {
            return false;
        }
        File file = fileChooser.getSelectedFile();
        file = FileUtil.addExtensionIfNone(file, "jmp");
        save(context.getTask(), file, context.getWorkbenchFrame());
        return true;
    }
}