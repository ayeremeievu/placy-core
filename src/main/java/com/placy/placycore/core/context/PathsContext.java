package com.placy.placycore.core.context;

import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
public class PathsContext {
   public String getExecutionClasspathLocalPath(Class<?> clazz){
       return clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
   }
}
