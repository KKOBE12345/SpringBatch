package com.qianfeng.SpringBatchJobLauncher;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 20:38
 **/
//@RestController
//public class JobLauncherController {
//    @Autowired
//    private JobLauncher jobLauncher;
//    @Autowired
//    private Job jobLauncherDemoJob;
//
//    @RequestMapping("/job/{msg}")
//    public String jobRun1(@PathVariable String msg) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
//        //把接收到的参数值传给任务
//        JobParameters parameters=new JobParametersBuilder()
//                .addString("msg", msg)
//                .toJobParameters();
//        //启动任务 并把参数传给任务
//        jobLauncher.run(jobLauncherDemoJob, parameters);
//
//        return "任务执行成功了！！！！！";
//
//    }
//}
