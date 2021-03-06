/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.workflow.simple.definition.form;

import java.util.ArrayList;
import java.util.List;

import org.activiti.workflow.simple.exception.SimpleWorkflowException;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * A group of {@link FormPropertyDefinition}s which belong together.
 * 
 * @author Frederik Heremans
 */
public class FormPropertyGroup implements FormPropertyDefinitionContainer {
	
	protected String title;
	protected String id;
	protected String type;
	
	protected List<FormPropertyDefinition> formPropertyDefinitions = new ArrayList<FormPropertyDefinition>();
	
	public FormPropertyGroup() {
		
  }
	
	public FormPropertyGroup(String id, String type, String title) {
		this.id = id;
		this.type = type;
		this.title = title;
  }
	
	@JsonSerialize(contentAs=FormPropertyDefinition.class)
  @JsonProperty(value="formProperties")
	public List<FormPropertyDefinition> getFormPropertyDefinitions() {
	  return formPropertyDefinitions;
  }
	
	@Override
	public void addFormProperty(FormPropertyDefinition definition) {
		formPropertyDefinitions.add(definition);
	}
	@Override
	public boolean removeFormProperty(FormPropertyDefinition definition) {
		return formPropertyDefinitions.remove(definition);
	}
	
	public void setFormPropertyDefinitions(List<FormPropertyDefinition> formPropertyDefinitions) {
	  this.formPropertyDefinitions = formPropertyDefinitions;
  }
	
	public FormPropertyGroup addFormPropertyDefinition(FormPropertyDefinition definition) {
		if(definition == null) {
			throw new SimpleWorkflowException("Definition to add cannot be null");
		}
		
		formPropertyDefinitions.add(definition);
		return this;
	}
	
	public String getType() {
	  return type;
  }
	public void setType(String type) {
	  this.type = type;
  }
	public void setTitle(String title) {
	  this.title = title;
  }
	public String getTitle() {
	  return title;
  }
	public String getId() {
	  return id;
  }
	public void setId(String id) {
	  this.id = id;
  }
}
