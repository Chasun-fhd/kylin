SELECT EXTRACT(YEAR FROM TEST_CAL_DT.WEEK_BEG_DT) AS yr_WEEK_BEG_DT_ok 
 FROM TEST_KYLIN_FACT 
 inner JOIN TEST_CAL_DT ON (TEST_KYLIN_FACT.CAL_DT = TEST_CAL_DT.CAL_DT) 
 GROUP BY EXTRACT(YEAR FROM TEST_CAL_DT.WEEK_BEG_DT) 
