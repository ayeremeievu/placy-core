package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.executable.ExecutableBean;
import com.placy.placycore.core.processes.executable.TaskRunner;
import com.placy.placycore.core.processes.model.TaskInstanceModel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class BeanRunnerService implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Autowired
    private ObjectProvider<TaskRunner> taskRunnerObjectProvider;

    public Future<?> runTaskBean(TaskInstanceModel executableModel, String beanId) {
        ExecutableBean executableBean = searchBeanById(beanId);

        TaskRunner taskRunner = taskRunnerObjectProvider.getObject(executableModel, executableBean);

        return createSingleThreadExecutorService().submit(taskRunner);
    }

    private ExecutorService createSingleThreadExecutorService() {
        return Executors.newSingleThreadExecutor();
    }

    public ExecutableBean searchBeanById(String beanId) {
        return applicationContext.getBean(beanId, ExecutableBean.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
