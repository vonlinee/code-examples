package design.pattern.state;

public class StatePattern {
	public static void main(String[] args) {
		AlertStateContext stateContext = new AlertStateContext();
		stateContext.alert();
		stateContext.alert();
		stateContext.setState(new Silent());
		stateContext.alert();
		stateContext.alert();
		stateContext.alert();
	}
}

class AlertStateContext {
	// Context持有状态对象
	private MobileAlertState currentState;

	public AlertStateContext() {
		currentState = new Vibration();
	}

	public void setState(MobileAlertState state) {
		currentState = state;
	}

	public void alert() {
		currentState.alert(this);
	}
}

class Vibration implements MobileAlertState {
	@Override
	public void alert(AlertStateContext ctx) {
		System.out.println("vibration...");
	}
}

class Silent implements MobileAlertState {
	@Override
	public void alert(AlertStateContext ctx) {
		System.out.println("silent...");
	}
}

interface MobileAlertState {
	public void alert(AlertStateContext ctx);
}