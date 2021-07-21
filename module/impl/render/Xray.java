package genius.module.impl.render;

import com.google.common.collect.Lists;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Setting;

import java.util.HashSet;
import java.util.List;

public class Xray extends Module {
	private static final String KEY_OPACITY = "OPACITY";
	public static final HashSet<Integer> blockIDs = new HashSet();
	private int opacity = 160;
	private List<Integer> KEY_IDS = Lists.newArrayList(10, 11, 8, 9, 14, 15, 16, 21, 41, 42, 46, 48, 52, 56, 57, 61, 62, 73, 74, 84,89, 103, 116, 117, 118, 120,129, 133, 137,145, 152, 153, 154);


	public Xray(ModuleData data) {
		super(data);
		settings.put(Xray.KEY_OPACITY, new Setting<>(Xray.KEY_OPACITY, 160, "Opacity for blocks you want to ignore.",  5, 0, 255));
	}

	@Override
	public void onEnable() {
		blockIDs.clear();
		opacity = ((Number) settings.get(KEY_OPACITY).getValue()).intValue();
		try {
			for (Integer o : KEY_IDS) {
				blockIDs.add(o);
			}
			genius.management.command.impl.Xray.loadIDs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mc.renderGlobal.loadRenderers();
	}

	@Override
	public void onDisable() {
		mc.renderGlobal.loadRenderers();
	}

	public boolean containsID(int id) {
		return blockIDs.contains(id);
	}

	public int getOpacity() {
		return opacity;
	}
}