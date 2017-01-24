package astro.backend.server.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.apache.shiro.concurrent.SubjectAwareExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorModule extends AbstractModule {


    private final ExecutorService executorService;

    public ExecutorModule() {
        executorService = new SubjectAwareExecutorService(Executors.newCachedThreadPool());
    }

    @Override
    protected void configure() {

    }

    @Provides
    public ExecutorService provideExecutor(){
        return executorService;
    }
}
