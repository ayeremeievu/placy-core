DROP TABLE IF EXISTS core.resourceImports;
CREATE TABLE core.resourceImports
(
    ri_version INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    createdAt  timestamp    not null,
    updatedAt  timestamp    not null,
    ri_result  VARCHAR(255),
    PRIMARY KEY (ri_version)
);

ALTER TABLE core.taskResources
    ADD tr_resource_import_version INT NOT NULL;

ALTER TABLE core.taskResources
    ADD CONSTRAINT tr_resource_name_import_unq_constraint
        UNIQUE (r_resource_name, tr_resource_import_version);

ALTER TABLE core.taskResources
    ADD CONSTRAINT tr_resource_import_version_fk
        FOREIGN KEY (tr_resource_import_version)
        REFERENCES core.resourceImports(ri_version);

ALTER TABLE core.processResources
    ADD pr_resource_import_version INT NOT NULL;

ALTER TABLE core.processResources
    ADD CONSTRAINT pr_resource_name_import_unq_constraint
        UNIQUE (r_resource_name, pr_resource_import_version);

ALTER TABLE core.processResources
    ADD CONSTRAINT pr_resource_import_version_fk
        FOREIGN KEY (pr_resource_import_version)
        REFERENCES core.resourceImports(ri_version);
