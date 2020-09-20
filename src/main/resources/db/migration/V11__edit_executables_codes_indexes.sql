alter TABLE core.tasks drop CONSTRAINT t_code_unq_constraint;
alter TABLE core.processes drop CONSTRAINT p_code_unq_constraint;

alter TABLE core.processParameters drop CONSTRAINT pp_code_unq_constraint;
alter table core.processParameters
    add constraint pp_code_process_unq_constraint unique (pp_code, pp_process);

alter TABLE core.processSteps drop CONSTRAINT ps_code_unq_constraint;
alter TABLE core.processSteps
    add constraint ps_code_process_pk_unq_constraint unique (ps_code, ps_process_pk);