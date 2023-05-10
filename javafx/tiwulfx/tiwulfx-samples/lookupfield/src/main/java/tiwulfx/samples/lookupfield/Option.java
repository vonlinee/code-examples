package tiwulfx.samples.lookupfield;

/**
 *
 * @author amrullah
 */
public class Option {

	private int value;
	private String label;

	public Option(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "Option{" + "value=" + value + ", label=" + label + '}';
	}

}
