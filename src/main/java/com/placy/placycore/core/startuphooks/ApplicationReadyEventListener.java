package com.placy.placycore.core.startuphooks;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    private List<PostStartupHook> postStartupHooks;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if(postStartupHooks != null && !postStartupHooks.isEmpty()) {
            postStartupHooks.forEach(postStartupHook -> postStartupHook.run(applicationReadyEvent.getApplicationContext()));
        }
    }

    public List<PostStartupHook> getPostStartupHooks() {
        return postStartupHooks;
    }

    public void setPostStartupHooks(List<PostStartupHook> postStartupHooks) {
        this.postStartupHooks = postStartupHooks;
    }
}
