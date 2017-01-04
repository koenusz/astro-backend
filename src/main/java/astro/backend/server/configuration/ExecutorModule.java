package astro.backend.server.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorModule extends AbstractModule {


    private final ExecutorService executorService;

    public ExecutorModule() {
        executorService = Executors.newCachedThreadPool();
    }

    @Override
    protected void configure() {

    }

    @Provides
    public ExecutorService provideExecutor(){
        return executorService;
    }
}
