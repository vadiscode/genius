package genius.management.keybinding;

public interface Bindable {
	void setKeybind(Keybind newBind);

	void onBindPress();

	void onBindRelease();
}