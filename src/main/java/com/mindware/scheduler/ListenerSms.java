package com.mindware.scheduler;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

@WebListener
public class ListenerSms implements ServletContextListener  {
	private Scheduler scheduler;
	
	public void smsTrigger() throws Exception {
		System.out.println("Inicio listener");
		
		if (!executionJob("smsJob", "group1")) {
						
			JobDetail job = JobBuilder.newJob(JobSms.class)
				.withIdentity("smsJob", "group1").build();
		
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("smsTrigger", "group1")
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(120).repeatForever())
									
					.build();
		
			// schedule it
			
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} else {
			System.out.println("Job ya existe");
		}
	}
	
	private boolean executionJob(String name, String group) throws Exception {
		boolean ejecutando = false;
		
		if (scheduler!=null)
		for (String groupName : scheduler.getJobGroupNames()) {

		     for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

			  String jobName = jobKey.getName();
			  String jobGroup = jobKey.getGroup();
			  
			  if ((jobName.equals(name)) && (jobGroup.equals(group))) {
				  ejecutando = true;
			  }

//			  //get job's trigger
//			  List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
//			  Date nextFireTime = triggers.get(0).getNextFireTime();
//
//				System.out.println("[jobName] : " + jobName + " [groupName] : "
//					+ jobGroup + " - " + nextFireTime);

			  }

		    }
		return ejecutando;
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			smsTrigger();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}


