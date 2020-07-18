ALTER TABLE core.processResources
    ADD CONSTRAINT pr_resource_unique_constraint UNIQUE (pr_resource);
