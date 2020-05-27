package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.executable.ExecutableBean;
import com.placy.placycore.core.processes.executable.TaskRunner;
import com.placy.placycore.core.processes.model.TaskInstanceModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class BeanRunnerService implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void runTaskBean(TaskInstanceModel executableModel, String beanId, Map<String, Object> params) {
        ExecutableBean executableBean = searchBeanById(beanId);

        TaskRunner taskRunner = new TaskRunner(executableModel, executableBean, params);
        Thread thread = new Thread(taskRunner);

        thread.start();
    }

    public ExecutableBean searchBeanById(String beanId) {
        return applicationContext.getBean(beanId, ExecutableBean.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
