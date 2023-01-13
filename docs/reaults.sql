select * from dataflow.BATCH_JOB_EXECUTION;
select * from dataflow.BATCH_STEP_EXECUTION;
select * from dataflow.TASK_EXECUTION;
--step csv
select count(*) from test_general.people;
--step json
select count(*) from test_general.bill_statement;
select * from test_general.bill_statement order by bill_id desc;