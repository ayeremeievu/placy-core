package com.placy.placycore.core.startuphooks;

import org.springframework.context.ApplicationContext;

/**
 * @author a.yeremeiev@netconomy.net
 */
public interface PostStartupHook {
    Object run(ApplicationContext applicationContext);
}
