package astro.backend.server.event.frame;

public class TestStateHandler implements Handler<TestEvent> {

    public TestStateHandler(TestState state) {
        this.state = state;
    }

    private TestState state;

    @Override
    public void onEvent(TestEvent event) {
        System.out.println("state " + state.getState());
    }
}
