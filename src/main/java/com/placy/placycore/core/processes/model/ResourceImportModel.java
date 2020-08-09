package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.AbstractDomainModel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author ayeremeiev@netconomy.net
 */
@Entity
@Table(name = "resourceImports")
public class ResourceImportModel extends AbstractDomainModel<Integer> {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       @Column(name = "ri_version", nullable = false)
       private Integer version;

       @Column(name = "ri_result", nullable = true)
       @Enumerated(EnumType.STRING)
       private ResourceImportResultEnum result;

       @OneToMany(mappedBy = "resourceImport", cascade = CascadeType.ALL)
       private List<TaskResourceModel> taskResources;

       @OneToMany(mappedBy = "resourceImport", cascade = CascadeType.ALL)
       private List<ProcessResourceModel> processResources;

       public ResourceImportModel() {
       }

       @Override
       public Integer getPk() {
              return version;
       }

       public Integer getVersion() {
              return version;
       }

       public void setVersion(Integer version) {
              this.version = version;
       }

       public ResourceImportResultEnum getResult() {
              return result;
       }

       public void setResult(ResourceImportResultEnum result) {
              this.result = result;
       }

       public List<TaskResourceModel> getTaskResources() {
              return taskResources;
       }

       public void setTaskResources(List<TaskResourceModel> taskResources) {
              this.taskResources = taskResources;
       }

       public List<ProcessResourceModel> getProcessResources() {
              return processResources;
       }

       public void setProcessResources(List<ProcessResourceModel> processResources) {
              this.processResources = processResources;
       }
}
