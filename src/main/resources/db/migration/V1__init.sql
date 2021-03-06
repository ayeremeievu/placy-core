CREATE SCHEMA IF NOT EXISTS core;


CREATE TABLE core.conditions (
                                 pk varchar(255) not null,
                                 createdAt timestamp not null,
                                 updatedAt timestamp not null,
                                 c_code varchar(255) not null,
                                 c_runnerBean varchar(255) not null,
                                 primary key (pk)
);

create table core.delegatingTaskParameterValues (
                                                    pk varchar(255) not null,
                                                    createdAt timestamp not null,
                                                    updatedAt timestamp not null,
                                                    dtpv_processParameter_pk varchar(255) not null,
                                                    dtpv_processStep_pk varchar(255) not null,
                                                    dtpv_taskParameter_pk varchar(255) not null,
                                                    primary key (pk)
);

create table core.predefinedTaskParameterValues (
                                                    pk varchar(255) not null,
                                                    createdAt timestamp not null,
                                                    updatedAt timestamp not null,
                                                    ptpv_value varchar(255) not null,
                                                    ptpv_processStep_pk varchar(255) not null,
                                                    ptpv_taskParameter_pk varchar(255) not null,
                                                    primary key (pk)
);

create table core.processes (
                                pk varchar(255) not null,
                                createdAt timestamp not null,
                                updatedAt timestamp not null,
                                p_code varchar(255) not null,
                                p_name varchar(255),
                                primary key (pk)
);

create table core.processInstances (
                                       pk varchar(255) not null,
                                       createdAt timestamp not null,
                                       updatedAt timestamp not null,
                                       pi_code varchar(255) not null,
                                       pi_finishDate timestamp,
                                       pi_startDate timestamp,
                                       pi_status varchar(255) not null,
                                       pi_process_pk varchar(255) not null,
                                       primary key (pk)
);

create table core.processParameters (
                                        pk varchar(255) not null,
                                        createdAt timestamp not null,
                                        updatedAt timestamp not null,
                                        pp_code varchar(255) not null,
                                        pp_defaultValue varchar(255),
                                        pp_isRequired boolean not null,
                                        pp_process varchar(255),
                                        primary key (pk)
);

create table core.processParameterValues (
                                             pk varchar(255) not null,
                                             createdAt timestamp not null,
                                             updatedAt timestamp not null,
                                             ppv_value varchar(255) not null,
                                             ppv_parameter_pk varchar(255) not null,
                                             pi_processInstance_pk varchar(255) not null,
                                             primary key (pk)
);

create table core.processResources (
                                       pk varchar(255) not null,
                                       createdAt timestamp not null,
                                       updatedAt timestamp not null,
                                       r_resource_name varchar(255) not null,
                                       r_resource_content text not null,
                                       r_resource_checksum varchar(255) not null,
                                       pr_process_pk varchar(255),
                                       primary key (pk)
);

create table core.processStepInstances (
                                           pk varchar(255) not null,
                                           createdAt timestamp not null,
                                           updatedAt timestamp not null,
                                           psi_code varchar(255) not null,
                                           psi_finishDate timestamp,
                                           psi_processStepResult varchar(255),
                                           psi_startDate timestamp,
                                           psi_processInstance_pk varchar(255),
                                           psi_processStep_pk varchar(255),
                                           psi_taskInstance_pk varchar(255),
                                           primary key (pk)
);

create table core.processSteps (
                                   pk varchar(255) not null,
                                   createdAt timestamp not null,
                                   updatedAt timestamp not null,
                                   ps_code varchar(255) not null,
                                   ps_order int4 not null,
                                   ps_condition_pk varchar(255),
                                   ps_process_pk varchar(255) not null,
                                   ps_task_pk varchar(255) not null,
                                   primary key (pk)
);

create table core.taskInstances (
                                    pk varchar(255) not null,
                                    createdAt timestamp not null,
                                    updatedAt timestamp not null,
                                    ti_code varchar(255) not null,
                                    ti_finishDate timestamp,
                                    ti_startDate timestamp,
                                    ti_status varchar(255) not null,
                                    ti_task_pk varchar(255) not null,
                                    primary key (pk)
);

create table core.taskParameterValues (
                                          pk varchar(255) not null,
                                          createdAt timestamp not null,
                                          updatedAt timestamp not null,
                                          tpv_value varchar(255) not null,
                                          tpv_parameter_pk varchar(255) not null,
                                          tpv_taskInstance_pk varchar(255) not null,
                                          primary key (pk)
);

create table core.taskResources (
                                    pk varchar(255) not null,
                                    createdAt timestamp not null,
                                    updatedAt timestamp not null,
                                    r_resource_name varchar(255) not null,
                                    r_resource_content text not null,
                                    r_resource_checksum varchar(255) not null,
                                    tr_task_pk varchar(255),
                                    primary key (pk)
);

create table core.tasks (
                            pk varchar(255) not null,
                            createdAt timestamp not null,
                            updatedAt timestamp not null,
                            t_code varchar(255) not null,
                            t_name varchar(255),
                            t_runnerBean varchar(255) not null,
                            primary key (pk)
);

create table core.tasksParameters (
                                      pk varchar(255) not null,
                                      createdAt timestamp not null,
                                      updatedAt timestamp not null,
                                      tp_code varchar(255) not null,
                                      tp_defaultValue varchar(255),
                                      tp_isRequired boolean not null,
                                      tp_task varchar(255),
                                      primary key (pk)
);
create index c_code_idx on core.conditions (c_code);

alter table core.conditions
    add constraint c_code_unq_constraint unique (c_code);

alter table core.delegatingTaskParameterValues
    add constraint dtpv_processStep_parameter_unq_constraint unique (dtpv_processStep_pk, dtpv_taskParameter_pk);

alter table core.predefinedTaskParameterValues
    add constraint ptpv_processStep_parameter_unq_constraint unique (ptpv_processStep_pk, ptpv_taskParameter_pk);
create index p_code_idx on core.processes (p_code);

alter table core.processes
    add constraint p_code_unq_constraint unique (p_code);
create index pi_code_idx on core.processInstances (pi_code);

alter table core.processInstances
    add constraint pi_code_unq_constraint unique (pi_code);
create index pp_code_idx on core.processParameters (pp_code);

alter table core.processParameters
    add constraint pp_code_unq_constraint unique (pp_code);

alter table core.processResources
    add constraint UK_5sk2lgy411mfes11quauw77qm unique (pr_process_pk);
create index psi_code_idx on core.processStepInstances (psi_code);

alter table core.processStepInstances
    add constraint psi_code_unq_constraint unique (psi_code);
create index ps_code_idx on core.processSteps (ps_code);

alter table core.processSteps
    add constraint ps_code_unq_constraint unique (ps_code);

alter table core.processSteps
    add constraint ps_task_order_unq_constraint unique (ps_task_pk, ps_order);
create index ti_code_idx on core.taskInstances (ti_code);

alter table core.taskInstances
    add constraint ti_code_unq_constraint unique (ti_code);

alter table core.taskResources
    add constraint UK_nrc2xqujm061glwxgcigmha62 unique (tr_task_pk);
create index t_code_idx on core.tasks (t_code);

alter table core.tasks
    add constraint t_code_unq_constraint unique (t_code);
create index tp_code_idx on core.tasksParameters (tp_code);

alter table core.tasksParameters
    add constraint tp_code_task_unq_constraint unique (tp_code, tp_task);

alter table core.delegatingTaskParameterValues
    add constraint FKdp2dxuqka3fapm4e2ad4s3bud
        foreign key (dtpv_processParameter_pk)
            references core.processParameters;

alter table core.delegatingTaskParameterValues
    add constraint FKirjo367k48evx7hf8pjehe4sn
        foreign key (dtpv_processStep_pk)
            references core.processSteps;

alter table core.delegatingTaskParameterValues
    add constraint FKal24c0dtr4h5lw2cho5crxpe9
        foreign key (dtpv_taskParameter_pk)
            references core.tasksParameters;

alter table core.predefinedTaskParameterValues
    add constraint FKngr5nmwex4p06b9w4mybhtioi
        foreign key (ptpv_processStep_pk)
            references core.processSteps;

alter table core.predefinedTaskParameterValues
    add constraint FKg99ajgmonwk69hfqnhpqjtce9
        foreign key (ptpv_taskParameter_pk)
            references core.tasksParameters;

alter table core.processInstances
    add constraint FKsn10frqrde9hhyo78rnnhrrey
        foreign key (pi_process_pk)
            references core.processes;

alter table core.processParameters
    add constraint FKrr7ge1d6ajx1esq9y6kf75d7q
        foreign key (pp_process)
            references core.processes;

alter table core.processParameterValues
    add constraint FK6nbewi6qes4cgsi3tsqhpmgi4
        foreign key (ppv_parameter_pk)
            references core.processParameters;

alter table core.processParameterValues
    add constraint FK1x6m2c346jsxopq9idun3jdcf
        foreign key (pi_processInstance_pk)
            references core.processInstances;

alter table core.processResources
    add constraint FKo6u92l5ixd1m0yg7yckcdni9v
        foreign key (pr_process_pk)
            references core.processes;

alter table core.processStepInstances
    add constraint FK1y7sg3j8l2bt9ibjshd31cx6o
        foreign key (psi_processInstance_pk)
            references core.processInstances;

alter table core.processStepInstances
    add constraint FKbknre97wsbvgn6jx0s9re62i5
        foreign key (psi_processStep_pk)
            references core.processSteps;

alter table core.processStepInstances
    add constraint FKo52vpj9eplnlwhqjurl584iyt
        foreign key (psi_taskInstance_pk)
            references core.taskInstances;

alter table core.processSteps
    add constraint FKsjoc03qlmpa9l0p3puc86ee6k
        foreign key (ps_condition_pk)
            references core.conditions;

alter table core.processSteps
    add constraint FKs1qa6geypha71m8tr0k5tnutk
        foreign key (ps_process_pk)
            references core.processes;

alter table core.processSteps
    add constraint FKnyia0jaiv2tratl8v8qfof7x2
        foreign key (ps_task_pk)
            references core.tasks;

alter table core.taskInstances
    add constraint FK1ff53kn6int0x0mrjf86giiim
        foreign key (ti_task_pk)
            references core.tasks;

alter table core.taskParameterValues
    add constraint FKl4amsdux44sj2uj4ihamfm418
        foreign key (tpv_parameter_pk)
            references core.tasksParameters;

alter table core.taskParameterValues
    add constraint FKp5c3ejbfh9gtojo31cwwmfr2o
        foreign key (tpv_taskInstance_pk)
            references core.taskInstances;

alter table core.taskResources
    add constraint FKc7w74ysym0u46oc7pw2vtytl
        foreign key (tr_task_pk)
            references core.tasks;

alter table core.tasksParameters
    add constraint FK5etrqkkk0n1hqs2rvg48980fl
        foreign key (tp_task)
            references core.tasks;
