/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.activiti.explorer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.explorer.ui.MainLayout;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

/**
 * @author Joram Barrez
 */
public class ExplorerApplication extends Application implements HttpServletRequestListener {

  private static final long serialVersionUID = -8923370280251348552L;
  
  private static ThreadLocal<ExplorerApplication> current = new ThreadLocal<ExplorerApplication>();
  
  protected Window mainWindow;
  protected MainLayout mainLayout;

  public void init() {
    
    // Demo
    setUser(ProcessEngines.getDefaultProcessEngine().getIdentityService()
             .createUserQuery().userId("kermit").singleResult());
    ProcessEngines.getDefaultProcessEngine().getIdentityService().setAuthenticatedUserId("kermit");
    // Demo
    
    // init window
    mainWindow = new Window("My pretty Vaadin Application");
    setMainWindow(mainWindow);
    setTheme(Constants.THEME);

    // init general look and feel
    mainLayout = new MainLayout(this); 
    mainWindow.setContent(mainLayout);
  }
  
  // View management
  
  public void switchView(Component component) {
    mainLayout.addComponent(component, Constants.LOCATION_CONTENT);
  }
  
  public static ExplorerApplication getCurrent() {
    return current.get();
  }
  
  public void showPopupWindow(Window window) {
    getMainWindow().addWindow(window);
  }
  
  // HttpServletRequestListener
  
  public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {
    // Set current application object as thread-local to make it easy accessible
    current.set(this);
    
    // Set thread-local userid of logged in user (needed for Activiti user logic)
    User user = (User) getUser();
    if (user != null) {
      Authentication.setAuthenticatedUserId("kermit");
    }
  }
  
  public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
    // Clean up thread-locals
    current.remove();
    Authentication.setAuthenticatedUserId(null);
  }
  
}