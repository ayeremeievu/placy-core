-- Task Resources
ALTER TABLE core.taskResources
    ADD tr_latest_date_imported TIMESTAMP;

ALTER TABLE core.taskResources
    ADD tr_latest_date_processed TIMESTAMP;

UPDATE core.taskResources
SET tr_latest_date_imported = (SELECT CURRENT_TIMESTAMP);

ALTER TABLE core.taskResources
    ALTER COLUMN tr_latest_date_imported SET NOT NULL;

-- Process Resources
ALTER TABLE core.processResources
    ADD pr_latest_date_imported TIMESTAMP;

ALTER TABLE core.processResources
    ADD pr_latest_date_processed TIMESTAMP;

UPDATE core.processResources
SET pr_latest_date_imported = (SELECT CURRENT_TIMESTAMP);

ALTER TABLE core.processResources
    ALTER COLUMN pr_latest_date_imported SET NOT NULL;


