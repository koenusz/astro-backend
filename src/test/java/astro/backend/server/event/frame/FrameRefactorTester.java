package astro.backend.server.event.frame;

public class FrameRefactorTester {

    public static void main(String[] args) {
        EventDispatcher dispatcher = new EventDispatcher();
        dispatcher.registerHandler(TestEvent.class, event -> System.out.println("1 " + ((TestEvent) event).getTest() ));

        dispatcher.dispatch(new TestEvent());

        dispatcher.registerHandler(TestEvent.class, event -> System.out.println("2 " + event.getType()));

        dispatcher.dispatch(new TestEvent());

        TestState state = new TestState("bla bla");

        dispatcher.registerHandler(TestEvent.class, new TestStateHandler(state));

        dispatcher.dispatch(new TestEvent());

    }

}
