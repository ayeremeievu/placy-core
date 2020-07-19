ALTER TABLE core.taskResources
    ADD CONSTRAINT tr_resource_unique_constraint UNIQUE (tr_resource_name);
