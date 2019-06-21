
package xs.project.services;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * Config for webservice
 * 
 * @author saukin
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    /**
     *
     * @return
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ExceptionMappers.Exception404Mapper.class);
        resources.add(ExceptionMappers.Exception500Mapper.class);
        resources.add(ExceptionMappers.GenericExceptionMapper.class);
        resources.add(xs.project.services.BrendanService.class);
        resources.add(xs.project.services.xs_webservice.class);
    }
    
}
